package io.swagger.model.crm;

import org.springframework.data.repository.CrudRepository;


public interface TaskCommentRepository  extends CrudRepository<TaskComment, Integer> {
    public TaskComment findById(Integer id);
}