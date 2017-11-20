package io.swagger.model.common;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by gros on 20.11.17.
 */
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    public Employee findById(Integer id);
}
