package main.java.erp_crm.backend.model.crm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateSerializer;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModelProperty;
import main.java.erp_crm.backend.model.common.Employee;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Objects;

/**
 * TaskComment
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
@Entity
public class TaskComment {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("content")
  @Column(nullable = false, unique = false)
  private String content;

  @JsonProperty("employee")
  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @JsonProperty("time")
  @Column(nullable = false, unique = false)
  @JsonDeserialize(using=LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate time;

  public TaskComment() {}

  public TaskComment(Integer id, Task task, String content, Employee employee, LocalDate time) {
    this.id = id;
    this.content = content;
    this.employee = employee;
    this.time = time;
  }

  public TaskComment id(Integer id) {
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


  public TaskComment content(String content) {
    this.content = content;
    return this;
  }

   /**
   * Get content
   * @return content
  **/
  @ApiModelProperty(required = true, value = "")



  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public TaskComment employee(Employee employee) {
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

  public TaskComment time(LocalDate time) {
    this.time = time;
    return this;
  }

   /**
   * Get time
   * @return time
  **/
  @ApiModelProperty(required = true, value = "")


  @Valid

  public LocalDate getTime() {
    return time;
  }

  public void setTime(LocalDate time) {
    this.time = time;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskComment taskComment = (TaskComment) o;
    return Objects.equals(this.id, taskComment.id) &&
        Objects.equals(this.content, taskComment.content) &&
        Objects.equals(this.employee, taskComment.employee) &&
        Objects.equals(this.time, taskComment.time);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, content, employee, time);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskComment {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    employee: ").append(toIndentedString(employee)).append("\n");
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
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

    public String serialize() {
      Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
      return gson.toJson(this);
    }
}

