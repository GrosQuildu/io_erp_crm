package io.swagger.model.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface UnitRepository extends CrudRepository<Unit, Integer> {
    public Unit findById(Integer id);
}