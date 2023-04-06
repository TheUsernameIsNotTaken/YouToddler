package io.swagger.api;

import io.swagger.model.ModelApiResponse;
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
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-06T21:09:37.363059348Z[GMT]")
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
                "\"\n}", ModelApiResponse.class), HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ModelApiResponse> getVideoData(@NotNull @Parameter(in = ParameterIn.QUERY, description = "URL of the video to fetch" ,required=true,schema=@Schema( defaultValue="https://youtu.be/dQw4w9WgXcQ")) @Valid @RequestParam(value = "url", required = true, defaultValue="https://youtu.be/dQw4w9WgXcQ") String url,@Parameter(in = ParameterIn.QUERY, description = "ID of the video format" ,schema=@Schema()) @Valid @RequestParam(value = "videoID", required = false) Long videoID,@Parameter(in = ParameterIn.QUERY, description = "ID of the audio format" ,schema=@Schema()) @Valid @RequestParam(value = "audioID", required = false) Long audioID) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                //Check if URl is present
                if(url.equals(null) || url.isEmpty())
                    return generateResponse(HttpStatus.BAD_REQUEST,
                            "Error", "Invalid url");
                // Check if an ID is present.
                if(audioID == null && videoID == null){
                    return generateResponse(HttpStatus.NOT_ACCEPTABLE,
                            "Error", "Missing video AND audio id");
                }
                // TODO: Check if URL exists.
                if(url.equals("NotValidVideo")){
                    return generateResponse(HttpStatus.NOT_FOUND,
                            "Error", "Unavailable or nonextistent video");
                }
                // TODO: Get video by URL. Or by ticket number if it gets changed.
                Path relIn = Paths.get("example_files/Videjo.zip");
                byte[] zipBytes = java.nio.file.Files.readAllBytes(relIn);
                String basedFile = Base64.getEncoder().encodeToString(zipBytes);
                // TODO: Return downloaded video file.
                return generateResponse(HttpStatus.NOT_IMPLEMENTED,
                        "base64", basedFile);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ModelApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<ModelApiResponse>(HttpStatus.BAD_REQUEST);
    }

}
