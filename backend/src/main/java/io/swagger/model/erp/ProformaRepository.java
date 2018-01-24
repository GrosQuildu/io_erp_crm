package io.swagger.model.erp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ProformaRepository extends CrudRepository<Proforma, Integer> {
    public Proforma findById(Integer id);
    public List<Proforma> findAllByOrder_Id(Integer id);
}