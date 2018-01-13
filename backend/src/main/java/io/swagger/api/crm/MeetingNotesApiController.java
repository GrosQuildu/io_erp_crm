package io.swagger.api.crm;

import io.swagger.model.BaseModel;
import io.swagger.model.crm.Meeting;
import io.swagger.model.crm.MeetingNote;

import io.swagger.annotations.*;

import io.swagger.model.crm.MeetingNoteRepository;
import io.swagger.model.crm.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Controller
public class MeetingNotesApiController implements MeetingNotesApi {

    /** Dependent:
        * none
     * Depends on:
        * meeting (not null)
     */
    @Autowired
    MeetingNoteRepository meetingNoteRepository;
    @Autowired
    MeetingRepository meetingRepository;

    private void checkMeeting(Integer meetingId, MeetingNote meetingNote) {
        Meeting meeting = meetingRepository.findById(meetingId);
        if(meeting == null)
            throw new Error("Meeting not found");
        if(meetingId != meetingNote.getMeetingId().getId())
            throw new Error("Wrong meeting id");
    }

    public ResponseEntity<Integer> createMeetingNotes(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
                                                      @ApiParam(value = "MeetingNote to create"  )  @Valid @RequestBody MeetingNote meetingNote) {
        checkMeeting(meetingId, meetingNote);
        meetingNote = BaseModel.dependsOn(Meeting.class, meetingRepository, meetingNote);
        meetingNote = meetingNoteRepository.save(meetingNote);
        return new ResponseEntity<Integer>(meetingNote.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteMeetingNote(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
                                                  @ApiParam(value = "",required=true ) @PathVariable("MeetingNoteId") Integer meetingNoteId) {
        MeetingNote meetingNote = BaseModel.getModelHelper(meetingNoteRepository, meetingNoteId);
        checkMeeting(meetingId, meetingNote);
        meetingNoteRepository.delete(meetingNote);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<MeetingNote> getMeetingNote(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
                                                      @ApiParam(value = "",required=true ) @PathVariable("MeetingNoteId") Integer meetingNoteId) {
        MeetingNote meetingNote = BaseModel.getModelHelper(meetingNoteRepository, meetingNoteId);
        checkMeeting(meetingId, meetingNote);
        return new ResponseEntity<MeetingNote>(meetingNote, HttpStatus.OK);
    }

    public ResponseEntity<List<MeetingNote>> getMeetingNotes(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId) {
        Meeting meeting = BaseModel.getModelHelper(meetingRepository, meetingId);
        List<MeetingNote> meetingNotes = meeting.getNotes();
        return new ResponseEntity<List<MeetingNote>>(meetingNotes, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateMeetingNote(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
                                                  @ApiParam(value = "",required=true ) @PathVariable("MeetingNoteId") Integer meetingNoteId,
                                                  @ApiParam(value = "MeetingNote to update"  )  @Valid @RequestBody MeetingNote meetingNote) {
        if(meetingNote.getId() != null && meetingNoteId != meetingNote.getId())
            throw new Error("Wrong id");

        MeetingNote meetingNoteOld = BaseModel.getModelHelper(meetingNoteRepository, meetingNoteId);
        checkMeeting(meetingId, meetingNote);

        meetingNote = BaseModel.combineWithOld(meetingNoteRepository, meetingNote);
        meetingNote = BaseModel.dependsOn(Meeting.class, meetingRepository, meetingNote);

        meetingNoteRepository.save(meetingNote);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
