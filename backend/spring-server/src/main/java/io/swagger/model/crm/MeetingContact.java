//package io.swagger.model.crm;
//
//import java.util.Objects;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import io.swagger.annotations.ApiModelProperty;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.validation.constraints.*;
//
///**
// * MeetingContact
// */
//@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
//@Entity
//public class MeetingContact   {
//  @JsonProperty("id")
//  @Id
//  @GeneratedValue
//  private Integer id;
//
//  @JsonProperty("meetingId")
//  @NotNull
//  @Column(nullable = false, unique = false)
//  private Integer meetingId;
//
//  @JsonProperty("contactId")
//  @NotNull
//  @Column(nullable = false, unique = false)
//  private Integer contactId;
//
//
//
//  public MeetingContact id(Integer id) {
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
//  public MeetingContact meetingId(Integer meetingId) {
//    this.meetingId = meetingId;
//    return this;
//  }
//
//   /**
//   * Get meetingId
//   * @return meetingId
//  **/
//  @ApiModelProperty(required = true, value = "")
//  @NotNull
//
//
//  public Integer getMeetingId() {
//    return meetingId;
//  }
//
//  public void setMeetingId(Integer meetingId) {
//    this.meetingId = meetingId;
//  }
//
//  public MeetingContact contactId(Integer contactId) {
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
//  public Integer getContactId() {
//    return contactId;
//  }
//
//  public void setContactId(Integer contactId) {
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
//    MeetingContact meetingContact = (MeetingContact) o;
//    return Objects.equals(this.id, meetingContact.id) &&
//        Objects.equals(this.meetingId, meetingContact.meetingId) &&
//        Objects.equals(this.contactId, meetingContact.contactId);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(id, meetingId, contactId);
//  }
//
//  @Override
//  public String toString() {
//    StringBuilder sb = new StringBuilder();
//    sb.append("class MeetingContact {\n");
//
//    sb.append("    id: ").append(toIndentedString(id)).append("\n");
//    sb.append("    meetingId: ").append(toIndentedString(meetingId)).append("\n");
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
