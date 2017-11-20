package io.swagger.model.common;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Client
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Entity
public class Client   {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("name")
  @NotNull
  private String name;

  @JsonProperty("street")
  @NotNull
  private String street = null;

  @JsonProperty("city")
  private String city = null;

  @JsonProperty("postCode")
  private String postCode = null;

  @JsonProperty("nameDelivery")
  private String nameDelivery = null;

  @JsonProperty("streetDelivery")
  private String streetDelivery = null;

  @JsonProperty("cityDelivery")
  private String cityDelivery = null;

  @JsonProperty("postCodeDelivery")
  private String postCodeDelivery = null;

  @JsonProperty("nip")
  private String nip = null;

  @JsonProperty("telephone")
  private String telephone = null;

  @JsonProperty("mail")
  @NotNull
  private String mail;

  @JsonProperty("clientType")
  @NotNull
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "client_type_id")
  private ClientType clientType;

  public Client id(Integer id) {
    this.id = id;
    return this;
  }

  protected Client() {}

  public Client(Integer id, String name, String mail, ClientType clientType) {
      this.id = id;
      this.name = name;
      this.mail = mail;
      this.clientType = clientType;
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

  public Client name(String name) {
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

  public Client street(String street) {
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

  public Client city(String city) {
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

  public Client postCode(String postCode) {
    this.postCode = postCode;
    return this;
  }

   /**
   * Get postCode
   * @return postCode
  **/
  @ApiModelProperty(value = "")


  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public Client nameDelivery(String nameDelivery) {
    this.nameDelivery = nameDelivery;
    return this;
  }

   /**
   * Get nameDelivery
   * @return nameDelivery
  **/
  @ApiModelProperty(value = "")

 @Size(min=5,max=100)
  public String getNameDelivery() {
    return nameDelivery;
  }

  public void setNameDelivery(String nameDelivery) {
    this.nameDelivery = nameDelivery;
  }

  public Client streetDelivery(String streetDelivery) {
    this.streetDelivery = streetDelivery;
    return this;
  }

   /**
   * Get streetDelivery
   * @return streetDelivery
  **/
  @ApiModelProperty(value = "")

 @Size(min=5,max=100)
  public String getStreetDelivery() {
    return streetDelivery;
  }

  public void setStreetDelivery(String streetDelivery) {
    this.streetDelivery = streetDelivery;
  }

  public Client cityDelivery(String cityDelivery) {
    this.cityDelivery = cityDelivery;
    return this;
  }

   /**
   * Get cityDelivery
   * @return cityDelivery
  **/
  @ApiModelProperty(value = "")

 @Size(min=5,max=100)
  public String getCityDelivery() {
    return cityDelivery;
  }

  public void setCityDelivery(String cityDelivery) {
    this.cityDelivery = cityDelivery;
  }

  public Client postCodeDelivery(String postCodeDelivery) {
    this.postCodeDelivery = postCodeDelivery;
    return this;
  }

   /**
   * Get postCodeDelivery
   * @return postCodeDelivery
  **/
  @ApiModelProperty(value = "")


  public String getPostCodeDelivery() {
    return postCodeDelivery;
  }

  public void setPostCodeDelivery(String postCodeDelivery) {
    this.postCodeDelivery = postCodeDelivery;
  }

  public Client nip(String nip) {
    this.nip = nip;
    return this;
  }

   /**
   * Get nip
   * @return nip
  **/
  @ApiModelProperty(value = "")

 @Size(min=10,max=20)
  public String getNip() {
    return nip;
  }

  public void setNip(String nip) {
    this.nip = nip;
  }

  public Client telephone(String telephone) {
    this.telephone = telephone;
    return this;
  }

   /**
   * Get telephone
   * @return telephone
  **/
  @ApiModelProperty(value = "")

 @Size(min=5,max=10)
  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public Client mail(String mail) {
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

  public Client clientTypeId(ClientType clientTypeId) {
    this.clientType = clientTypeId;
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Client client = (Client) o;
    return Objects.equals(this.id, client.id) &&
        Objects.equals(this.name, client.name) &&
        Objects.equals(this.street, client.street) &&
        Objects.equals(this.city, client.city) &&
        Objects.equals(this.postCode, client.postCode) &&
        Objects.equals(this.nameDelivery, client.nameDelivery) &&
        Objects.equals(this.streetDelivery, client.streetDelivery) &&
        Objects.equals(this.cityDelivery, client.cityDelivery) &&
        Objects.equals(this.postCodeDelivery, client.postCodeDelivery) &&
        Objects.equals(this.nip, client.nip) &&
        Objects.equals(this.telephone, client.telephone) &&
        Objects.equals(this.mail, client.mail) &&
        Objects.equals(this.clientType, client.clientType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, street, city, postCode, nameDelivery, streetDelivery, cityDelivery, postCodeDelivery, nip, telephone, mail, clientType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Client {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    street: ").append(toIndentedString(street)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    postCode: ").append(toIndentedString(postCode)).append("\n");
    sb.append("    nameDelivery: ").append(toIndentedString(nameDelivery)).append("\n");
    sb.append("    streetDelivery: ").append(toIndentedString(streetDelivery)).append("\n");
    sb.append("    cityDelivery: ").append(toIndentedString(cityDelivery)).append("\n");
    sb.append("    postCodeDelivery: ").append(toIndentedString(postCodeDelivery)).append("\n");
    sb.append("    nip: ").append(toIndentedString(nip)).append("\n");
    sb.append("    telephone: ").append(toIndentedString(telephone)).append("\n");
    sb.append("    mail: ").append(toIndentedString(mail)).append("\n");
    sb.append("    clientType: ").append(toIndentedString(clientType)).append("\n");
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

