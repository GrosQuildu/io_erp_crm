package io.swagger.model.crm;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TaskRepository  extends CrudRepository<Task, Integer> {
    public Task findById(Integer id);
    public List<Task> findAllByTaskStatusId(Integer id);
    public List<Task> findAllByContactsId(Integer id);
}