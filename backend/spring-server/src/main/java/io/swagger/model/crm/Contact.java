package io.swagger.model.crm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.BaseModel;
import io.swagger.model.common.Client;
import io.swagger.model.common.ClientType;
import io.swagger.model.common.Employee;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Contact
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
@Entity
public class Contact extends BaseModel {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("client")
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "client_id")
  private Client client = null;

  @JsonProperty("employee")
  @NotNull
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @JsonProperty("clientType")
  @NotNull
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "clientType_id")
  private ClientType clientType;

  @JsonProperty("vip")
  private Boolean vip = false;

  @JsonProperty("name")
  @NotNull
  @Column(nullable = false, unique = false)
  private String name;

  @JsonProperty("street")
  private String street = null;

  @JsonProperty("city")
  private String city = null;

  @JsonProperty("postCode")
  private String postCode = null;

  @JsonProperty("telephone")
  private String telephone = null;

  @JsonProperty("mail")
  @NotNull
  @Column(nullable = false, unique = true)
  private String mail;

  protected Contact() {}

  public Contact(Integer id, Employee employee, ClientType clientType, String name, String mail) {
      this.id = id;
      this.employee = employee;
      this.clientType = clientType;
      this.name = name;
      this.mail = mail;
  }

  public Contact id(Integer id) {
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

  public Contact client(Client client) {
    this.client = client;
    return this;
  }

   /**
   * Get client
   * @return client
  **/
  @ApiModelProperty(value = "")


  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public Contact employee(Employee employee) {
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

  public Contact clientType(ClientType clientType) {
    this.clientType = clientType;
    return this;
  }

   /**
   * Get clientType
   * @return clientType
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public ClientType getClientType() {
    return clientType;
  }

  public void setClientType(ClientType clientType) {
    this.clientType = clientType;
  }

  public Contact vip(Boolean vip) {
    this.vip = vip;
    return this;
  }

   /**
   * Get vip
   * @return vip
  **/
  @ApiModelProperty(value = "")


  public Boolean getVip() {
    return vip;
  }

  public void setVip(Boolean vip) {
    this.vip = vip;
  }

  public Contact name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

 @Size(min=5,max=100)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Contact street(String street) {
    this.street = street;
    return this;
  }

   /**
   * Get street
   * @return street
  **/
  @ApiModelProperty(value = "")

 @Size(min=5,max=100)
  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Contact city(String city) {
    this.city = city;
    return this;
  }

   /**
   * Get city
   * @return city
  **/
  @ApiModelProperty(value = "")

 @Size(min=5,max=100)
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Contact postCode(String postCode) {
    this.postCode = postCode;
    return this;
  }

   /**
   * Get postCode
   * @return postCode
  **/
  @ApiModelProperty(value = "")

 @Size(min=5,max=100)
  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public Contact telephone(String telephone) {
    this.telephone = telephone;
    return this;
  }

   /**
   * Get telephone
   * @return telephone
  **/
  @ApiModelProperty(value = "")

 @Size(min=5,max=100)
  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public Contact mail(String mail) {
    this.mail = mail;
    return this;
  }

   /**
   * Get mail
   * @return mail
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Contact contact = (Contact) o;
    return Objects.equals(this.id, contact.id) &&
        Objects.equals(this.client, contact.client) &&
        Objects.equals(this.employee, contact.employee) &&
        Objects.equals(this.clientType, contact.clientType) &&
        Objects.equals(this.vip, contact.vip) &&
        Objects.equals(this.name, contact.name) &&
        Objects.equals(this.street, contact.street) &&
        Objects.equals(this.city, contact.city) &&
        Objects.equals(this.postCode, contact.postCode) &&
        Objects.equals(this.telephone, contact.telephone) &&
        Objects.equals(this.mail, contact.mail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, client, employee, clientType, vip, name, street, city, postCode, telephone, mail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Contact {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    client: ").append(toIndentedString(client)).append("\n");
    sb.append("    employee: ").append(toIndentedString(employee)).append("\n");
    sb.append("    clientType: ").append(toIndentedString(clientType)).append("\n");
    sb.append("    vip: ").append(toIndentedString(vip)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    street: ").append(toIndentedString(street)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    postCode: ").append(toIndentedString(postCode)).append("\n");
    sb.append("    telephone: ").append(toIndentedString(telephone)).append("\n");
    sb.append("    mail: ").append(toIndentedString(mail)).append("\n");
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

