package io.swagger.model.crm;

import io.swagger.model.common.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TaskStatusRepository extends CrudRepository<TaskStatus, Integer> {
    public TaskStatus findById(Integer id);
}