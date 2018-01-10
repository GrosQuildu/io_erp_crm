package io.swagger.model.erp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by gros on 15.11.17.
 */
@RepositoryRestResource
public interface ProformaRepository extends CrudRepository<Proforma, Integer> {
    public Proforma findById(Integer id);
}