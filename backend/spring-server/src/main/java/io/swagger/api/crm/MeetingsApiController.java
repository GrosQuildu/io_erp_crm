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
public class MeetingsApiController implements MeetingsApi {

    public ResponseEntity<Integer> createMeeting(@ApiParam(value = "Meeting to create"  )  @Valid @RequestBody Meeting meeting) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }


    public ResponseEntity<Void> deleteMeeting(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Meeting> getMeeting(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId) {
        // do some magic!
        return new ResponseEntity<Meeting>(HttpStatus.OK);
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

}
