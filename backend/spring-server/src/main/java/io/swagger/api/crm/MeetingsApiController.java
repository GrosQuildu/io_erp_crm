package io.swagger.api.crm;

import io.swagger.model.crm.Meeting;
import io.swagger.model.crm.MeetingContact;
import io.swagger.model.crm.MeetingEmployee;
import io.swagger.model.crm.MeetingNote;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Controller
public class MeetingsApiController implements MeetingsApi {



    public ResponseEntity<Integer> createMeeting(@ApiParam(value = "Meeting to create"  )  @Valid @RequestBody Meeting meeting) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Integer> createMeetingContacts(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
        @ApiParam(value = "MeetingContact to create"  )  @Valid @RequestBody MeetingContact order) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Integer> createMeetingEmployees(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
        @ApiParam(value = "MeetingEmployee to create"  )  @Valid @RequestBody MeetingEmployee order) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Integer> createMeetingNotes(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
        @ApiParam(value = "MeetingNote to create"  )  @Valid @RequestBody MeetingNote order) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteMeeting(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteMeetingContact(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
        @ApiParam(value = "",required=true ) @PathVariable("MeetingContactId") Integer meetingContactId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteMeetingEmployee(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
        @ApiParam(value = "",required=true ) @PathVariable("MeetingEmployeeId") Integer meetingEmployeeId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteMeetingNote(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
        @ApiParam(value = "",required=true ) @PathVariable("MeetingNoteId") Integer meetingNoteId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Meeting> getMeeting(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId) {
        // do some magic!
        return new ResponseEntity<Meeting>(HttpStatus.OK);
    }

    public ResponseEntity<MeetingContact> getMeetingContact(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
        @ApiParam(value = "",required=true ) @PathVariable("MeetingContactId") Integer meetingContactId) {
        // do some magic!
        return new ResponseEntity<MeetingContact>(HttpStatus.OK);
    }

    public ResponseEntity<List<MeetingContact>> getMeetingContacts(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId) {
        // do some magic!
        return new ResponseEntity<List<MeetingContact>>(HttpStatus.OK);
    }

    public ResponseEntity<MeetingEmployee> getMeetingEmployee(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
        @ApiParam(value = "",required=true ) @PathVariable("MeetingEmployeeId") Integer meetingEmployeeId) {
        // do some magic!
        return new ResponseEntity<MeetingEmployee>(HttpStatus.OK);
    }

    public ResponseEntity<List<MeetingEmployee>> getMeetingEmployees(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId) {
        // do some magic!
        return new ResponseEntity<List<MeetingEmployee>>(HttpStatus.OK);
    }

    public ResponseEntity<MeetingNote> getMeetingNote(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
        @ApiParam(value = "",required=true ) @PathVariable("MeetingNoteId") Integer meetingNoteId) {
        // do some magic!
        return new ResponseEntity<MeetingNote>(HttpStatus.OK);
    }

    public ResponseEntity<List<MeetingNote>> getMeetingNotes(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId) {
        // do some magic!
        return new ResponseEntity<List<MeetingNote>>(HttpStatus.OK);
    }

    public ResponseEntity<List<Meeting>> getMeetings() {
        // do some magic!
        return new ResponseEntity<List<Meeting>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateMeeting(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
        @ApiParam(value = "Meeting to update"  )  @Valid @RequestBody Meeting meeting) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateMeetingContact(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
        @ApiParam(value = "",required=true ) @PathVariable("MeetingContactId") Integer meetingContactId,
        @ApiParam(value = "MeetingContact to update"  )  @Valid @RequestBody MeetingContact meetingContact) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateMeetingEmployee(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
        @ApiParam(value = "",required=true ) @PathVariable("MeetingEmployeeId") Integer meetingEmployeeId,
        @ApiParam(value = "MeetingEmployee to update"  )  @Valid @RequestBody MeetingEmployee meetingEmployee) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateMeetingNote(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
        @ApiParam(value = "",required=true ) @PathVariable("MeetingNoteId") Integer meetingNoteId,
        @ApiParam(value = "MeetingNote to update"  )  @Valid @RequestBody MeetingNote meetingNote) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
