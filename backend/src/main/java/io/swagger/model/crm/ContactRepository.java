package io.swagger.model.crm;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ContactRepository extends CrudRepository<Contact, Integer> {
    public Contact findById(Integer id);
    public List<Contact> findAllByContactGroupId(Integer id);
}