package io.swagger.model.crm;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by gros on 01.12.17.
 */
public interface ContactRepository extends CrudRepository<Contact, Integer> {
    public Contact findById(Integer id);
}