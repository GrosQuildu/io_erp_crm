package io.swagger.model.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface ClientRepository extends CrudRepository<Client, Integer> {
    public Client findById(Integer id);
    public List<Client> findAllByClientTypeId(Integer id);
}