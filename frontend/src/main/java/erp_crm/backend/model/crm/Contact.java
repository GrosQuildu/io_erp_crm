package main.java.erp_crm.backend.model.crm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.*;
import io.swagger.annotations.ApiModelProperty;
import main.java.erp_crm.backend.model.common.Client;
import main.java.erp_crm.backend.model.common.Employee;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Client
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
@Entity
public class Contact {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("client")
  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client = null;

  @JsonProperty("employee")
  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @JsonProperty("contactGroup")
  @ManyToOne
  @JoinColumn(name = "contact_group_id")
  private ContactGroup contactGroup;

  @JsonProperty("vip")
  private Boolean vip = false;

  @JsonProperty("name")
  @Size(min=5,max=100)
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
  @Size(min=8,max=256)
  @Column(nullable = false, unique = true)
  private String mail;

  public Contact() {}

  public Contact(Integer id, Employee employee, ContactGroup contactGroup, String name, String mail) {
      this.id = id;
      this.employee = employee;
      this.contactGroup = contactGroup;
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



  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public Contact contactGroup(ContactGroup contactGroup) {
    this.contactGroup = contactGroup;
    return this;
  }

   /**
   * Get contactGroup
   * @return contactGroup
  **/
  @ApiModelProperty(required = true, value = "")



  public ContactGroup getContactGroup() {
    return contactGroup;
  }

  public void setContactGroup(ContactGroup contactGroup) {
    this.contactGroup = contactGroup;
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



  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }


  @Override
  public boolean equals(Object o) {
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
        Objects.equals(this.contactGroup, contact.contactGroup) &&
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
    return Objects.hash(id, client, employee, contactGroup, vip, name, street, city, postCode, telephone, mail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Client {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    client: ").append(toIndentedString(client)).append("\n");
    sb.append("    employee: ").append(toIndentedString(employee)).append("\n");
    sb.append("    contactGroup: ").append(toIndentedString(contactGroup)).append("\n");
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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  public String serialize(){
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Contact.class, new ContactSerializer());
    //gsonBuilder.setPrettyPrinting();
    final Gson gson = gsonBuilder.create();
    return gson.toJson(this);
  }

  class ContactSerializer implements JsonSerializer<Contact> {

    @Override
    public JsonElement serialize(final Contact contact, final Type typeOfSrc, final JsonSerializationContext context) {
      JsonObject object = new JsonObject();
      object.addProperty("id", id);
      object.addProperty("name", name);
      object.addProperty("street", street);
      object.addProperty("city", city);
      object.addProperty("postCode", postCode);

      object.addProperty("telephone", telephone);
      object.addProperty("mail", mail);
      object.addProperty("vip", vip);

      object.addProperty("employee", employee.getId());
      JsonObject clientObject = new JsonObject();
      clientObject.addProperty("id", client.getId());
      object.add("client", clientObject);

      JsonObject contactGroupObject = new JsonObject();
      contactGroupObject.addProperty("id", contactGroup.getId());
      object.add("contactGroup", contactGroupObject);

      JsonObject employeeObject = new JsonObject();
      employeeObject.addProperty("id", employee.getId());
      object.add("employee", employeeObject);

      return object;
    }
  }
}

