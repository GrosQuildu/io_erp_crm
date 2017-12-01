//package io.swagger.api.crm;
//
//import io.swagger.model.crm.Meeting;
//import io.swagger.model.crm.MeetingNote;
//
//import io.swagger.annotations.*;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.util.List;
//
//import javax.validation.Valid;
//@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
//
//@Controller
//public class MeetingContactsApiController implements  MeetingContactsApi {
//
//    public ResponseEntity<Integer> createMeetingContacts(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
//                                                         @ApiParam(value = "MeetingContact to create"  )  @Valid @RequestBody MeetingContact order) {
//        // do some magic!
//        return new ResponseEntity<Integer>(HttpStatus.OK);
//    }
//
//    public ResponseEntity<Void> deleteMeetingContact(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
//                                                     @ApiParam(value = "",required=true ) @PathVariable("MeetingContactId") Integer meetingContactId) {
//        // do some magic!
//        return new ResponseEntity<Void>(HttpStatus.OK);
//    }
//
//    public ResponseEntity<Contact> getMeetingContact(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
//                                                            @ApiParam(value = "",required=true ) @PathVariable("MeetingContactId") Integer meetingContactId) {
//        // do some magic!
//        return new ResponseEntity<Contact>(HttpStatus.OK);
//    }
//
//    public ResponseEntity<List<MeetingContact>> getMeetingContacts(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId) {
//        // do some magic!
//        return new ResponseEntity<List<MeetingContact>>(HttpStatus.OK);
//    }
//
//    public ResponseEntity<Void> updateMeetingContact(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
//                                                     @ApiParam(value = "",required=true ) @PathVariable("MeetingContactId") Integer meetingContactId,
//                                                     @ApiParam(value = "MeetingContact to update"  )  @Valid @RequestBody MeetingContact meetingContact) {
//        // do some magic!
//        return new ResponseEntity<Void>(HttpStatus.OK);
//    }
//}
