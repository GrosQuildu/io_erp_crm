package main.java.erp_crm.backend.model.crm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * ContactGroup
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
@Entity
public class ContactGroup {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("description")
  @Size(min=5,max=100)
  @Column(nullable = false, unique = false)
  private String description;

  public ContactGroup() {}

  public ContactGroup(Integer id, String description) {
      this.id = id;
      this.description = description;
  }

    public ContactGroup id(Integer id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(required = true, value = "")


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ContactGroup description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(required = true, value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ContactGroup contactGroup = (ContactGroup) o;
    return Objects.equals(this.id, contactGroup.id) &&
        Objects.equals(this.description, contactGroup.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ContactGroup {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  public String serialize(){
    Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
    return gson.toJson(this);
  }
}

