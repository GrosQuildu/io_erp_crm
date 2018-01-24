package main.java.erp_crm.backend.model.crm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateSerializer;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.*;
import io.swagger.annotations.ApiModelProperty;
import main.java.erp_crm.backend.model.common.Employee;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Task
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-30T21:06:24.731Z")
@Entity
public class Task {
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
  @Cascade(CascadeType.ALL)
  private List<TaskComment> comments = null;

  @JsonProperty("notes")
  @OneToMany(orphanRemoval=true)
  @Cascade(CascadeType.ALL)
  private List<TaskNote> notes = null;

  @JsonProperty("contacts")
  @OneToMany
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

  public Task() {}

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
    this.comments = comments;
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
    this.notes = notes;
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
  public boolean equals(Object o) {
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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  public String serialize(){
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Task.class, new TaskSerializer());
    gsonBuilder.setPrettyPrinting();
    final Gson gson = gsonBuilder.create();
    return gson.toJson(this);
  }

  class TaskSerializer implements JsonSerializer<Task> {

    @Override
    public JsonElement serialize(final Task task, final Type typeOfSrc, final JsonSerializationContext context) {
      JsonObject object = new JsonObject();
      Gson gson = Converters.registerLocalDate(new GsonBuilder()).create();
      JsonElement startDateElement = gson.toJsonTree(startDate);
      JsonElement endDateElement = gson.toJsonTree(endDate);

      object.add("startDate", startDateElement);
      object.add("endDate", endDateElement);

      object.addProperty("description", description);
      object.addProperty("isArchived", isArchived);
      object.addProperty("title", title);

      JsonElement taskStatusElement = gson.toJsonTree(taskStatus);

      object.add("taskStatus", taskStatusElement);


      object.addProperty("backgroundColor", backgroundColor);

      object.addProperty("id", id);

      JsonObject employeeObject = new JsonObject();
      employeeObject.addProperty("id", employee.getId());
      object.add("employee", employeeObject);
      
      if(employeeCommissioned!=null) {
        JsonObject employeeCommissionedObject = new JsonObject();
        employeeCommissionedObject.addProperty("id", employeeCommissioned.getId());
        object.add("employeeCommissioned", employeeCommissionedObject);
      }



      JsonArray taskCommentArray = new JsonArray();
      for(TaskComment i : comments){
        JsonObject taskCommentObj = new JsonObject();
        JsonObject commentObj = new JsonObject();
        commentObj.addProperty("id", i.getId());
        commentObj.addProperty("content", i.getContent());
        JsonElement timeElement = gson.toJsonTree(i.getTime());
        commentObj.add("time", timeElement);
        commentObj.addProperty("employee", i.getEmployee().getId());
      }
      object.add("comments", taskCommentArray);

      JsonArray notesArray = new JsonArray();
      for(TaskNote i : notes){
        JsonObject noteObj = new JsonObject();
        noteObj.addProperty("id", i.getId());
        noteObj.addProperty("content", i.getContent());
        noteObj.addProperty("backgroundColor", i.getBackgroundColor());
        notesArray.add(noteObj);
      }
      object.add("notes", notesArray);


      JsonArray contactsArray = new JsonArray();
      for(Contact i : contacts){
        JsonObject contactObj = new JsonObject();
        contactObj.addProperty("id", i.getId());
        contactsArray.add(contactObj);
      }
      object.add("contacts", contactsArray);


      return object;
    }
  }
}
