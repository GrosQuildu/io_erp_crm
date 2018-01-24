package io.swagger.model.crm;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MeetingRepository extends CrudRepository<Meeting, Integer> {
    public Meeting findById(Integer id);
    public List<Meeting> findAllByContactsId(Integer id);
}