package io.swagger.model.erp;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.BaseModel;
import io.swagger.model.common.Unit;


import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * Article
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
@Entity
public class Article extends BaseModel {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("name")
  @Size(min=5,max=100)
  @Column(nullable = false, unique = true)
  private String name;

  @JsonProperty("availability")
  @Column(nullable = false, unique = false)
  private Integer availability;

  @JsonProperty("unit")
  @ManyToOne
  @JoinColumn(name = "unit_id")
  private Unit unit;

  @JsonProperty("unitPrice")
  @Column(nullable = false, unique = false)
  private BigDecimal unitPrice;

  @JsonProperty("weight")
  private Float weight = null;

  protected Article(){}

  public Article(Integer id, String name, Integer availability, Unit unit, BigDecimal unitPrice) {
    this.id = id;
    this.name = name;
    this.availability = availability;
    this.unit = unit;
    this.unitPrice = unitPrice;
  }

  public Article(Integer id) {
    this.id = id;
  }

  public Article id(Integer id) {
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


  public Article name(String name) {
    this.name = name;
    return this;
  }


  /**
   * Get name
   * @return name
   **/
  @ApiModelProperty(required = true, value = "")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }



  public Article availability(Integer availability) {
    this.availability = availability;
    return this;
  }

   /**
   * Get availability
   * @return availability
  **/
  @ApiModelProperty(required = true, value = "")


  public Integer getAvailability() {
    return availability;
  }

  public void setAvailability(Integer availability) {
    this.availability = availability;
  }



  public Article unitId(Unit unitId) {
    this.unit = unitId;
    return this;
  }

   /**
   * Get unit
   * @return unit
  **/
  @ApiModelProperty(required = true, value = "Not null")


  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  public Article unitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }

   /**
   * Get unitPrice
   * @return unitPrice
  **/
  @ApiModelProperty(required = true, value = "")


  @Valid

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public Article weight(Float weight) {
    this.weight = weight;
    return this;
  }

   /**
   * Get weight
   * @return weight
  **/
  @ApiModelProperty(value = "")


  public Float getWeight() {
    return weight;
  }

  public void setWeight(Float weight) {
    this.weight = weight;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Article article = (Article) o;
    return Objects.equals(this.id, article.id) &&
        Objects.equals(this.availability, article.availability) &&
        Objects.equals(this.unit, article.unit) &&
        Objects.equals(this.unitPrice, article.unitPrice) &&
        Objects.equals(this.weight, article.weight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, availability, unit, unitPrice, weight);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Article {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    availability: ").append(toIndentedString(availability)).append("\n");
    sb.append("    unit: ").append(toIndentedString(unit)).append("\n");
    sb.append("    unitPrice: ").append(toIndentedString(unitPrice)).append("\n");
    sb.append("    weight: ").append(toIndentedString(weight)).append("\n");
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

