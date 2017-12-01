//package io.swagger.api.crm;
//
//import io.swagger.model.common.Employee;
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
//public class MeetingEmployeesApiController implements MeetingEmployeesApi {
//    public ResponseEntity<Integer> createMeetingEmployees(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
//                                                          @ApiParam(value = "MeetingEmployee to create"  )  @Valid @RequestBody Employee order) {
//        // do some magic!
//        return new ResponseEntity<Integer>(HttpStatus.OK);
//    }
//
//    public ResponseEntity<Void> deleteMeetingEmployee(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
//                                                      @ApiParam(value = "",required=true ) @PathVariable("MeetingemployeeId") Integer employeeId) {
//        // do some magic!
//        return new ResponseEntity<Void>(HttpStatus.OK);
//    }
//
//    public ResponseEntity<Employee> getMeetingEmployee(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
//                                                       @ApiParam(value = "",required=true ) @PathVariable("MeetingemployeeId") Integer employeeId) {
//        // do some magic!
//        return new ResponseEntity<Employee>(HttpStatus.OK);
//    }
//
//    public ResponseEntity<List<Employee>> getMeetingEmployees(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId) {
//        // do some magic!
//        return new ResponseEntity<List<Employee>>(HttpStatus.OK);
//    }
//
//    public ResponseEntity<Void> updateMeetingEmployee(@ApiParam(value = "",required=true ) @PathVariable("meetingId") Integer meetingId,
//                                                      @ApiParam(value = "",required=true ) @PathVariable("MeetingemployeeId") Integer employeeId,
//                                                      @ApiParam(value = "MeetingEmployee to update"  )  @Valid @RequestBody Employee meetingEmployee) {
//        // do some magic!
//        return new ResponseEntity<Void>(HttpStatus.OK);
//    }
//}
