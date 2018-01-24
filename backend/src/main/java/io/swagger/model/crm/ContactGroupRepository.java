package io.swagger.model.crm;

import org.springframework.data.repository.CrudRepository;


public interface ContactGroupRepository extends CrudRepository<ContactGroup, Integer> {
    public ContactGroup findById(Integer id);
}