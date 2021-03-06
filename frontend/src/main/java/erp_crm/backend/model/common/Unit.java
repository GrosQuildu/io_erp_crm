package main.java.erp_crm.backend.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.*;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Unit
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Entity
public class Unit  {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("name")
  @NotNull
  @Column(nullable = false, unique = false)
  private String name;

  @JsonProperty("nameShort")
  @NotNull
  @Column(nullable = false, unique = false)
  private String nameShort;

    public Unit() {}

  public Unit(Integer id, String name, String nameShort) {
      this.id = id;
      this.name = name;
      this.nameShort = nameShort;
  }

  public Unit(Integer id) {
      this.id = id;
  }

  public Unit id(Integer id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Unit name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

 @Size(max=100)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Unit nameShort(String nameShort) {
    this.nameShort = nameShort;
    return this;
  }

   /**
   * Get nameShort
   * @return nameShort
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

 @Size(max=32)
  public String getNameShort() {
    return nameShort;
  }

  public void setNameShort(String nameShort) {
    this.nameShort = nameShort;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Unit unit = (Unit) o;
    return Objects.equals(this.id, unit.id) &&
        Objects.equals(this.name, unit.name) &&
        Objects.equals(this.nameShort, unit.nameShort);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, nameShort);
  }

  @Override
  public String toString() {
      return name;
    /*StringBuilder sb = new StringBuilder();
    sb.append("class Unit {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    nameShort: ").append(toIndentedString(nameShort)).append("\n");
    sb.append("}");
    return sb.toString();*/
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
        return new Gson().toJson(this);
    }

}

