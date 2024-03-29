package apimodels;

import com.fasterxml.jackson.annotation.*;
import java.util.Set;
import javax.validation.*;
import java.util.Objects;
import javax.validation.constraints.*;
/**
 * Attribute
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-11-12T23:40:58.720Z")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class Attribute   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("value")
  private String value = null;

  @JsonProperty("source")
  private String source = null;

  @JsonProperty("url")
  private String url = null;

  public Attribute name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the attribute.
   * @return name
  **/
  @NotNull
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Attribute value(String value) {
    this.value = value;
    return this;
  }

   /**
   * Value of the attribute.
   * @return value
  **/
  @NotNull
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Attribute source(String source) {
    this.source = source;
    return this;
  }

   /**
   * Transformer that produced the attribute's value.
   * @return source
  **/
  @NotNull
  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public Attribute url(String url) {
    this.url = url;
    return this;
  }

   /**
   * URL for additional information.
   * @return url
  **/
    public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Attribute attribute = (Attribute) o;
    return Objects.equals(name, attribute.name) &&
        Objects.equals(value, attribute.value) &&
        Objects.equals(source, attribute.source) &&
        Objects.equals(url, attribute.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, value, source, url);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Attribute {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
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

