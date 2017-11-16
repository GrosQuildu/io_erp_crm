package io.swagger.model.crm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

/**
 * Task
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

public class Task   {
  @JsonProperty("task")
  private Task task = null;

  @JsonProperty("comments")
  private List<TaskComment> comments = null;

  @JsonProperty("notes")
  private List<TaskNote> notes = null;

  @JsonProperty("contacts")
  private List<TaskContact> contacts = null;

  public Task task(Task task) {
    this.task = task;
    return this;
  }

   /**
   * Get task
   * @return task
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
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

  public Task contacts(List<TaskContact> contacts) {
    this.contacts = contacts;
    return this;
  }

  public Task addContactsItem(TaskContact contactsItem) {
    if (this.contacts == null) {
      this.contacts = new ArrayList<TaskContact>();
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

  public List<TaskContact> getContacts() {
    return contacts;
  }

  public void setContacts(List<TaskContact> contacts) {
    this.contacts = contacts;
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
    return Objects.equals(this.task, task.task) &&
        Objects.equals(this.comments, task.comments) &&
        Objects.equals(this.notes, task.notes) &&
        Objects.equals(this.contacts, task.contacts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(task, comments, notes, contacts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Task {\n");
    
    sb.append("    task: ").append(toIndentedString(task)).append("\n");
    sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
    sb.append("    notes: ").append(toIndentedString(notes)).append("\n");
    sb.append("    contacts: ").append(toIndentedString(contacts)).append("\n");
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

