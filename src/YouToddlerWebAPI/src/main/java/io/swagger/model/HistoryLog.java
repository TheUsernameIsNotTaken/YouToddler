package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * HistoryLog
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-22T11:09:03.174853304Z[GMT]")


public class HistoryLog   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("url")
  private String url = null;

  @JsonProperty("videoId")
  private Long videoId = null;

  @JsonProperty("audioId")
  private Long audioId = null;

  @JsonProperty("date")
  private OffsetDateTime date = null;

  public HistoryLog id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(example = "1", required = true, description = "")
      @NotNull

    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public HistoryLog url(String url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
   **/
  @Schema(example = "https://youtu.be/dQw4w9WgXcQ", required = true, description = "")
      @NotNull

    public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public HistoryLog videoId(Long videoId) {
    this.videoId = videoId;
    return this;
  }

  /**
   * Get videoId
   * @return videoId
   **/
  @Schema(example = "30", description = "")
  
    public Long getVideoId() {
    return videoId;
  }

  public void setVideoId(Long videoId) {
    this.videoId = videoId;
  }

  public HistoryLog audioId(Long audioId) {
    this.audioId = audioId;
    return this;
  }

  /**
   * Get audioId
   * @return audioId
   **/
  @Schema(example = "20", description = "")
  
    public Long getAudioId() {
    return audioId;
  }

  public void setAudioId(Long audioId) {
    this.audioId = audioId;
  }

  public HistoryLog date(OffsetDateTime date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
   **/
  @Schema(example = "2022-12-10T13:45Z", description = "")
  
    @Valid
    public OffsetDateTime getDate() {
    return date;
  }

  public void setDate(OffsetDateTime date) {
    this.date = date;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HistoryLog historyLog = (HistoryLog) o;
    return Objects.equals(this.id, historyLog.id) &&
        Objects.equals(this.url, historyLog.url) &&
        Objects.equals(this.videoId, historyLog.videoId) &&
        Objects.equals(this.audioId, historyLog.audioId) &&
        Objects.equals(this.date, historyLog.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, url, videoId, audioId, date);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HistoryLog {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    videoId: ").append(toIndentedString(videoId)).append("\n");
    sb.append("    audioId: ").append(toIndentedString(audioId)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
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
