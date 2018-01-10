package main.java.erp.backend.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import main.java.erp.backend.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Employee
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-20T01:05:47.562Z")

@Entity
public class Employee extends BaseModel {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("name")
  @NotNull
  @Column(nullable = false, unique = false)
  private String name;

  @JsonProperty("telephone")
  private String telephone = null;

  @JsonProperty("mailField")
  @NotNull
  @Column(nullable = false, unique = true)
  private String mail;

  @JsonProperty("password")
  @NotNull
  @Column(nullable = false, unique = false)
  private String password;

  public Employee() {}

  public Employee(Integer id, String name, String mail, String password, Role role) {
    this.id = id;
    this.name = name;
    this.mail = mail;
    this.password = password;
    this.role = role;
  }

  public Employee(Integer id) {
    this.id = id;
  }

  /**
   * Gets or Sets role
   */
  public enum Role {
    ERP("erp"),
    CRM("crm"),
    ADMIN("admin");

    private String value;

    Role(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static Role fromValue(String text) {
      for (Role b : Role.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("role")
  @NotNull
  private Role role;

  @JsonProperty("monthSchedule")
  private BigDecimal monthSchedule = null;

  @JsonProperty("visibility")
  @NotNull
  private Boolean visibility = true;

  public Employee id(Integer id) {
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

  public Employee name(String name) {
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

  public Employee telephone(String telephone) {
    this.telephone = telephone;
    return this;
  }

  /**
   * Get telephone
   * @return telephone
   **/
  @ApiModelProperty(value = "")

  @Size(min=5,max=15)
  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public Employee mail(String mail) {
    this.mail = mail;
    return this;
  }

  /**
   * Get mailField
   * @return mailField
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public Employee asd(String asd) {
    this.password = asd;
    return this;
  }

  /**
   * required on post, ignored on update, \"***\" on get
   * @return password
   **/
  @ApiModelProperty(required = true, value = "required on post, ignored on update, \"***\" on get")
  @NotNull


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Employee role(Role role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   * @return role
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Employee monthSchedule(BigDecimal monthSchedule) {
    this.monthSchedule = monthSchedule;
    return this;
  }

  /**
   * Get monthSchedule
   * @return monthSchedule
   **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMonthSchedule() {
    return monthSchedule;
  }

  public void setMonthSchedule(BigDecimal monthSchedule) {
    this.monthSchedule = monthSchedule;
  }

  public Employee visibility(Boolean visibility) {
    this.visibility = visibility;
    return this;
  }

  /**
   * Get visibility
   * @return visibility
   **/
  @ApiModelProperty(value = "")


  public Boolean getVisibility() {
    return visibility;
  }

  public void setVisibility(Boolean visibility) {
    this.visibility = visibility;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Employee employee = (Employee) o;
    return Objects.equals(this.id, employee.id) &&
            Objects.equals(this.name, employee.name) &&
            Objects.equals(this.telephone, employee.telephone) &&
            Objects.equals(this.mail, employee.mail) &&
            Objects.equals(this.password, employee.password) &&
            Objects.equals(this.role, employee.role) &&
            Objects.equals(this.monthSchedule, employee.monthSchedule) &&
            Objects.equals(this.visibility, employee.visibility);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, telephone, mail, password, role, monthSchedule, visibility);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Employee {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    telephone: ").append(toIndentedString(telephone)).append("\n");
    sb.append("    mailField: ").append(toIndentedString(mail)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    monthSchedule: ").append(toIndentedString(monthSchedule)).append("\n");
    sb.append("    visibility: ").append(toIndentedString(visibility)).append("\n");
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
}
