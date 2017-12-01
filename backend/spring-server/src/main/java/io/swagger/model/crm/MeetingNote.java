package io.swagger.model.crm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.BaseModel;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * MeetingNote
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
@Entity
public class MeetingNote extends BaseModel {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id ;

  @JsonProperty("meeting")
  @NotNull
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "meeting_id")
  private Meeting meeting;

  @JsonProperty("content")
  @NotNull
  private String content;

  @JsonProperty("backgroundColor")
  @NotNull
  private String backgroundColor;

  protected MeetingNote() {}

  public MeetingNote(Integer id, Meeting meeting, String content, String backgroundColor) {
    this.id = id;
    this.meeting = meeting;
    this.content = content;
    this.backgroundColor = backgroundColor;
  }

    public MeetingNote id(Integer id) {
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

  public MeetingNote meeting(Meeting meeting) {
    this.meeting = meeting;
    return this;
  }

   /**
   * Get meeting
   * @return meeting
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Meeting getMeetingId() {
    return meeting;
  }

  public void setMeetingId(Meeting meeting) {
    this.meeting = meeting;
  }

  public MeetingNote content(String content) {
    this.content = content;
    return this;
  }

   /**
   * Get content
   * @return content
  **/
  @ApiModelProperty(value = "")


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public MeetingNote backgroundColor(String backgroundColor) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MeetingNote meetingNote = (MeetingNote) o;
    return Objects.equals(this.id, meetingNote.id) &&
        Objects.equals(this.meeting, meetingNote.meeting) &&
        Objects.equals(this.content, meetingNote.content) &&
        Objects.equals(this.backgroundColor, meetingNote.backgroundColor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, meeting, content, backgroundColor);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MeetingNote {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    meeting: ").append(toIndentedString(meeting)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    backgroundColor: ").append(toIndentedString(backgroundColor)).append("\n");
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

