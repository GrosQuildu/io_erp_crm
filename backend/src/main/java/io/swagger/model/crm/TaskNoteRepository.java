package io.swagger.model.crm;

import org.springframework.data.repository.CrudRepository;


public interface TaskNoteRepository extends CrudRepository<TaskNote, Integer> {
    public TaskNote findById(Integer id);
}