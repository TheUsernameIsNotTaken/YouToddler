package io.youtoddler.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

/**
 * Audio
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-22T11:09:03.174853304Z[GMT]")


public class Audio   {
  @JsonProperty("audioCodec")
  private String audioCodec = null;

  @JsonProperty("abr")
  private Long abr = null;

  public Audio audioCodec(String audioCodec) {
    this.audioCodec = audioCodec;
    return this;
  }

  /**
   * Get audioCodec
   * @return audioCodec
   **/
  @Schema(example = "mp4a", required = true, description = "")
      @NotNull

    public String getAudioCodec() {
    return audioCodec;
  }

  public void setAudioCodec(String audioCodec) {
    this.audioCodec = audioCodec;
  }

  public Audio abr(Long abr) {
    this.abr = abr;
    return this;
  }

  /**
   * Get abr
   * @return abr
   **/
  @Schema(example = "0", description = "")
  
    public Long getAbr() {
    return abr;
  }

  public void setAbr(Long abr) {
    this.abr = abr;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Audio audio = (Audio) o;
    return Objects.equals(this.audioCodec, audio.audioCodec) &&
        Objects.equals(this.abr, audio.abr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(audioCodec, abr);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Audio {\n");
    
    sb.append("    audioCodec: ").append(toIndentedString(audioCodec)).append("\n");
    sb.append("    abr: ").append(toIndentedString(abr)).append("\n");
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
