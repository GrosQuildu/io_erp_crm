package io.swagger.model.crm;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by gros on 01.12.17.
 */
public interface TaskNoteRepository extends CrudRepository<TaskNote, Integer> {
    public TaskNote findById(Integer id);
}