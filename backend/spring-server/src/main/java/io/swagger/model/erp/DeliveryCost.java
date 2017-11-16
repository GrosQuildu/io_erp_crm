package io.swagger.model.erp;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DeliveryCost
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
@Entity
public class DeliveryCost   {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id = null;

  @JsonProperty("weightFrom")
  private Float weightFrom = null;

  @JsonProperty("weightTo")
  private Float weightTo = null;

  @JsonProperty("price")
  private BigDecimal price = null;

  public DeliveryCost id(Integer id) {
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

  public DeliveryCost weightFrom(Float weightFrom) {
    this.weightFrom = weightFrom;
    return this;
  }

   /**
   * Get weightFrom
   * @return weightFrom
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Float getWeightFrom() {
    return weightFrom;
  }

  public void setWeightFrom(Float weightFrom) {
    this.weightFrom = weightFrom;
  }

  public DeliveryCost weightTo(Float weightTo) {
    this.weightTo = weightTo;
    return this;
  }

   /**
   * Get weightTo
   * @return weightTo
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Float getWeightTo() {
    return weightTo;
  }

  public void setWeightTo(Float weightTo) {
    this.weightTo = weightTo;
  }

  public DeliveryCost price(BigDecimal price) {
    this.price = price;
    return this;
  }

   /**
   * Get price
   * @return price
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeliveryCost deliveryCost = (DeliveryCost) o;
    return Objects.equals(this.id, deliveryCost.id) &&
        Objects.equals(this.weightFrom, deliveryCost.weightFrom) &&
        Objects.equals(this.weightTo, deliveryCost.weightTo) &&
        Objects.equals(this.price, deliveryCost.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, weightFrom, weightTo, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeliveryCost {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    weightFrom: ").append(toIndentedString(weightFrom)).append("\n");
    sb.append("    weightTo: ").append(toIndentedString(weightTo)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
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

