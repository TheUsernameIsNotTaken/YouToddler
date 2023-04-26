package io.youtoddler.api;

import io.youtoddler.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.concurrent.*;
import java.util.concurrent.Future;
import java.util.function.Consumer;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-22T11:09:03.174853304Z[GMT]")
@RestController
public class MetaApiController implements MetaApi {

    private static final Logger log = LoggerFactory.getLogger(MetaApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public MetaApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;


        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                    .forEach(consumer);
        }
    }

    static class loggerConsumer implements Consumer<String>{
        @Override
        public void accept(String s) {
            log.info(s);
        }
    }

    public ResponseEntity<Metadata> getVideoMeta(@NotNull @Parameter(in = ParameterIn.QUERY, description = "ID of the object to fetch" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "url", required = true) String url) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            // Check if URL is valid.
            if(url.isEmpty() || url.equals(null)){
                return new ResponseEntity<Metadata>(HttpStatus.NOT_ACCEPTABLE);
            }
            /*
            //Check if URL exists. - Unneded, done by YouToddlerCLI
            if(url.equals("NotValidVideo")) {
                return new ResponseEntity<Metadata>(HttpStatus.NOT_FOUND);
            }
            */
            try {
                // Needed main paths of operation
                Path toddlerCliDir = Paths.get("").toRealPath();
                //  - Read in staging directories from file
                Path settingJson = Paths.get(toddlerCliDir.toString(), "appsettings.json");
                FileReader fr = new FileReader(settingJson.toString());
                Object o = new JSONParser().parse(fr);
                JSONObject j = (JSONObject) o;
                JSONObject YouToddlerConfiguration = (JSONObject) j.get("YouToddlerConfiguration");
                String StagingDirectory = (String) YouToddlerConfiguration.get("StagingDirectory");
                //  - Generate staging and artifact paths.
                Path toddlerStaging = Paths.get(toddlerCliDir.toString(), StagingDirectory);
                fr.close();
                // Get metadata from YouToddlerCLI - Generate command
                //  - Check os
                boolean isWindows = System.getProperty("os.name")
                        .toLowerCase().startsWith("windows");
                //  - Get metadata by URL.
                ProcessBuilder builder = new ProcessBuilder();
                log.info("Start getting metadata for " + url);
                if (isWindows) {
                    builder.command("powershell.exe", ".\\YouToddlerCLI", "describe ",
                            "-t", url);
                } else {
                    builder.command("./YouToddlerCLI", "describe",
                            "-t", url);
                }
                builder.directory(new File(toddlerCliDir.toString()));
                log.debug("Builded command: " + builder.command().toString());
                Process process = builder.start();
                //  - gobble up the proccess
                log.debug("Starting proccess gobbler.");
                MetaApiController.StreamGobbler streamGobbler =
                        new MetaApiController.StreamGobbler(
                                process.getInputStream(),
                                new loggerConsumer());
                log.info("Starting metadata reading.");
                Future<?> future = Executors.newSingleThreadExecutor().submit(streamGobbler);
                int exitCode = process.waitFor();
                assert exitCode == 0;
                future.get(90, TimeUnit.SECONDS);
                log.info("Finished getting metadata.");
                // Generate the metadata object.
                Path metadataJson = Paths.get(toddlerStaging.toString(), "format_metadata.json");
                // Build a Metadata from returned information
                FileReader MetaFr = new FileReader(metadataJson.toString());
                Object metaO = new JSONParser().parse(MetaFr);
                JSONArray metaJ = (JSONArray) metaO;
                Iterator metaI = metaJ.iterator();
                boolean first = true;
                Metadata genMeta = new Metadata();  // Metadata to save data into
                while (metaI.hasNext()) {
                    JSONObject currMetaJ = (JSONObject) metaI.next();
                    if(first){  //Set outside variables once.
                        genMeta.setUrl(url);
                        genMeta.setVideoName((String) currMetaJ.get("videoTitle"));
                        genMeta.setImageUrl((String) currMetaJ.get("thumbnailUrl"));
                        first = false;
                    }
                    // generate the current format data
                    Format nextFormat = new Format();
                    nextFormat.setId((Long) currMetaJ.get("id"));
                    nextFormat.setName((String) currMetaJ.get("extension"));
                        // generate resolution
                        Resolution nextRes = new Resolution();
                        nextRes.setFilesize((Long) currMetaJ.get("fileSize"));
                        nextRes.setTbr((Long) currMetaJ.get("tbr"));
                            // generate audio
                            Audio nextAudio = new Audio();
                            JSONObject audioO = (JSONObject) currMetaJ.get("audioFormat");
                            nextAudio.setAudioCodec((String) audioO.get("codec"));
                            nextAudio.setAbr((Long) audioO.get("abr"));
                            // generate video
                            Video nextVideo = new Video();
                            JSONObject videoO = (JSONObject) currMetaJ.get("videoFormat");
                            nextVideo.setVideoCodec((String) videoO.get("codec"));
                            nextVideo.setVbr((Long) videoO.get("vbr"));
                            nextVideo.setFps((Long) videoO.get("fps"));
                        // add generated objects to resolution
                        nextRes.setAudio(nextAudio);
                        nextRes.setVideo(nextVideo);
                    // add generated resolution to format
                    nextFormat.setResolution(nextRes);
                    //add format into Metadata list
                    genMeta.addFormatsItem(nextFormat);
                }
                MetaFr.close();
                log.info("Generated medtadata");
                // Return the generated metadata.
                return new ResponseEntity<Metadata>(genMeta, HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Metadata>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (ParseException e) {
                log.error("Error while parsing JSON values", e);
                return new ResponseEntity<Metadata>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (ExecutionException e) {
                log.error("Error during metadata getting execution", e);
                return new ResponseEntity<Metadata>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (InterruptedException e) {
                log.error("Interrupted metadata getter execution", e);
                return new ResponseEntity<Metadata>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (TimeoutException e) {
                log.error("Error while parsing JSON values", e);
                return new ResponseEntity<Metadata>(HttpStatus.REQUEST_TIMEOUT);
            }
        }
        // If the accept is not supported, then consider it as a bad request.
        return new ResponseEntity<Metadata>(HttpStatus.BAD_REQUEST);
    }

}
