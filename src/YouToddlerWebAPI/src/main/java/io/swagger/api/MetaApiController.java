package io.swagger.api;

import io.swagger.model.Metadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.Future;
import java.util.function.Consumer;


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-06T21:09:37.363059348Z[GMT]")
@RestController
public class MetaApiController implements MetaApi {
    public final static String CLI_SUBPATH = "YouToddlerCLI\\bin\\Debug\\net7.0";

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

    public ResponseEntity<Metadata> getVideoMeta(@NotNull @Parameter(in = ParameterIn.QUERY, description = "ID of the object to fetch" ,required=true,schema=@Schema( defaultValue="https://youtu.be/dQw4w9WgXcQ")) @Valid @RequestParam(value = "url", required = true, defaultValue="https://youtu.be/dQw4w9WgXcQ") String url) {
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
                Path runParent = Paths.get("").toRealPath().getParent();
                Path toddlerCliDir = Paths.get(runParent.toString(),CLI_SUBPATH);
                //  - Read in staging directories from file
                Path settingJson = Paths.get(toddlerCliDir.toString(), "appsettings.json");
                Object o = new JSONParser().parse(new FileReader(settingJson.toString()));
                JSONObject j = (JSONObject) o;
                JSONObject YouToddlerConfiguration = (JSONObject) j.get("YouToddlerConfiguration");
                String StagingDirectory = (String) YouToddlerConfiguration.get("StagingDirectory");
                //  - Generate staging and artifact paths.
                Path toddlerStaging = Paths.get(toddlerCliDir.toString(), StagingDirectory);
                // Get metadata from YouToddlerCLI - Generate command
                //  - Check os
                boolean isWindows = System.getProperty("os.name")
                        .toLowerCase().startsWith("windows");
                log.info(toddlerCliDir.toString());
                //  - Get metadata by URL.
                ProcessBuilder builder = new ProcessBuilder();
                log.info("Start getting metadata for " + url);
                if (isWindows) {
                    builder.command("powershell.exe", ".\\YouToddlerCLI", "describe ",
                            "-t", url);
                } else {
                    builder.command("sh", "./YouToddlerCLI", "describe",
                            "-t", url);
                }
                builder.directory(new File(toddlerCliDir.toString()));
                //log.info("Builded command: " + builder.command().toString());
                Process process = builder.start();
                //  - gobble up the proccess
                log.info("Starting proccess gobbler.");
                MetaApiController.StreamGobbler streamGobbler =
                        new MetaApiController.StreamGobbler(
                                process.getInputStream(),
                                (Consumer<String>) System.out::println);
                log.info("Starting metadata reading.");
                Future<?> future = Executors.newSingleThreadExecutor().submit(streamGobbler);
                int exitCode = process.waitFor();
                assert exitCode == 0;
                future.get(60, TimeUnit.SECONDS);
                log.info("Finished getting metadata.");
                // Generate the metadata object.
                Path metadataJson = Paths.get(toddlerStaging.toString(), "format_metadata.json");
                byte[] metadataBytes = Files.readAllBytes(metadataJson);
                String metadataContent = new String(metadataBytes);
                // TODO: Fix Metadata class differences
                Metadata genMeta = objectMapper.readValue(metadataContent, Metadata.class);
                // Return the generated metadata.
                return new ResponseEntity<Metadata>(genMeta, HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Metadata>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (ParseException e) {
                log.error("Error while parsing JSON values", e);
                return new ResponseEntity<Metadata>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (ExecutionException e) {
                log.error("Error while parsing JSON values", e);
                return new ResponseEntity<Metadata>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (InterruptedException e) {
                log.error("Error while parsing JSON values", e);
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
