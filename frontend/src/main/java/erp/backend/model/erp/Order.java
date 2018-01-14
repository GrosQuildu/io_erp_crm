package main.java.erp.backend.model.erp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.*;
import io.swagger.annotations.ApiModelProperty;
import main.java.erp.backend.model.BaseModel;
import main.java.erp.backend.model.common.Client;
import main.java.erp.backend.model.common.Employee;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Order
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-16T02:31:17.737Z")
@Entity
public class Order extends BaseModel {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("orderNumber")
  @NotNull
  @Column(nullable = false, unique = true)
  private String orderNumber;

  @JsonProperty("orderDate")
  @NotNull
  @Column(nullable = false, unique = false)
  private LocalDate orderDate;

  @JsonProperty("realizationDate")
  private LocalDate realizationDate = null;

  @JsonProperty("realizationDeadline")
  private String realizationDeadline = null;

  @JsonProperty("employee")
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "employee_id")
  private Employee employee = null;

  @JsonProperty("client")
  @NotNull
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "client_id")
  private Client client;

  @JsonProperty("conditions")
  private String conditions = "";

  @JsonProperty("comments")
  private String comments = "";

  @JsonProperty("advance")
  private BigDecimal advance = null;

  @JsonProperty("vat")
  @NotNull
  @Column(nullable = false, unique = false)
  private Float vat;

  @JsonProperty("state")
  @NotNull
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

  private List<OrderedArticle> orderedArticles = new LinkedList<>();

  public Order() {}

  public Order(Integer id, String orderNumber, LocalDate orderDate, Client client, Float vat, String state) {
    this.id = id;
    this.orderNumber = orderNumber;
    this.orderDate = orderDate;
    this.client = client;
    this.vat = vat;
    this.state = state;
  }

  public Order(Integer id) {
    this.id = id;
  }

  public Order id(Integer id) {
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

  public Order orderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
    return this;
  }

  /**
   * Get orderNumber
   * @return orderNumber
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(max=32)
  public String getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  public Order orderDate(LocalDate orderDate) {
    this.orderDate = orderDate;
    return this;
  }

  /**
   * Get orderDate
   * @return orderDate
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public LocalDate getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDate orderDate) {
    this.orderDate = orderDate;
  }

  public Order realizationDate(LocalDate realizationDate) {
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

  public Order realizationDeadline(String realizationDeadline) {
    this.realizationDeadline = realizationDeadline;
    return this;
  }

  /**
   * Get realizationDeadline
   * @return realizationDeadline
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(max=100)
  public String getRealizationDeadline() {
    return realizationDeadline;
  }

  public void setRealizationDeadline(String realizationDeadline) {
    this.realizationDeadline = realizationDeadline;
  }

  public Order employeeId(Employee employee) {
    this.employee = employee;
    return this;
  }

  /**
   * Get employee
   * @return employee
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public Order clientId(Client client) {
    this.client = client;
    return this;
  }

  /**
   * Get client
   * @return client
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public Order conditions(String conditions) {
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

  public Order comments(String comments) {
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

  public Order advance(BigDecimal advance) {
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

  public Order vat(Float vat) {
    this.vat = vat;
    return this;
  }

  /**
   * Get vat
   * @return vat
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Float getVat() {
    return vat;
  }

  public void setVat(Float vat) {
    this.vat = vat;
  }

  public Order state(String state) {
    this.state = state;
    return this;
  }

  /**
   * Get state
   * @return state
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(max=100)
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Order deliveryCost(BigDecimal deliveryCost) {
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

  public Order deliveryAddress(String deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
    return this;
  }

  /**
   * Get deliveryAddress
   * @return deliveryAddress
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getDeliveryAddress() {
    return deliveryAddress;
  }

  public void setDeliveryAddress(String deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  public Order isSigned(Boolean isSigned) {
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

  public Order isPaid(String isPaid) {
    this.isPaid = isPaid;
    return this;
  }

  /**
   * Get isPaid
   * @return isPaid
   **/
  @ApiModelProperty(value = "")

  @Size(max=100)
  public String getIsPaid() {
    return isPaid;
  }

  public void setIsPaid(String isPaid) {
    this.isPaid = isPaid;
  }

  public Order isDone(Boolean isDone) {
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

  public List<OrderedArticle> getOrderedArticles() {
    return orderedArticles;
  }

  public void setOrderedArticles(List<OrderedArticle> orderedArticles) {
    this.orderedArticles = orderedArticles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return Objects.equals(this.id, order.id) &&
            Objects.equals(this.orderNumber, order.orderNumber) &&
            Objects.equals(this.orderDate, order.orderDate) &&
            Objects.equals(this.realizationDate, order.realizationDate) &&
            Objects.equals(this.realizationDeadline, order.realizationDeadline) &&
            Objects.equals(this.employee, order.employee) &&
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
    return Objects.hash(id, orderNumber, orderDate, realizationDate, realizationDeadline, employee, client, conditions, comments, advance, vat, state, deliveryCost, deliveryAddress, isSigned, isPaid, isDone);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Order {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    orderNumber: ").append(toIndentedString(orderNumber)).append("\n");
    sb.append("    orderDate: ").append(toIndentedString(orderDate)).append("\n");
    sb.append("    realizationDate: ").append(toIndentedString(realizationDate)).append("\n");
    sb.append("    realizationDeadline: ").append(toIndentedString(realizationDeadline)).append("\n");
    sb.append("    employee: ").append(toIndentedString(employee)).append("\n");
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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  public String serialize(){
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Order.class, new OrderSerializer());
    gsonBuilder.setPrettyPrinting();
    final Gson gson = gsonBuilder.create();
    return gson.toJson(this);
  }

  class OrderSerializer implements JsonSerializer<Order> {

    @Override
    public JsonElement serialize(final Order order, final Type typeOfSrc, final JsonSerializationContext context) {
      JsonObject object = new JsonObject();
      SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
      object.addProperty("id", id);
      object.addProperty("orderNumber", orderNumber);
      object.addProperty("orderDate", format.format(orderDate.toDate()));
      object.addProperty("realizationDate", format.format(realizationDate.toDate()));
      object.addProperty("realizationDeadline", format.format(Date.valueOf(realizationDeadline)));
      object.addProperty("conditions", conditions);
      object.addProperty("comments", comments);
      object.addProperty("advance", advance);
      object.addProperty("vat", vat);
      object.addProperty("state", state);
      object.addProperty("deliveryCost", deliveryCost);
      object.addProperty("deliveryAddress", deliveryAddress);
      object.addProperty("isPaid", isPaid);
      object.addProperty("isSigned", isSigned);
      object.addProperty("isDone", isDone);
      object.addProperty("employee", employee.getId());
      JsonObject clientObject = new JsonObject();
      clientObject.addProperty("id", client.getId());
      object.add("client", clientObject);


      JsonArray articles = new JsonArray();

      for(OrderedArticle i : orderedArticles){
        JsonObject orderedArt = new JsonObject();
        orderedArt.addProperty("id", i.getId());
        orderedArt.addProperty("description", i.getDescription());
        orderedArt.addProperty("unitPrice", i.getUnitPrice());
        orderedArt.addProperty("amount", i.getAmount());
        orderedArt.addProperty("netPrice", i.getNetPrice());
        orderedArt.addProperty("weight", i.getWeight());

        JsonObject articleObject = new JsonObject();
        articleObject.addProperty("id", i.getArticle().getId());
        orderedArt.add("article", articleObject);

        articles.add(orderedArt);
      }
      
      object.add("orderedArticles", articles);
      return object;
    }
  }
}
