package main.java.erp.backend.model.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by gros on 17.11.17.
 */
@RepositoryRestResource
public interface ClientRepository extends CrudRepository<Client, Integer> {
    public Client findById(Integer id);
    public List<Client> findAllByClientTypeId(Integer id);
}