package io.swagger.model.crm;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by gros on 01.12.17.
 */
public interface ContactGroupRepository extends CrudRepository<ContactGroup, Integer> {
    public ContactGroup findById(Integer id);
}