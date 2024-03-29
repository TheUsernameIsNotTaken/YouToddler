/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.42).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.youtoddler.api;

import io.youtoddler.model.ModelApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.*;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-22T11:09:03.174853304Z[GMT]")
@Validated
public interface DownloadApi {

    @Operation(summary = "Return video's video or audio data", description = "This returns a video's video or audio data given by it's url, video id or audio id. it can be video-only, audio-only or both combined.", tags={ "download" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful download", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModelApiResponse.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid query value (video id/audio id/url) or content type."),
        
        @ApiResponse(responseCode = "404", description = "Unavailable or nonextistent video"),
        
        @ApiResponse(responseCode = "406", description = "Missing video OR audio id"),
        
        @ApiResponse(responseCode = "408", description = "Timeout"),
        
        @ApiResponse(responseCode = "500", description = "Internal Server error while generating response.") })
    @RequestMapping(value = "/download",
        produces = { "application/json", "application/xml" }, 
        method = RequestMethod.GET)
    ResponseEntity<ModelApiResponse> getVideoData(@NotNull @Parameter(in = ParameterIn.QUERY, description = "URL of the video to fetch" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "url", required = true) String url, @Parameter(in = ParameterIn.QUERY, description = "ID of the video format" ,schema=@Schema()) @Valid @RequestParam(value = "videoID", required = false) Long videoID, @Parameter(in = ParameterIn.QUERY, description = "ID of the audio format" ,schema=@Schema()) @Valid @RequestParam(value = "audioID", required = false) Long audioID);

}

