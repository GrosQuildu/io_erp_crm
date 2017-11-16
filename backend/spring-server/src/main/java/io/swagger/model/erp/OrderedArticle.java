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
 * OrderedArticle
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
@Entity
public class OrderedArticle   {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id = null;

  @JsonProperty("articleId")
  private Integer articleId = null;

  @JsonProperty("orderId")
  private Integer orderId = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("amount")
  private Integer amount = null;

  @JsonProperty("unitPrice")
  private BigDecimal unitPrice = null;

  @JsonProperty("netPrice")
  private BigDecimal netPrice = null;

  @JsonProperty("weight")
  private Float weight = null;

  public OrderedArticle id(Integer id) {
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

  public OrderedArticle articleId(Integer articleId) {
    this.articleId = articleId;
    return this;
  }

   /**
   * Get articleId
   * @return articleId
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getArticleId() {
    return articleId;
  }

  public void setArticleId(Integer articleId) {
    this.articleId = articleId;
  }

  public OrderedArticle orderId(Integer orderId) {
    this.orderId = orderId;
    return this;
  }

   /**
   * Get orderId
   * @return orderId
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public OrderedArticle description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public OrderedArticle amount(Integer amount) {
    this.amount = amount;
    return this;
  }

   /**
   * Get amount
   * @return amount
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public OrderedArticle unitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }

   /**
   * Get unitPrice
   * @return unitPrice
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public OrderedArticle netPrice(BigDecimal netPrice) {
    this.netPrice = netPrice;
    return this;
  }

   /**
   * Get netPrice
   * @return netPrice
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public BigDecimal getNetPrice() {
    return netPrice;
  }

  public void setNetPrice(BigDecimal netPrice) {
    this.netPrice = netPrice;
  }

  public OrderedArticle weight(Float weight) {
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
    OrderedArticle orderedArticle = (OrderedArticle) o;
    return Objects.equals(this.id, orderedArticle.id) &&
        Objects.equals(this.articleId, orderedArticle.articleId) &&
        Objects.equals(this.orderId, orderedArticle.orderId) &&
        Objects.equals(this.description, orderedArticle.description) &&
        Objects.equals(this.amount, orderedArticle.amount) &&
        Objects.equals(this.unitPrice, orderedArticle.unitPrice) &&
        Objects.equals(this.netPrice, orderedArticle.netPrice) &&
        Objects.equals(this.weight, orderedArticle.weight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, articleId, orderId, description, amount, unitPrice, netPrice, weight);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderedArticle {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    articleId: ").append(toIndentedString(articleId)).append("\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    unitPrice: ").append(toIndentedString(unitPrice)).append("\n");
    sb.append("    netPrice: ").append(toIndentedString(netPrice)).append("\n");
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

