package main.java.erp.backend.model.erp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by gros on 15.11.17.
 */
@RepositoryRestResource
public interface OrderedArticleRepository extends CrudRepository<OrderedArticle, Integer> {
    public OrderedArticle findById(Integer id);
    public List<Article> findAllByArticleId(Integer id);
    public List<OrderedArticle> findAllByOrderId(Integer id);
}