/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.42).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.HistoryLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-22T11:09:03.174853304Z[GMT]")
@Validated
public interface HistoryApi {

    @Operation(summary = "Get all previous downloads' logs", description = "Returns all history data regarding previous downloads. Could be made deprecated, and only allow specified history data to be GET later.", tags={ "history" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = HistoryLog.class)))),
        
        @ApiResponse(responseCode = "403", description = "Not authorized"),
        
        @ApiResponse(responseCode = "404", description = "No history found"),
        
        @ApiResponse(responseCode = "500", description = "Internal Server error while generating response.") })
    @RequestMapping(value = "/history/getAll",
        produces = { "application/json", "application/xml" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<HistoryLog>> getAllHistory();


    @Operation(summary = "Return video's video or audio data", description = "Returns specific history data regarding previous downloads. The input is the specific url, we search by this between the logs. Returns all history logs that contain the specific URL.", tags={ "history" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = HistoryLog.class)))),
        
        @ApiResponse(responseCode = "400", description = "Invalid URL"),
        
        @ApiResponse(responseCode = "404", description = "No history found"),
        
        @ApiResponse(responseCode = "500", description = "Internal Server error while generating response.") })
    @RequestMapping(value = "/history/findByUrl",
        produces = { "application/json", "application/xml" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<HistoryLog>> getUrlHistory(@NotNull @Parameter(in = ParameterIn.QUERY, description = "URL of the video to fetch" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "url", required = true) String url);


    @Operation(summary = "Get log by id", description = "Returns a specific history log data regarding a previous download. Takes a log ID in path, and returns that specific log, if found.", tags={ "history" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HistoryLog.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        
        @ApiResponse(responseCode = "404", description = "Log not found"),
        
        @ApiResponse(responseCode = "500", description = "Internal Server error while generating response.") })
    @RequestMapping(value = "/history/{logId}",
        produces = { "application/json", "application/xml" }, 
        method = RequestMethod.GET)
    ResponseEntity<HistoryLog> getUserByName(@Parameter(in = ParameterIn.PATH, description = "The ID that needs to be fetched.", required=true, schema=@Schema()) @PathVariable("logId") Long logId);

}

