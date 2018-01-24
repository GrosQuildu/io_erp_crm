package io.swagger.model.erp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface DeliveryCostRepository extends CrudRepository<DeliveryCost, Integer> {
    public DeliveryCost findById(Integer id);
}