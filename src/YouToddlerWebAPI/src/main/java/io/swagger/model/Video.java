package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Video
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-22T11:09:03.174853304Z[GMT]")


public class Video   {
  @JsonProperty("videoCodec")
  private String videoCodec = null;

  @JsonProperty("fps")
  private Long fps = null;

  @JsonProperty("vbr")
  private Long vbr = null;

  public Video videoCodec(String videoCodec) {
    this.videoCodec = videoCodec;
    return this;
  }

  /**
   * Get videoCodec
   * @return videoCodec
   **/
  @Schema(example = "avc1", required = true, description = "")
      @NotNull

    public String getVideoCodec() {
    return videoCodec;
  }

  public void setVideoCodec(String videoCodec) {
    this.videoCodec = videoCodec;
  }

  public Video fps(Long fps) {
    this.fps = fps;
    return this;
  }

  /**
   * Get fps
   * @return fps
   **/
  @Schema(example = "30", description = "")
  
    public Long getFps() {
    return fps;
  }

  public void setFps(Long fps) {
    this.fps = fps;
  }

  public Video vbr(Long vbr) {
    this.vbr = vbr;
    return this;
  }

  /**
   * Get vbr
   * @return vbr
   **/
  @Schema(example = "446000", description = "")
  
    public Long getVbr() {
    return vbr;
  }

  public void setVbr(Long vbr) {
    this.vbr = vbr;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Video video = (Video) o;
    return Objects.equals(this.videoCodec, video.videoCodec) &&
        Objects.equals(this.fps, video.fps) &&
        Objects.equals(this.vbr, video.vbr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(videoCodec, fps, vbr);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Video {\n");
    
    sb.append("    videoCodec: ").append(toIndentedString(videoCodec)).append("\n");
    sb.append("    fps: ").append(toIndentedString(fps)).append("\n");
    sb.append("    vbr: ").append(toIndentedString(vbr)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
