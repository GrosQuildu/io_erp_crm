package io.swagger.model.crm;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by gros on 01.12.17.
 */
public interface TaskCommentRepository  extends CrudRepository<TaskComment, Integer> {
    public TaskStatus findById(Integer id);
}