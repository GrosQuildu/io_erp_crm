package io.swagger.model.erp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by gros on 15.11.17.
 */
@RepositoryRestResource
public interface OrderRepository extends CrudRepository<Order_, Integer> {
    public Order_ findById(Integer id);
}