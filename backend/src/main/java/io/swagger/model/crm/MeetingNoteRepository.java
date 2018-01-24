package io.swagger.model.crm;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MeetingNoteRepository extends CrudRepository<MeetingNote, Integer> {
    public MeetingNote findById(Integer id);
}