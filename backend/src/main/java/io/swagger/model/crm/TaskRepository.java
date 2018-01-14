package io.swagger.model.crm;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by gros on 01.12.17.
 */
public interface TaskRepository  extends CrudRepository<Task, Integer> {
    public Task findById(Integer id);
    public List<Task> findAllByTaskStatusId(Integer id);
    public List<Task> findAllByContacts(Integer id);
}