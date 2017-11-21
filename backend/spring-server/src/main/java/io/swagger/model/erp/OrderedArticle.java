package io.swagger.model.erp;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.BaseModel;
import io.swagger.models.auth.In;

import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OrderedArticle
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
@Entity
public class OrderedArticle extends BaseModel {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("article")
  @NotNull
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "article_id")
  private Article article;

  @JsonProperty("order")
  @NotNull
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "order_id")
  private Order_ order;

  @JsonProperty("description")
  private String description = "";

  @JsonProperty("amount")
  @NotNull
  @Column(nullable = false, unique = false)
  private Integer amount;

  @JsonProperty("unitPrice")
  private BigDecimal unitPrice = null;

  @JsonProperty("netPrice")
  private BigDecimal netPrice = null;

  @JsonProperty("weight")
  private Float weight = null;

  protected OrderedArticle() {}

  public OrderedArticle(Integer id, Article article, Order_ order, String description, Integer amount) {
    this.id = id;
    this.article = article;
    this.order = order;
    this.description = description;
    this.amount = amount;
  }

  public OrderedArticle(Integer id) {
    this.id = id;
  }

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

  public OrderedArticle articleId(Article article) {
    this.article = article;
    return this;
  }

   /**
   * Get article
   * @return article
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Article getArticle() {
    return article;
  }

  public void setArticle(Article article) {
    this.article = article;
  }

  public OrderedArticle orderId(Order_ orderId) {
    this.order = orderId;
    return this;
  }

   /**
   * Get order
   * @return order
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Order_ getOrder() {
    return order;
  }

  public void setOrder(Order_ order) {
    this.order = order;
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
        Objects.equals(this.article, orderedArticle.article) &&
        Objects.equals(this.order, orderedArticle.order) &&
        Objects.equals(this.description, orderedArticle.description) &&
        Objects.equals(this.amount, orderedArticle.amount) &&
        Objects.equals(this.unitPrice, orderedArticle.unitPrice) &&
        Objects.equals(this.netPrice, orderedArticle.netPrice) &&
        Objects.equals(this.weight, orderedArticle.weight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, article, order, description, amount, unitPrice, netPrice, weight);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderedArticle {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    article: ").append(toIndentedString(article)).append("\n");
    sb.append("    order: ").append(toIndentedString(order)).append("\n");
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

