package io.youtoddler.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Resolution
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-22T11:09:03.174853304Z[GMT]")


public class Resolution   {
  @JsonProperty("video")
  private Video video = null;

  @JsonProperty("audio")
  private Audio audio = null;

  @JsonProperty("filesize")
  private Long filesize = null;

  @JsonProperty("tbr")
  private Long tbr = null;

  public Resolution video(Video video) {
    this.video = video;
    return this;
  }

  /**
   * Get video
   * @return video
   **/
  @Schema(description = "")
  
    @Valid
    public Video getVideo() {
    return video;
  }

  public void setVideo(Video video) {
    this.video = video;
  }

  public Resolution audio(Audio audio) {
    this.audio = audio;
    return this;
  }

  /**
   * Get audio
   * @return audio
   **/
  @Schema(description = "")
  
    @Valid
    public Audio getAudio() {
    return audio;
  }

  public void setAudio(Audio audio) {
    this.audio = audio;
  }

  public Resolution filesize(Long filesize) {
    this.filesize = filesize;
    return this;
  }

  /**
   * Get filesize
   * @return filesize
   **/
  @Schema(example = "8497", required = true, description = "")
      @NotNull

    public Long getFilesize() {
    return filesize;
  }

  public void setFilesize(Long filesize) {
    this.filesize = filesize;
  }

  public Resolution tbr(Long tbr) {
    this.tbr = tbr;
    return this;
  }

  /**
   * Get tbr
   * @return tbr
   **/
  @Schema(example = "446000", description = "")
  
    public Long getTbr() {
    return tbr;
  }

  public void setTbr(Long tbr) {
    this.tbr = tbr;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Resolution resolution = (Resolution) o;
    return Objects.equals(this.video, resolution.video) &&
        Objects.equals(this.audio, resolution.audio) &&
        Objects.equals(this.filesize, resolution.filesize) &&
        Objects.equals(this.tbr, resolution.tbr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(video, audio, filesize, tbr);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Resolution {\n");
    
    sb.append("    video: ").append(toIndentedString(video)).append("\n");
    sb.append("    audio: ").append(toIndentedString(audio)).append("\n");
    sb.append("    filesize: ").append(toIndentedString(filesize)).append("\n");
    sb.append("    tbr: ").append(toIndentedString(tbr)).append("\n");
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
