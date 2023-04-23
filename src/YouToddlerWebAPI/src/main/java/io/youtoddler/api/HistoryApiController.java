package io.youtoddler.api;

import io.youtoddler.model.HistoryLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-22T11:09:03.174853304Z[GMT]")
@RestController
public class HistoryApiController implements HistoryApi {

    private static final Logger log = LoggerFactory.getLogger(HistoryApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public HistoryApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<HistoryLog>> getAllHistory() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<HistoryLog>>(objectMapper.readValue("[ {\n  \"date\" : \"2022-12-10T13:45:00Z\",\n  \"audioId\" : 20,\n  \"videoId\" : 30,\n  \"id\" : 1,\n  \"url\" : \"https://youtu.be/dQw4w9WgXcQ\"\n}, {\n  \"date\" : \"2022-12-10T13:45:00Z\",\n  \"audioId\" : 20,\n  \"videoId\" : 30,\n  \"id\" : 1,\n  \"url\" : \"https://youtu.be/dQw4w9WgXcQ\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<HistoryLog>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<HistoryLog>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<HistoryLog>> getUrlHistory(@NotNull @Parameter(in = ParameterIn.QUERY, description = "URL of the video to fetch" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "url", required = true) String url) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<HistoryLog>>(objectMapper.readValue("[ {\n  \"date\" : \"2022-12-10T13:45:00Z\",\n  \"audioId\" : 20,\n  \"videoId\" : 30,\n  \"id\" : 1,\n  \"url\" : \"https://youtu.be/dQw4w9WgXcQ\"\n}, {\n  \"date\" : \"2022-12-10T13:45:00Z\",\n  \"audioId\" : 20,\n  \"videoId\" : 30,\n  \"id\" : 1,\n  \"url\" : \"https://youtu.be/dQw4w9WgXcQ\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<HistoryLog>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<HistoryLog>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<HistoryLog> getUserByName(@Parameter(in = ParameterIn.PATH, description = "The ID that needs to be fetched.", required=true, schema=@Schema()) @PathVariable("logId") Long logId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<HistoryLog>(objectMapper.readValue("{\n  \"date\" : \"2022-12-10T13:45:00Z\",\n  \"audioId\" : 20,\n  \"videoId\" : 30,\n  \"id\" : 1,\n  \"url\" : \"https://youtu.be/dQw4w9WgXcQ\"\n}", HistoryLog.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<HistoryLog>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<HistoryLog>(HttpStatus.NOT_IMPLEMENTED);
    }

}
