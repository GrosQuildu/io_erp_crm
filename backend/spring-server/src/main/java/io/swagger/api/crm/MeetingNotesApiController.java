package io.swagger.api.crm;

import io.swagger.model.crm.Meeting;
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
public class MeetingNotesApiController implements MeetingNotesApi {
    public ResponseEntity<Integer> createMeetingNotes(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
                                                      @ApiParam(value = "MeetingNote to create"  )  @Valid @RequestBody MeetingNote order) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteMeetingNote(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
                                                  @ApiParam(value = "",required=true ) @PathVariable("MeetingNoteId") Integer meetingNoteId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
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

    public ResponseEntity<Void> updateMeetingNote(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
                                                  @ApiParam(value = "",required=true ) @PathVariable("MeetingNoteId") Integer meetingNoteId,
                                                  @ApiParam(value = "MeetingNote to update"  )  @Valid @RequestBody MeetingNote meetingNote) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
