package io.swagger.model.crm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.BaseModel;
import io.swagger.model.common.Employee;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Task
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-30T21:06:24.731Z")
@Entity
public class Task extends BaseModel {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("title")
  @Column(nullable = false, unique = false)
  private String title;

  @JsonProperty("taskStatus")
  @ManyToOne
  @JoinColumn(name = "task_status_id")
  private TaskStatus taskStatus;

  @JsonProperty("comments")
  @OneToMany(orphanRemoval=true)
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  private List<TaskComment> comments = null;

  @JsonProperty("notes")
  @OneToMany(orphanRemoval=true)
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  private List<TaskNote> notes = null;

  @JsonProperty("contacts")
  @ManyToMany
  private List<Contact> contacts = null;

  @JsonProperty("employee")
  @OneToOne
  private Employee employee;

  @JsonProperty("employeeCommissioned")
  @OneToOne
  private Employee employeeCommissioned = null;

  @JsonProperty("backgroundColor")
  @Column(nullable = false, unique = false)
  private String backgroundColor;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("isArchived")
  private Boolean isArchived = false;

  @JsonProperty("endDate")
  @Column(nullable = false, unique = false)
  @JsonDeserialize(using=LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate endDate;

  @JsonProperty("startDate")
  @Column(nullable = false, unique = false)
  @JsonDeserialize(using=LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate startDate;

  protected Task() {}

  public Task(Integer id, String title, TaskStatus taskStatus, Employee employee, String backgroundColor, LocalDate endDate, LocalDate startDate) {
    this.id = id;
    this.title = title;
    this.taskStatus = taskStatus;
    this.employee = employee;
    this.backgroundColor = backgroundColor;
    this.endDate = endDate;
    this.startDate = startDate;
  }

  public Task id(Integer id) {
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

  public Task title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
   **/
  @ApiModelProperty(required = true, value = "")


  @Size(min=5,max=100)
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Task comments(List<TaskComment> comments) {
    this.comments = comments;
    return this;
  }

  public Task addCommentsItem(TaskComment commentsItem) {
    if (this.comments == null) {
      this.comments = new ArrayList<TaskComment>();
    }
    this.comments.add(commentsItem);
    return this;
  }

  /**
   * Get comments
   * @return comments
   **/
  @ApiModelProperty(value = "")

  @Valid

  public List<TaskComment> getComments() {
    return comments;
  }

  public void setComments(List<TaskComment> comments) {
    if(this.comments == null)
      this.comments = comments;
    else {
      this.comments.clear();
      this.comments.addAll(comments);
    }
  }

  public Task notes(List<TaskNote> notes) {
    this.notes = notes;
    return this;
  }

  public Task addNotesItem(TaskNote notesItem) {
    if (this.notes == null) {
      this.notes = new ArrayList<TaskNote>();
    }
    this.notes.add(notesItem);
    return this;
  }

  /**
   * Get notes
   * @return notes
   **/
  @ApiModelProperty(value = "")

  @Valid

  public List<TaskNote> getNotes() {
    return notes;
  }

  public void setNotes(List<TaskNote> notes) {
    if(this.notes == null)
      this.notes = notes;
    else {
      this.notes.clear();
      this.notes.addAll(notes);
    }
  }

  public Task contacts(List<Contact> contacts) {
    this.contacts = contacts;
    return this;
  }

  public Task addContactsItem(Contact contactsItem) {
    if (this.contacts == null) {
      this.contacts = new ArrayList<Contact>();
    }
    this.contacts.add(contactsItem);
    return this;
  }

  /**
   * Get contacts
   * @return contacts
   **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
  }


  public Task taskStatus(TaskStatus taskStatus) {
    this.taskStatus = taskStatus;
    return this;
  }

  /**
   * Get taskStatus
   * @return taskStatus
   **/
  @ApiModelProperty(required = true, value = "")



  public TaskStatus getTaskStatus() {
    return taskStatus;
  }

  public void setTaskStatus(TaskStatus taskStatus) {
    this.taskStatus = taskStatus;
  }

  public Task employee(Employee employee) {
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

  public Task employeeCommissioned(Employee employeeCommissioned) {
    this.employeeCommissioned = employeeCommissioned;
    return this;
  }

  /**
   * Get employeeCommissioned
   * @return employeeCommissioned
   **/
  @ApiModelProperty(value = "")


  public Employee getEmployeeCommissioned() {
    return employeeCommissioned;
  }

  public void setEmployeeCommissioned(Employee employeeCommissioned) {
    this.employeeCommissioned = employeeCommissioned;
  }


  public Task backgroundColor(String backgroundColor) {
    this.backgroundColor = backgroundColor;
    return this;
  }

  /**
   * Get backgroundColor
   * @return backgroundColor
   **/
  @ApiModelProperty(value = "")

  @Size(min=4,max=10)
  public String getBackgroundColor() {
    return backgroundColor;
  }

  public void setBackgroundColor(String backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public Task description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   **/
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Task isArchived(Boolean isArchived) {
    this.isArchived = isArchived;
    return this;
  }

  /**
   * Get isArchived
   * @return isArchived
   **/
  @ApiModelProperty(value = "")


  public Boolean getIsArchived() {
    return isArchived;
  }

  public void setIsArchived(Boolean isArchived) {
    this.isArchived = isArchived;
  }

  public Task startDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Get startDate
   * @return startDate
   **/
  @ApiModelProperty(value = "")
  @Valid
  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }


  public Task endDate(LocalDate endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * Get endDate
   * @return endDate
   **/
  @ApiModelProperty(value = "")

  @Valid

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Task task = (Task) o;
    return Objects.equals(this.id, task.id) &&
            Objects.equals(this.title, task.title) &&
            Objects.equals(this.comments, task.comments) &&
            Objects.equals(this.notes, task.notes) &&
            Objects.equals(this.contacts, task.contacts) &&
            Objects.equals(this.taskStatus, task.taskStatus) &&
            Objects.equals(this.employee, task.employee) &&
            Objects.equals(this.employeeCommissioned, task.employeeCommissioned) &&
            Objects.equals(this.backgroundColor, task.backgroundColor) &&
            Objects.equals(this.description, task.description) &&
            Objects.equals(this.isArchived, task.isArchived) &&
            Objects.equals(this.endDate, task.endDate) &&
            Objects.equals(this.startDate, task.startDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, comments, notes, contacts, taskStatus, employee, employeeCommissioned, backgroundColor, description, isArchived, endDate, startDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Task {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
    sb.append("    notes: ").append(toIndentedString(notes)).append("\n");
    sb.append("    contacts: ").append(toIndentedString(contacts)).append("\n");
    sb.append("    taskStatus: ").append(toIndentedString(taskStatus)).append("\n");
    sb.append("    employee: ").append(toIndentedString(employee)).append("\n");
    sb.append("    employeeCommissioned: ").append(toIndentedString(employeeCommissioned)).append("\n");
    sb.append("    backgroundColor: ").append(toIndentedString(backgroundColor)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    isArchived: ").append(toIndentedString(isArchived)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
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
