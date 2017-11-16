package io.swagger.model.crm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;

/**
 * Contact
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

public class Contact   {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("clientId")
  private Integer clientId = null;

  @JsonProperty("employeeId")
  private Integer employeeId = null;

  @JsonProperty("clientTypeId")
  private Integer clientTypeId = null;

  @JsonProperty("vip")
  private Boolean vip = false;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("street")
  private String street = null;

  @JsonProperty("city")
  private String city = null;

  @JsonProperty("postCode")
  private String postCode = null;

  @JsonProperty("telephone")
  private String telephone = null;

  @JsonProperty("mail")
  private String mail = null;

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

  public Contact clientId(Integer clientId) {
    this.clientId = clientId;
    return this;
  }

   /**
   * Get clientId
   * @return clientId
  **/
  @ApiModelProperty(value = "")


  public Integer getClientId() {
    return clientId;
  }

  public void setClientId(Integer clientId) {
    this.clientId = clientId;
  }

  public Contact employeeId(Integer employeeId) {
    this.employeeId = employeeId;
    return this;
  }

   /**
   * Get employeeId
   * @return employeeId
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Integer employeeId) {
    this.employeeId = employeeId;
  }

  public Contact clientTypeId(Integer clientTypeId) {
    this.clientTypeId = clientTypeId;
    return this;
  }

   /**
   * Get clientTypeId
   * @return clientTypeId
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getClientTypeId() {
    return clientTypeId;
  }

  public void setClientTypeId(Integer clientTypeId) {
    this.clientTypeId = clientTypeId;
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
        Objects.equals(this.clientId, contact.clientId) &&
        Objects.equals(this.employeeId, contact.employeeId) &&
        Objects.equals(this.clientTypeId, contact.clientTypeId) &&
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
    return Objects.hash(id, clientId, employeeId, clientTypeId, vip, name, street, city, postCode, telephone, mail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Contact {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    employeeId: ").append(toIndentedString(employeeId)).append("\n");
    sb.append("    clientTypeId: ").append(toIndentedString(clientTypeId)).append("\n");
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

