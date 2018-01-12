package main.java.erp.backend.model.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by gros on 17.11.17.
 */
@RepositoryRestResource
public interface ClientTypeRepository extends CrudRepository<ClientType, Integer> {
    public ClientType findById(Integer id);
}