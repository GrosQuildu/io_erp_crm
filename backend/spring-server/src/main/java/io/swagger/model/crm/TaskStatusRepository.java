package io.swagger.model.crm;

import io.swagger.model.common.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by gros on 01.12.17.
 */
public interface TaskStatusRepository extends CrudRepository<TaskStatus, Integer> {
    public TaskStatus findById(Integer id);
}