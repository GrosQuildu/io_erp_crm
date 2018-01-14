package io.swagger.model.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    public Employee findById(Integer id);
    public Employee findByMail(String mail);
}
