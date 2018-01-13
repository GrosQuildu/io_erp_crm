package io.swagger.api.crm;


import io.swagger.annotations.ApiParam;
import io.swagger.model.BaseModel;
import io.swagger.model.common.Employee;
import io.swagger.model.common.EmployeeRepository;
import io.swagger.model.crm.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Controller
public class MeetingsApiController implements MeetingsApi {

    /** Dependent:
        * meetingNotes (cascade, remove all on delete)
     * Depends on:
        * mainEmployee (not null)
        * employees (not null, list)
        * contacts (not null, list)
        * notes (may be null, list)
     */
    @Autowired
    MeetingRepository meetingRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    MeetingNoteRepository meetingNoteRepository;
    @Autowired
    ContactRepository contactRepository;

    public ResponseEntity<Integer> createMeeting(@ApiParam(value = "Meeting to create"  )  @RequestBody Meeting meeting) {
        meeting = BaseModel.dependsOn(Employee.class, employeeRepository, meeting, "MainEmployee");

        if(meeting.getEmployees() == null)
            throw new Error("Employees list can't be null");
        ArrayList<Employee> employees = new ArrayList<Employee>();
        for (Employee employee : meeting.getEmployees()) {
            Employee employeeTmp = employeeRepository.findById(employee.getId());
            if(employeeTmp != null) {
                employees.add(employeeTmp);
            } else {
                throw new Error("Employee not found");
            }
        }
        meeting.setEmployees(employees);

        if(meeting.getContacts() == null)
            throw new Error("Contacts list can't be null");
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        for (Contact contact : meeting.getContacts()) {
            Contact contactTmp = contactRepository.findById(contact.getId());
            if(contactTmp != null) {
                contacts.add(contactTmp);
            } else {
                throw new Error("Contact not found");
            }
        }
        meeting.setContacts(contacts);

        if(meeting.getNotes() != null) {
            ArrayList<MeetingNote> notes = new ArrayList<MeetingNote>();
            for (MeetingNote note : meeting.getNotes()) {
                MeetingNote noteTmp = meetingNoteRepository.findById(note.getId());
                if (noteTmp != null) {
                    notes.add(noteTmp);
                } else {
                    throw new Error("Meeting note not found");
                }
            }
            meeting.setNotes(notes);
        }

        meeting = meetingRepository.save(meeting);
        return new ResponseEntity<Integer>(meeting.getId(), HttpStatus.OK);
    }


    public ResponseEntity<Void> deleteMeeting(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId) {
        Meeting meeting = BaseModel.getModelHelper(meetingRepository, meetingId);
        meetingNoteRepository.delete(meeting.getNotes());
        meetingRepository.delete(meetingId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Meeting> getMeeting(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId) {
        Meeting meeting = BaseModel.getModelHelper(meetingRepository, meetingId);
        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

    public ResponseEntity<List<Meeting>> getMeetings() {
        List<Meeting> meetings = (List<Meeting>) meetingRepository.findAll();
        return new ResponseEntity<List<Meeting>>(meetings, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateMeeting(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
        @ApiParam(value = "Meeting to update"  )  @RequestBody Meeting meeting) {
        if(meeting.getId() != null && meetingId != meeting.getId())
            throw new Error("Wrong id");

        meeting = BaseModel.combineWithOld(meetingRepository, meeting);
        meeting = BaseModel.dependsOn(Employee.class, employeeRepository, meeting, "MainEmployee");

        if(meeting.getEmployees() == null)
            throw new Error("Employees list can't be null");
        ArrayList<Employee> employees = new ArrayList<Employee>();
        for (Employee employee : meeting.getEmployees()) {
            Employee employeeTmp = employeeRepository.findById(employee.getId());
            if(employeeTmp != null) {
                employees.add(employeeTmp);
            } else {
                throw new Error("Employee not found");
            }
        }
        meeting.setEmployees(employees);

        if(meeting.getContacts() == null)
            throw new Error("Contacts list can't be null");
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        for (Contact contact : meeting.getContacts()) {
            Contact contactTmp = contactRepository.findById(contact.getId());
            if(contactTmp != null) {
                contacts.add(contactTmp);
            } else {
                throw new Error("Contact not found");
            }
        }
        meeting.setContacts(contacts);

        if(meeting.getNotes() != null) {
            ArrayList<MeetingNote> notes = new ArrayList<MeetingNote>();
            for (MeetingNote note : meeting.getNotes()) {
                MeetingNote noteTmp = meetingNoteRepository.findById(note.getId());
                if (noteTmp != null) {
                    notes.add(noteTmp);
                } else {
                    throw new Error("Meeting note not found");
                }
            }
            meeting.setNotes(notes);
        }

        meetingRepository.save(meeting);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
