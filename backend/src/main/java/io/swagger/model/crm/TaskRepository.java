package io.swagger.model.crm;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by gros on 01.12.17.
 */
public interface TaskRepository  extends CrudRepository<Task, Integer> {
    public Task findById(Integer id);
}