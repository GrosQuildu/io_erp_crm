//package io.swagger.model.crm;
//
//import java.util.Objects;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import io.swagger.annotations.ApiModelProperty;
//
//import javax.validation.constraints.*;
//
///**
// * TaskContact
// */
//@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
//
//public class TaskContact   {
//  @JsonProperty("id")
//  private Integer id = null;
//
//  @JsonProperty("taskId")
//  private Integer taskId = null;
//
//  @JsonProperty("contactId")
//  private Integer contactId = null;
//
//  public TaskContact id(Integer id) {
//    this.id = id;
//    return this;
//  }
//
//   /**
//   * Get id
//   * @return id
//  **/
//  @ApiModelProperty(required = true, value = "")
//  @NotNull
//
//
//  public Integer getId() {
//    return id;
//  }
//
//  public void setId(Integer id) {
//    this.id = id;
//  }
//
//  public TaskContact taskId(Integer taskId) {
//    this.taskId = taskId;
//    return this;
//  }
//
//   /**
//   * Get taskId
//   * @return taskId
//  **/
//  @ApiModelProperty(required = true, value = "")
//  @NotNull
//
//
//  public Integer getTaskId() {
//    return taskId;
//  }
//
//  public void setTaskId(Integer taskId) {
//    this.taskId = taskId;
//  }
//
//  public TaskContact contactId(Integer contactId) {
//    this.contactId = contactId;
//    return this;
//  }
//
//   /**
//   * Get contactId
//   * @return contactId
//  **/
//  @ApiModelProperty(required = true, value = "")
//  @NotNull
//
//
//  public Integer getContact() {
//    return contactId;
//  }
//
//  public void setContact(Integer contactId) {
//    this.contactId = contactId;
//  }
//
//
//  @Override
//  public boolean equals(java.lang.Object o) {
//    if (this == o) {
//      return true;
//    }
//    if (o == null || getClass() != o.getClass()) {
//      return false;
//    }
//    TaskContact taskContact = (TaskContact) o;
//    return Objects.equals(this.id, taskContact.id) &&
//        Objects.equals(this.taskId, taskContact.taskId) &&
//        Objects.equals(this.contactId, taskContact.contactId);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(id, taskId, contactId);
//  }
//
//  @Override
//  public String toString() {
//    StringBuilder sb = new StringBuilder();
//    sb.append("class TaskContact {\n");
//
//    sb.append("    id: ").append(toIndentedString(id)).append("\n");
//    sb.append("    taskId: ").append(toIndentedString(taskId)).append("\n");
//    sb.append("    contactId: ").append(toIndentedString(contactId)).append("\n");
//    sb.append("}");
//    return sb.toString();
//  }
//
//  /**
//   * Convert the given object to string with each line indented by 4 spaces
//   * (except the first line).
//   */
//  private String toIndentedString(java.lang.Object o) {
//    if (o == null) {
//      return "null";
//    }
//    return o.toString().replace("\n", "\n    ");
//  }
//}
//
