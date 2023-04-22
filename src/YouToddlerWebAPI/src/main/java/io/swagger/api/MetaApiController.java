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
import java.util.List;
import java.util.Map;

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

    public ResponseEntity<Metadata> getVideoMeta(@NotNull @Parameter(in = ParameterIn.QUERY, description = "ID of the object to fetch" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "url", required = true) String url) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Metadata>(objectMapper.readValue("{\n  \"formats\" : [ {\n    \"name\" : \"mp4\",\n    \"id\" : 22,\n    \"resolution\" : {\n      \"tbr\" : 446000,\n      \"video\" : {\n        \"vbr\" : 446000,\n        \"fps\" : 30,\n        \"videoCodec\" : \"avc1\"\n      },\n      \"audio\" : {\n        \"abr\" : 0,\n        \"audioCodec\" : \"mp4a\"\n      },\n      \"filesize\" : 8497\n    }\n  }, {\n    \"name\" : \"mp4\",\n    \"id\" : 22,\n    \"resolution\" : {\n      \"tbr\" : 446000,\n      \"video\" : {\n        \"vbr\" : 446000,\n        \"fps\" : 30,\n        \"videoCodec\" : \"avc1\"\n      },\n      \"audio\" : {\n        \"abr\" : 0,\n        \"audioCodec\" : \"mp4a\"\n      },\n      \"filesize\" : 8497\n    }\n  } ],\n  \"videoName\" : \"Example Name gone wrong! | !!!NOT CLICKBAIT!!!\",\n  \"imageUrl\" : \"https://i.ytimg.com/vi/dQw4w9WgXcQ/hq720.jpg\",\n  \"url\" : \"https://youtu.be/dQw4w9WgXcQ\"\n}", Metadata.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Metadata>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Metadata>(HttpStatus.NOT_IMPLEMENTED);
    }

}
