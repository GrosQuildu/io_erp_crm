package main.java.erp.backend.model.erp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by gros on 15.11.17.
 */
@RepositoryRestResource
public interface ArticleRepository extends CrudRepository<Article, Integer> {
    public Article findById(Integer id);
    public List<Article> findAllByUnitId(Integer id);
}