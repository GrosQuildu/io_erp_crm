package io.swagger.model.crm;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by gros on 01.12.17.
 */
public interface MeetingRepository extends CrudRepository<Meeting, Integer> {
    public Meeting findById(Integer id);
    public List<Meeting> findAllByContacts(Integer id);
}