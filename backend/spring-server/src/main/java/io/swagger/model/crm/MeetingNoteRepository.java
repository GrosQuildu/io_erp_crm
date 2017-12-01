package io.swagger.model.crm;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by gros on 01.12.17.
 */
public interface MeetingNoteRepository extends CrudRepository<MeetingNote, Integer> {
    public MeetingNote findById(Integer id);
}