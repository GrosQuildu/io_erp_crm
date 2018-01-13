package io.swagger.model.crm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.BaseModel;
import io.swagger.model.common.Employee;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TaskComment
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
@Entity
public class TaskComment extends BaseModel {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("task")

  @ManyToOne
  @JoinColumn(name = "task_id")
  private Task task;

  @JsonProperty("content")

  @Column(nullable = false, unique = false)
  private String content;

  @JsonProperty("employee")

  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @JsonProperty("time")

  @Column(nullable = false, unique = false)
  private LocalDate time;

  protected TaskComment() {}

  public TaskComment(Integer id, Task task, String content, Employee employee, LocalDate time) {
    this.id = id;
    this.task = task;
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

  public TaskComment task(Task task) {
    this.task = task;
    return this;
  }

   /**
   * Get task
   * @return task
  **/
  @ApiModelProperty(required = true, value = "")



  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
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
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskComment taskComment = (TaskComment) o;
    return Objects.equals(this.id, taskComment.id) &&
        Objects.equals(this.task, taskComment.task) &&
        Objects.equals(this.content, taskComment.content) &&
        Objects.equals(this.employee, taskComment.employee) &&
        Objects.equals(this.time, taskComment.time);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, task, content, employee, time);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskComment {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    task: ").append(toIndentedString(task)).append("\n");
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
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

