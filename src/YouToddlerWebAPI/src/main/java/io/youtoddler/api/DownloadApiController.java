package io.youtoddler.api;

import io.youtoddler.model.ModelApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.*;
import java.util.concurrent.Future;
import java.util.function.Consumer;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-22T11:09:03.174853304Z[GMT]")
@RestController
public class DownloadApiController implements DownloadApi {

    private static final Logger log = LoggerFactory.getLogger(DownloadApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public DownloadApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    private ResponseEntity<ModelApiResponse> generateResponse(HttpStatus responseStatus, String typeString, String messageString) throws IOException{
        return new ResponseEntity<ModelApiResponse>(objectMapper.readValue("{\n  " +
                "\"code\" : " + responseStatus.value() + ",\n  " +
                "\"type\" : \"" + typeString + "\",\n  " +
                "\"message\" : \""+ messageString +
                "\"\n}", ModelApiResponse.class), responseStatus);
    }

    //Zip file name holder class
    private class ZipFinder{
        private boolean defined;
        private String zipName;

        public ZipFinder() {
            defined = false;
            zipName = null;
        }

        boolean isDefined() {
            return defined;
        }

        void setDefined(boolean defined) {
            this.defined = defined;
        }

        String getZipName() {
            return zipName;
        }

        void setZipName(String zipName) {
            this.zipName = zipName;
        }
    }

    //Zip search consumer
    private static class ExecConsumer implements Consumer<String>{
        //Pattern filePattern = Pattern.compile("^(.+?)'(.+?)'(.+?)$");

        private ZipFinder zipFinder;

        public ExecConsumer(ZipFinder zipFinder) {
            this.zipFinder = zipFinder;
        }

        @Override
        public void accept(String s) {
            String[] strArr = s.split("'", 3);
            if(strArr.length == 3){
                zipFinder.setDefined(true);
                zipFinder.setZipName(strArr[1]);
            }
        }
    }

    public ResponseEntity<ModelApiResponse> getVideoData(@NotNull @Parameter(in = ParameterIn.QUERY, description = "URL of the video to fetch" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "url", required = true) String url,@Parameter(in = ParameterIn.QUERY, description = "ID of the video format" ,schema=@Schema()) @Valid @RequestParam(value = "videoID", required = false) Long videoID,@Parameter(in = ParameterIn.QUERY, description = "ID of the audio format" ,schema=@Schema()) @Valid @RequestParam(value = "audioID", required = false) Long audioID) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                //Check if URl is present
                if(url.equals(null) || url.isEmpty())
                    return generateResponse(HttpStatus.BAD_REQUEST,
                            "Error", "Invalid url");
                // Check if all ID is present.
                if(audioID == null || videoID == null){
                    return generateResponse(HttpStatus.NOT_ACCEPTABLE,
                            "Error", "Missing video OR audio id");
                }
                /*
                //Check if URL exists. - Unneded, done by YouToddlerCLI
                if(url.equals("NotValidVideo")){
                    return generateResponse(HttpStatus.NOT_FOUND,
                            "Error", "Unavailable or nonextistent video");
                }
                */
                // Needed main paths of operation
                Path toddlerCliDir = Paths.get("").toRealPath();
                //  - Read in staging directories from file
                Path settingJson = Paths.get(toddlerCliDir.toString(), "appsettings.json");
                FileReader fr = new FileReader(settingJson.toString());
                Object o = new JSONParser().parse(fr);
                JSONObject j = (JSONObject) o;
                JSONObject YouToddlerConfiguration = (JSONObject) j.get("YouToddlerConfiguration");
                String StagingDirectory = (String) YouToddlerConfiguration.get("StagingDirectory");
                String ArtifactUploadDestination = (String) YouToddlerConfiguration.get("ArtifactUploadDestination");
                //  - Generate staging and artifact paths.
                Path toddlerStaging = Paths.get(toddlerCliDir.toString(), StagingDirectory);
                Path toddlerArtifact = Paths.get(toddlerCliDir.toString(), ArtifactUploadDestination);
                fr.close();
                // Generate command
                //  - Check os
                boolean isWindows = System.getProperty("os.name")
                        .toLowerCase().startsWith("windows");
                //  - Get video by URL.
                ProcessBuilder builder = new ProcessBuilder();
                //String url_str = "https://www.youtube.com/watch?v=ycHVUvvOwzY";
                log.info("Starting builder for " + url);
                if (isWindows) {
                    builder.command("powershell.exe", ".\\YouToddlerCLI", "download",
                            videoID != null ? ("-v " + Long.toString(videoID)) : (""),
                            audioID != null ? ("-a " + Long.toString(audioID)) : (""),
                            "-t", url);
                } else {
                    builder.command("./YouToddlerCLI", "download",
                            videoID != null ? ("-v " + Long.toString(videoID)) : (""),
                            audioID != null ? ("-a " + Long.toString(audioID)) : (""),
                            "-t", url);
                }
                builder.directory(new File(toddlerCliDir.toString()));
                log.debug("Builded command: " + builder.command().toString());
                Process process = builder.start();
                //  - gobble up the proccess
                ZipFinder downloadedZip = new ZipFinder();
                Consumer<String> execConsumer = new ExecConsumer(downloadedZip);
                log.debug("Starting proccess gobbler.");
                MetaApiController.StreamGobbler streamGobbler =
                        new MetaApiController.StreamGobbler(
                                process.getInputStream(),
                                (new MetaApiController.loggerConsumer()).andThen(execConsumer));
                log.info("Starting download execution.");
                Future<?> future = Executors.newSingleThreadExecutor().submit(streamGobbler);
                int exitCode = process.waitFor();
                assert exitCode == 0;
                future.get(600, TimeUnit.SECONDS);
                log.info("Finished download execution.");
                // Check if download is successfull
                if(downloadedZip.isDefined()){
                    // Generate bytes
                    Path zipVideoPath = Paths.get(toddlerArtifact.toString(), downloadedZip.getZipName());
                    //log.info(zipVideoPath.toString());
                    byte[] zipBytes = Files.readAllBytes(zipVideoPath);
                    String basedFile = Base64.getEncoder().encodeToString(zipBytes);
                    // Return downloaded video file.
                    return generateResponse(HttpStatus.OK,
                            "base64", basedFile);
                }else{
                    return generateResponse(HttpStatus.NOT_FOUND,
                            "Error", "Not found video id, audio id, or url.");
                }
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ModelApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }catch (ParseException e){
                log.error("Couldn't parse co-working paths", e);
                return new ResponseEntity<ModelApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (InterruptedException e) {
                log.error("Interupted download execution", e);
                return new ResponseEntity<ModelApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (ExecutionException e) {
                log.error("Error during download execution", e);
                return new ResponseEntity<ModelApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (TimeoutException e) {
                log.error("Timeout during download", e);
                return new ResponseEntity<ModelApiResponse>(HttpStatus.REQUEST_TIMEOUT);
            }
        }
        return new ResponseEntity<ModelApiResponse>(HttpStatus.BAD_REQUEST);
    }

}
