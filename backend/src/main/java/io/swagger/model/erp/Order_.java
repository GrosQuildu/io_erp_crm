package io.swagger.model.erp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;

import io.swagger.model.BaseModel;
import io.swagger.model.common.Client;
import io.swagger.model.common.Employee;
import org.hibernate.annotations.*;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.Valid;

/**
 * Order_
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-16T02:31:17.737Z")
@Entity
public class Order_ extends BaseModel {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("orderNumber")
  @Column(nullable = false, unique = true)
  private String orderNumber;

  @JsonProperty("orderDate")
  @Column(nullable = false, unique = false)
  @JsonDeserialize(using=LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate orderDate;

  @JsonProperty("realizationDate")
  @JsonDeserialize(using=LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate realizationDate = null;

  @JsonProperty("realizationDeadline")
  private String realizationDeadline = null;

  @JsonProperty("employee")
  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee = null;

  @JsonProperty("orderedArticles")
  @OneToMany(orphanRemoval=true)
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  private List<OrderedArticle> orderedArticles = null;

  @JsonProperty("client")
  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  @JsonProperty("conditions")
  private String conditions = "";

  @JsonProperty("comments")
  private String comments = "";

  @JsonProperty("advance")
  private BigDecimal advance = null;

  @JsonProperty("vat")
  @Column(nullable = false, unique = false)
  private Float vat;

  @JsonProperty("state")
  @Column(nullable = false, unique = false)
  private String state;

  @JsonProperty("deliveryCost")
  private BigDecimal deliveryCost = null;

  @JsonProperty("deliveryAddress")
  private String deliveryAddress = null;

  @JsonProperty("isSigned")
  private Boolean isSigned = false;

  @JsonProperty("isPaid")
  private String isPaid = "false";

  @JsonProperty("isDone")
  private Boolean isDone = false;

  protected Order_() {}

  public Order_(Integer id, String orderNumber, LocalDate orderDate, Client client, Float vat, String state) {
    this.id = id;
    this.orderNumber = orderNumber;
    this.orderDate = orderDate;
    this.client = client;
    this.vat = vat;
    this.state = state;
  }

  public Order_(Integer id) {
    this.id = id;
  }

  public Order_ id(Integer id) {
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

  public Order_ orderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
    return this;
  }

  /**
   * Get orderNumber
   * @return orderNumber
   **/
  @ApiModelProperty(required = true, value = "")



  public String getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  public Order_ orderDate(LocalDate orderDate) {
    this.orderDate = orderDate;
    return this;
  }

  /**
   * Get orderDate
   * @return orderDate
   **/
  @ApiModelProperty(required = true, value = "")


  @Valid

  public LocalDate getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDate orderDate) {
    this.orderDate = orderDate;
  }

  public Order_ realizationDate(LocalDate realizationDate) {
    this.realizationDate = realizationDate;
    return this;
  }

  /**
   * Get realizationDate
   * @return realizationDate
   **/
  @ApiModelProperty(value = "")

  @Valid

  public LocalDate getRealizationDate() {
    return realizationDate;
  }

  public void setRealizationDate(LocalDate realizationDate) {
    this.realizationDate = realizationDate;
  }

  public Order_ realizationDeadline(String realizationDeadline) {
    this.realizationDeadline = realizationDeadline;
    return this;
  }

  /**
   * Get realizationDeadline
   * @return realizationDeadline
   **/
  @ApiModelProperty(required = true, value = "")

  public String getRealizationDeadline() {
    return realizationDeadline;
  }

  public void setRealizationDeadline(String realizationDeadline) {
    this.realizationDeadline = realizationDeadline;
  }

  public Order_ employeeId(Employee employee) {
    this.employee = employee;
    return this;
  }

  /**
   * Get employee
   * @return employee
   **/
  @ApiModelProperty(required = true, value = "")



  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }


  public Order_ orderedArticles(List<OrderedArticle> orderedArticles) {
    this.orderedArticles = orderedArticles;
    return this;
  }

  public Order_ addorderedArticlesItem(OrderedArticle orderedArticlesItem) {
    if (this.orderedArticles == null) {
      this.orderedArticles = new ArrayList<OrderedArticle>();
    }
    this.orderedArticles.add(orderedArticlesItem);
    return this;
  }

  /**
   * Get orderedArticles
   * @return orderedArticles
   **/
  @ApiModelProperty(value = "orderedArticles can be created, updated and deleted with POST and PUT")
  @Valid

  public List<OrderedArticle> getorderedArticles() {
    return orderedArticles;
  }

  public void setorderedArticles(List<OrderedArticle> orderedArticles) {
    if(this.orderedArticles == null)
      this.orderedArticles = orderedArticles;
    else {
      this.orderedArticles.clear();
      this.orderedArticles.addAll(orderedArticles);
    }
  }

  public Order_ clientId(Client client) {
    this.client = client;
    return this;
  }

  /**
   * Get client
   * @return client
   **/
  @ApiModelProperty(required = true, value = "")



  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public Order_ conditions(String conditions) {
    this.conditions = conditions;
    return this;
  }

  /**
   * Get conditions
   * @return conditions
   **/
  @ApiModelProperty(value = "")


  public String getConditions() {
    return conditions;
  }

  public void setConditions(String conditions) {
    this.conditions = conditions;
  }

  public Order_ comments(String comments) {
    this.comments = comments;
    return this;
  }

  /**
   * Get comments
   * @return comments
   **/
  @ApiModelProperty(value = "")


  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public Order_ advance(BigDecimal advance) {
    this.advance = advance;
    return this;
  }

  /**
   * Get advance
   * @return advance
   **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getAdvance() {
    return advance;
  }

  public void setAdvance(BigDecimal advance) {
    this.advance = advance;
  }

  public Order_ vat(Float vat) {
    this.vat = vat;
    return this;
  }

  /**
   * Get vat
   * @return vat
   **/
  @ApiModelProperty(required = true, value = "")



  public Float getVat() {
    return vat;
  }

  public void setVat(Float vat) {
    this.vat = vat;
  }

  public Order_ state(String state) {
    this.state = state;
    return this;
  }

  /**
   * Get state
   * @return state
   **/
  @ApiModelProperty(required = true, value = "")


  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Order_ deliveryCost(BigDecimal deliveryCost) {
    this.deliveryCost = deliveryCost;
    return this;
  }

  /**
   * Get deliveryCost
   * @return deliveryCost
   **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getDeliveryCost() {
    return deliveryCost;
  }

  public void setDeliveryCost(BigDecimal deliveryCost) {
    this.deliveryCost = deliveryCost;
  }

  public Order_ deliveryAddress(String deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
    return this;
  }

  /**
   * Get deliveryAddress
   * @return deliveryAddress
   **/
  @ApiModelProperty(required = true, value = "")



  public String getDeliveryAddress() {
    return deliveryAddress;
  }

  public void setDeliveryAddress(String deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  public Order_ isSigned(Boolean isSigned) {
    this.isSigned = isSigned;
    return this;
  }

  /**
   * Get isSigned
   * @return isSigned
   **/
  @ApiModelProperty(value = "")


  public Boolean getIsSigned() {
    return isSigned;
  }

  public void setIsSigned(Boolean isSigned) {
    this.isSigned = isSigned;
  }

  public Order_ isPaid(String isPaid) {
    this.isPaid = isPaid;
    return this;
  }

  /**
   * Get isPaid
   * @return isPaid
   **/
  @ApiModelProperty(value = "")

  public String getIsPaid() {
    return isPaid;
  }

  public void setIsPaid(String isPaid) {
    this.isPaid = isPaid;
  }

  public Order_ isDone(Boolean isDone) {
    this.isDone = isDone;
    return this;
  }

  /**
   * Get isDone
   * @return isDone
   **/
  @ApiModelProperty(value = "")


  public Boolean getIsDone() {
    return isDone;
  }

  public void setIsDone(Boolean isDone) {
    this.isDone = isDone;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order_ order = (Order_) o;
    return Objects.equals(this.id, order.id) &&
            Objects.equals(this.orderNumber, order.orderNumber) &&
            Objects.equals(this.orderDate, order.orderDate) &&
            Objects.equals(this.realizationDate, order.realizationDate) &&
            Objects.equals(this.realizationDeadline, order.realizationDeadline) &&
            Objects.equals(this.employee, order.employee) &&
            Objects.equals(this.orderedArticles, order.orderedArticles) &&
            Objects.equals(this.client, order.client) &&
            Objects.equals(this.conditions, order.conditions) &&
            Objects.equals(this.comments, order.comments) &&
            Objects.equals(this.advance, order.advance) &&
            Objects.equals(this.vat, order.vat) &&
            Objects.equals(this.state, order.state) &&
            Objects.equals(this.deliveryCost, order.deliveryCost) &&
            Objects.equals(this.deliveryAddress, order.deliveryAddress) &&
            Objects.equals(this.isSigned, order.isSigned) &&
            Objects.equals(this.isPaid, order.isPaid) &&
            Objects.equals(this.isDone, order.isDone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, orderNumber, orderDate, realizationDate, realizationDeadline, employee, orderedArticles, client,
            conditions, comments, advance, vat, state, deliveryCost, deliveryAddress, isSigned, isPaid, isDone);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Order_ {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    orderNumber: ").append(toIndentedString(orderNumber)).append("\n");
    sb.append("    orderDate: ").append(toIndentedString(orderDate)).append("\n");
    sb.append("    realizationDate: ").append(toIndentedString(realizationDate)).append("\n");
    sb.append("    realizationDeadline: ").append(toIndentedString(realizationDeadline)).append("\n");
    sb.append("    employee: ").append(toIndentedString(employee)).append("\n");
    sb.append("    orderedArticles: ").append(toIndentedString(orderedArticles)).append("\n");
    sb.append("    client: ").append(toIndentedString(client)).append("\n");
    sb.append("    conditions: ").append(toIndentedString(conditions)).append("\n");
    sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
    sb.append("    advance: ").append(toIndentedString(advance)).append("\n");
    sb.append("    vat: ").append(toIndentedString(vat)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    deliveryCost: ").append(toIndentedString(deliveryCost)).append("\n");
    sb.append("    deliveryAddress: ").append(toIndentedString(deliveryAddress)).append("\n");
    sb.append("    isSigned: ").append(toIndentedString(isSigned)).append("\n");
    sb.append("    isPaid: ").append(toIndentedString(isPaid)).append("\n");
    sb.append("    isDone: ").append(toIndentedString(isDone)).append("\n");
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
