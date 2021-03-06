package io.swagger.model.erp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.criteria.Order;
import java.util.List;


@RepositoryRestResource
public interface OrderedArticleRepository extends CrudRepository<OrderedArticle, Integer> {
    public OrderedArticle findById(Integer id);
    public List<OrderedArticle> findAllByArticleId(Integer id);
}