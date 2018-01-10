package io.swagger.model.erp;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.BaseModel;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Proforma
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
@Entity
public class Proforma extends BaseModel {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("proformaNumber")
  @NotNull
  @Column(nullable = false, unique = true)
  private String proformaNumber;

  @JsonProperty("order")
  @NotNull
  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "order_id", unique = true)
  private Order_ order;

  @JsonProperty("issueDate")
  @NotNull
  private LocalDate issueDate ;

  @JsonProperty("saleDate")
  @NotNull
  private LocalDate saleDate;

  @JsonProperty("paymentDate")
  @NotNull
  private LocalDate paymentDate;

  @JsonProperty("paymentMethod")
  @NotNull
  private String paymentMethod;

  public Proforma id(Integer id) {
    this.id = id;
    return this;
  }

  protected Proforma() {}

  public Proforma(Integer id, String proformaNumber, Order_ order, LocalDate issueDate, LocalDate saleDate, LocalDate paymentDate, String paymentMethod) {
      this.id = id;
      this.proformaNumber = proformaNumber;
      this.order = order;
      this.issueDate = issueDate;
      this.saleDate = saleDate;
      this.paymentDate = paymentDate;
      this.paymentMethod = paymentMethod;
  }

  public Proforma(Integer id) {
      this.id = id;
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

  public Proforma proformaNumber(String proformaNumber) {
    this.proformaNumber = proformaNumber;
    return this;
  }

   /**
   * Get proformaNumber
   * @return proformaNumber
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

 @Size(min=5,max=32)
  public String getProformaNumber() {
    return proformaNumber;
  }

  public void setProformaNumber(String proformaNumber) {
    this.proformaNumber = proformaNumber;
  }

  public Proforma orderId(Order_ order) {
    this.order = order;
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

  public Proforma issueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
    return this;
  }

   /**
   * Get issueDate
   * @return issueDate
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
  }

  public Proforma saleDate(LocalDate saleDate) {
    this.saleDate = saleDate;
    return this;
  }

   /**
   * Get saleDate
   * @return saleDate
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public LocalDate getSaleDate() {
    return saleDate;
  }

  public void setSaleDate(LocalDate saleDate) {
    this.saleDate = saleDate;
  }

  public Proforma paymentDate(LocalDate paymentDate) {
    this.paymentDate = paymentDate;
    return this;
  }

   /**
   * Get paymentDate
   * @return paymentDate
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public LocalDate getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(LocalDate paymentDate) {
    this.paymentDate = paymentDate;
  }

  public Proforma paymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
    return this;
  }

   /**
   * Get paymentMethod
   * @return paymentMethod
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

 @Size(max=100)
  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Proforma proforma = (Proforma) o;
    return Objects.equals(this.id, proforma.id) &&
        Objects.equals(this.proformaNumber, proforma.proformaNumber) &&
        Objects.equals(this.order, proforma.order) &&
        Objects.equals(this.issueDate, proforma.issueDate) &&
        Objects.equals(this.saleDate, proforma.saleDate) &&
        Objects.equals(this.paymentDate, proforma.paymentDate) &&
        Objects.equals(this.paymentMethod, proforma.paymentMethod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, proformaNumber, order, issueDate, saleDate, paymentDate, paymentMethod);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Proforma {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    proformaNumber: ").append(toIndentedString(proformaNumber)).append("\n");
    sb.append("    order: ").append(toIndentedString(order)).append("\n");
    sb.append("    issueDate: ").append(toIndentedString(issueDate)).append("\n");
    sb.append("    saleDate: ").append(toIndentedString(saleDate)).append("\n");
    sb.append("    paymentDate: ").append(toIndentedString(paymentDate)).append("\n");
    sb.append("    paymentMethod: ").append(toIndentedString(paymentMethod)).append("\n");
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

