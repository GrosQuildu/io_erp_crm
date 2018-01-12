package main.java.erp.backend.model.erp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by gros on 15.11.17.
 */
@RepositoryRestResource
public interface DeliveryCostRepository extends CrudRepository<DeliveryCost, Integer> {
    public DeliveryCost findById(Integer id);
}