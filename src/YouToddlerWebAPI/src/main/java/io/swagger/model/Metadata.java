package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Format;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Metadata
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-06T21:09:37.363059348Z[GMT]")


public class Metadata   {
  @JsonProperty("url")
  private String url = null;

  @JsonProperty("videoName")
  private String videoName = null;

  @JsonProperty("imageUrl")
  private String imageUrl = null;

  @JsonProperty("formats")
  @Valid
  private List<Format> formats = null;

  public Metadata url(String url) {
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

  public Metadata videoName(String videoName) {
    this.videoName = videoName;
    return this;
  }

  /**
   * Get videoName
   * @return videoName
   **/
  @Schema(example = "Example Name gone wrong! | !!!NOT CLICKBAIT!!!", description = "")
  
    public String getVideoName() {
    return videoName;
  }

  public void setVideoName(String videoName) {
    this.videoName = videoName;
  }

  public Metadata imageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  /**
   * Get imageUrl
   * @return imageUrl
   **/
  @Schema(example = "https://i.ytimg.com/vi/dQw4w9WgXcQ/hq720.jpg", description = "")
  
    public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Metadata formats(List<Format> formats) {
    this.formats = formats;
    return this;
  }

  public Metadata addFormatsItem(Format formatsItem) {
    if (this.formats == null) {
      this.formats = new ArrayList<Format>();
    }
    this.formats.add(formatsItem);
    return this;
  }

  /**
   * Get formats
   * @return formats
   **/
  @Schema(description = "")
      @Valid
    public List<Format> getFormats() {
    return formats;
  }

  public void setFormats(List<Format> formats) {
    this.formats = formats;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Metadata metadata = (Metadata) o;
    return Objects.equals(this.url, metadata.url) &&
        Objects.equals(this.videoName, metadata.videoName) &&
        Objects.equals(this.imageUrl, metadata.imageUrl) &&
        Objects.equals(this.formats, metadata.formats);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, videoName, imageUrl, formats);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Metadata {\n");
    
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    videoName: ").append(toIndentedString(videoName)).append("\n");
    sb.append("    imageUrl: ").append(toIndentedString(imageUrl)).append("\n");
    sb.append("    formats: ").append(toIndentedString(formats)).append("\n");
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
