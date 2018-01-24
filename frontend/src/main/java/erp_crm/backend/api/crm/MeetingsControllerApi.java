package main.java.erp_crm.backend.api.crm;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.erp_crm.backend.api.ConnectionApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.crm.Meeting;
import main.java.erp_crm.backend.model.crm.MeetingNote;
import main.java.erp_crm.frontend.login.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeetingsControllerApi {
    private ConnectionApi connection = Login.connection;
    private Gson gson = Converters.registerLocalDate(new GsonBuilder()).create();


    public void getMeetings(){
        List<Meeting> meetings = new ArrayList<>();
        meetings.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.MEETINGS), Meeting[].class) )
        );
        DBData.setMeetings(meetings);
    }

    public Integer createMeeting(Meeting meeting){
        return connection.createObject(meeting.serialize(), ConnectionApi.ObjectType.MEETINGS);
    }

    public void updateMeeting(Meeting item){
        connection.updateObject(item.getId(), item.serialize(), ConnectionApi.ObjectType.MEETINGS);
    }

    public void deleteMeeting(Meeting item) {
        connection.deleteObject(item.getId(), ConnectionApi.ObjectType.MEETINGS);
    }
    public void updateNote(Meeting meeting, MeetingNote note) {
        connection.updateObject(meeting.getId(), note.serialize(), ConnectionApi.ObjectType.MEETING_NOTES, note.getId(), ConnectionApi.ObjectType.MEETING_NOTES_SECOND);
    }

    public void deleteNote(Meeting meeting, MeetingNote note) {
        connection.deleteObject(meeting.getId(), ConnectionApi.ObjectType.MEETING_NOTES, note.getId(), ConnectionApi.ObjectType.MEETING_NOTES_SECOND);
        meeting.getNotes().remove(note);
        updateMeeting(meeting);
    }
    public void createNote(Meeting meeting, MeetingNote note){
        connection.createObject(note.serialize(), ConnectionApi.ObjectType.MEETING_NOTES, meeting.getId(), ConnectionApi.ObjectType.MEETING_NOTES_SECOND);
    }
}
