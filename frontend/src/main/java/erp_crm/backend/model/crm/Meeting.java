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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Meeting
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
@Entity
public class Meeting {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("meetingDate")
  @Column(nullable = false, unique = false)
  @JsonDeserialize(using=LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate meetingDate;

  @JsonProperty("employees")
  @OneToMany
  private List<Employee> employees;

  @JsonProperty("contacts")
  @OneToMany
  private List<Contact> contacts;

  @JsonProperty("notes")
  @OneToMany(orphanRemoval=true)
  @Cascade(CascadeType.ALL)
  private List<MeetingNote> notes = null;

  @JsonProperty("nextMeetingDate")
  @JsonDeserialize(using=LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate nextMeetingDate = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("purpose")
  private String purpose = null;

  @JsonProperty("mainEmployee")
  @OneToOne
  private Employee mainEmployee;

  @JsonProperty("telephoneMeeting")
  private Boolean telephoneMeeting = false;

  public Meeting() {}

  public Meeting(Integer id, LocalDate meetingDate, Employee mainEmployee, List<Contact> contacts, List<Employee> employees) {
    this.id = id;
    this.meetingDate = meetingDate;
    this.mainEmployee = mainEmployee;
    this.employees = employees;
    this.contacts = contacts;
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



  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Meeting notes(List<MeetingNote> notes) {
    this.notes = notes;
    return this;
  }

  public Meeting addNotesItem(MeetingNote notesItem) {
    if (this.notes == null) {
      this.notes = new ArrayList<MeetingNote>();
    }
    this.notes.add(notesItem);
    return this;
  }

  /**
   * Get notes
   * @return notes
   **/
  @ApiModelProperty(value = "Notes can be created, updated and deleted with POST and PUT")
  @Valid

  public List<MeetingNote> getNotes() {
    return notes;
  }

  public void setNotes(List<MeetingNote> notes) {
    this.notes = notes;
  }


  public Meeting employees(List<Employee> employees) {
    this.employees = employees;
    return this;
  }

  public Meeting addEmployeeItem(Employee employeesItem) {
    if (this.employees == null) {
      this.employees = new ArrayList<Employee>();
    }
    this.employees.add(employeesItem);
    return this;
  }

  /**
   * Get employee
   * @return employee
   **/
  @ApiModelProperty(value = "Can use only existing employees")
  @Valid

  public List<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
  }


  public Meeting contacts(List<Contact> contacts) {
    this.contacts = contacts;
    return this;
  }

  public Meeting addContactItem(Contact contactsItem) {
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
  @ApiModelProperty(value = "Can use only existing contacts")
  @Valid

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
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
  @ApiModelProperty(value = "Can use only existing employee")


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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Meeting meeting = (Meeting) o;
    return Objects.equals(this.id, meeting.id) &&
        Objects.equals(this.employees, meeting.employees) &&
        Objects.equals(this.contacts, meeting.contacts) &&
        Objects.equals(this.notes, meeting.notes) &&
        Objects.equals(this.meetingDate, meeting.meetingDate) &&
        Objects.equals(this.nextMeetingDate, meeting.nextMeetingDate) &&
        Objects.equals(this.description, meeting.description) &&
        Objects.equals(this.purpose, meeting.purpose) &&
        Objects.equals(this.mainEmployee, meeting.mainEmployee) &&
        Objects.equals(this.telephoneMeeting, meeting.telephoneMeeting);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, employees, contacts, notes, meetingDate, nextMeetingDate, description, purpose, mainEmployee, telephoneMeeting);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Meeting {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    employees: ").append(toIndentedString(employees)).append("\n");
    sb.append("    contacts: ").append(toIndentedString(contacts)).append("\n");
    sb.append("    notes: ").append(toIndentedString(notes)).append("\n");
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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  public String serialize(){
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Meeting.class, new MeetingSerializer());
    gsonBuilder.setPrettyPrinting();
    final Gson gson = gsonBuilder.create();
    return gson.toJson(this);
  }

  class MeetingSerializer implements JsonSerializer<Meeting> {

    @Override
    public JsonElement serialize(final Meeting meeting, final Type typeOfSrc, final JsonSerializationContext context) {
      JsonObject object = new JsonObject();
      Gson gson = Converters.registerLocalDate(new GsonBuilder()).create();
      JsonElement meetingDateElement = gson.toJsonTree(meetingDate);
      JsonElement nextMeetingDateElement = gson.toJsonTree(nextMeetingDate);

      object.add("meetingDate", meetingDateElement);
      object.add("nextMeetingDate", nextMeetingDateElement);

      object.addProperty("description", description);
      object.addProperty("purpose", purpose);
      object.addProperty("telephoneMeeting", telephoneMeeting);

      object.addProperty("id", id);

      JsonObject mainEmployeeObject = new JsonObject();
      mainEmployeeObject.addProperty("id", mainEmployee.getId());
      object.add("mainEmployee", mainEmployeeObject);



      JsonArray employeeArray = new JsonArray();
      for(Employee i : employees){
        JsonObject employeeObj = new JsonObject();
        employeeObj.addProperty("id", i.getId());
        employeeArray.add(employeeObj);
      }
      object.add("employees", employeeArray);

      JsonArray notesArray = new JsonArray();
      for(MeetingNote i : notes){
        JsonObject noteObj = new JsonObject();
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

