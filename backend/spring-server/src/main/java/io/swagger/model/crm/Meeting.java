package io.swagger.model.crm;

import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.common.Employee;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Meeting
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
@Entity
public class Meeting   {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("meetingDate")
  @NotNull
  @Column(nullable = false, unique = false)
  private LocalDate meetingDate;

  @JsonProperty("employees")
  @NotNull
  @OneToMany
  private List<Employee> employees;

  @JsonProperty("contacts")
  @NotNull
  @OneToMany
  private List<Contact> contacts;

  @JsonProperty("notes")
  @NotNull
  @OneToMany(mappedBy = "meeting")
  private List<MeetingNote> notes;

  @JsonProperty("nextMeetingDate")
  private LocalDate nextMeetingDate = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("purpose")
  private String purpose = null;

  @JsonProperty("mainEmployee")
  @NotNull
  @OneToOne(cascade = CascadeType.PERSIST)
  private Employee mainEmployee;

  @JsonProperty("telephoneMeeting")
  private Boolean telephoneMeeting = false;

  protected Meeting() {}

  public Meeting(Integer id, LocalDate meetingDate, Employee mainEmployee) {
    this.id = id;
    this.meetingDate = meetingDate;
    this.mainEmployee = mainEmployee;
  }

  public Meeting id(Integer id) {
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

  public Meeting meetingDate(LocalDate meetingDate) {
    this.meetingDate = meetingDate;
    return this;
  }

   /**
   * Get meetingDate
   * @return meetingDate
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public LocalDate getMeetingDate() {
    return meetingDate;
  }

  public void setMeetingDate(LocalDate meetingDate) {
    this.meetingDate = meetingDate;
  }

  public Meeting nextMeetingDate(LocalDate nextMeetingDate) {
    this.nextMeetingDate = nextMeetingDate;
    return this;
  }

   /**
   * Get nextMeetingDate
   * @return nextMeetingDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public LocalDate getNextMeetingDate() {
    return nextMeetingDate;
  }

  public void setNextMeetingDate(LocalDate nextMeetingDate) {
    this.nextMeetingDate = nextMeetingDate;
  }

  public Meeting description(String description) {
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

  public Meeting purpose(String purpose) {
    this.purpose = purpose;
    return this;
  }

   /**
   * Get purpose
   * @return purpose
  **/
  @ApiModelProperty(value = "")


  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public Meeting mainEmployee(Employee mainEmployee) {
    this.mainEmployee = mainEmployee;
    return this;
  }

   /**
   * Get mainEmployee
   * @return mainEmployee
  **/
  @ApiModelProperty(value = "")


  public Employee getMainEmployee() {
    return mainEmployee;
  }

  public void setMainEmployee(Employee mainEmployee) {
    this.mainEmployee = mainEmployee;
  }

  public Meeting telephoneMeeting(Boolean telephoneMeeting) {
    this.telephoneMeeting = telephoneMeeting;
    return this;
  }

   /**
   * Get telephoneMeeting
   * @return telephoneMeeting
  **/
  @ApiModelProperty(value = "")


  public Boolean getTelephoneMeeting() {
    return telephoneMeeting;
  }

  public void setTelephoneMeeting(Boolean telephoneMeeting) {
    this.telephoneMeeting = telephoneMeeting;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Meeting meeting = (Meeting) o;
    return Objects.equals(this.id, meeting.id) &&
        Objects.equals(this.meetingDate, meeting.meetingDate) &&
        Objects.equals(this.nextMeetingDate, meeting.nextMeetingDate) &&
        Objects.equals(this.description, meeting.description) &&
        Objects.equals(this.purpose, meeting.purpose) &&
        Objects.equals(this.mainEmployee, meeting.mainEmployee) &&
        Objects.equals(this.telephoneMeeting, meeting.telephoneMeeting);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, meetingDate, nextMeetingDate, description, purpose, mainEmployee, telephoneMeeting);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Meeting {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    meetingDate: ").append(toIndentedString(meetingDate)).append("\n");
    sb.append("    nextMeetingDate: ").append(toIndentedString(nextMeetingDate)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    purpose: ").append(toIndentedString(purpose)).append("\n");
    sb.append("    mainEmployee: ").append(toIndentedString(mainEmployee)).append("\n");
    sb.append("    telephoneMeeting: ").append(toIndentedString(telephoneMeeting)).append("\n");
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

