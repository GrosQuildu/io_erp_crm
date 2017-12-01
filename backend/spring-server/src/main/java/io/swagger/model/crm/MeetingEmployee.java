//package io.swagger.model.crm;
//
//import java.util.Objects;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import io.swagger.annotations.ApiModelProperty;
//
//import javax.validation.constraints.*;
//
///**
// * MeetingEmployee
// */
//@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
//
//public class MeetingEmployee   {
//  @JsonProperty("id")
//  private Integer id = null;
//
//  @JsonProperty("meetingId")
//  private Integer meetingId = null;
//
//  @JsonProperty("employeeId")
//  private Integer employeeId = null;
//
//  public MeetingEmployee id(Integer id) {
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
//  public MeetingEmployee meetingId(Integer meetingId) {
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
//  public MeetingEmployee employeeId(Integer employeeId) {
//    this.employeeId = employeeId;
//    return this;
//  }
//
//   /**
//   * Get employeeId
//   * @return employeeId
//  **/
//  @ApiModelProperty(required = true, value = "")
//  @NotNull
//
//
//  public Integer getEmployee() {
//    return employeeId;
//  }
//
//  public void setEmployee(Integer employeeId) {
//    this.employeeId = employeeId;
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
//    MeetingEmployee meetingEmployee = (MeetingEmployee) o;
//    return Objects.equals(this.id, meetingEmployee.id) &&
//        Objects.equals(this.meetingId, meetingEmployee.meetingId) &&
//        Objects.equals(this.employeeId, meetingEmployee.employeeId);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(id, meetingId, employeeId);
//  }
//
//  @Override
//  public String toString() {
//    StringBuilder sb = new StringBuilder();
//    sb.append("class MeetingEmployee {\n");
//
//    sb.append("    id: ").append(toIndentedString(id)).append("\n");
//    sb.append("    meetingId: ").append(toIndentedString(meetingId)).append("\n");
//    sb.append("    employeeId: ").append(toIndentedString(employeeId)).append("\n");
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
