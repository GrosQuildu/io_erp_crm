package io.swagger.api.erp;

import java.math.BigDecimal;

import io.swagger.ModelHelper;
import io.swagger.model.BaseModel;
import io.swagger.model.erp.*;

import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import javax.persistence.criteria.Order;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Controller
public class OrderedArticlesApiController implements OrderedArticlesApi {

    /** Dependent:
        * none
     * Depends on:
        * order (not null)
        * article (not null)
     */
    @Autowired
    OrderedArticleRepository orderedArticleRepository ;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    OrderRepository orderRepository;

    private void checkOrder(Integer orderId, OrderedArticle orderedArticle) {
        Order_ order = orderRepository.findById(orderId);
        if(order == null)
            throw new Error("Order not found");
        if(orderId != orderedArticle.getOrder().getId())
            throw new Error("Wrong order id");
    }

    public ResponseEntity<Integer> createOrderedArticle(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId,
                                                        @ApiParam(value = "OrderedArticle to create"  )  @Valid @RequestBody OrderedArticle orderedArticle) {
        checkOrder(orderId, orderedArticle);
        orderedArticle = BaseModel.dependsOn(Order.class, orderRepository, orderedArticle);
        orderedArticle = BaseModel.dependsOn(Article.class, articleRepository, orderedArticle);
        orderedArticle = orderedArticleRepository.save(orderedArticle);
        return new ResponseEntity<Integer>(orderedArticle.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteOrderedArticle(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId,
                                                     @ApiParam(value = "",required=true ) @PathVariable("OrderedArticleId") Integer orderedArticleId) {
        OrderedArticle orderedArticle = BaseModel.getModelHelper(orderedArticleRepository, orderedArticleId);
        checkOrder(orderId, orderedArticle);
        orderedArticleRepository.delete(orderedArticle);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    public ResponseEntity<OrderedArticle> getOrderedArticle(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId,
                                                            @ApiParam(value = "",required=true ) @PathVariable("OrderedArticleId") Integer orderedArticleId) {
        OrderedArticle orderedArticle = BaseModel.getModelHelper(orderedArticleRepository, orderedArticleId);
        checkOrder(orderId, orderedArticle);
        return new ResponseEntity<OrderedArticle>(orderedArticle, HttpStatus.OK);
    }

    public ResponseEntity<BigDecimal> getOrderedArticleNetPrice(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId,
                                                                @ApiParam(value = "",required=true ) @PathVariable("OrderedArticleId") Integer orderedArticleId) {
        OrderedArticle orderedArticle = BaseModel.getModelHelper(orderedArticleRepository, orderedArticleId);
        checkOrder(orderId, orderedArticle);

        BigDecimal netPrice = orderedArticle.getNetPrice();
        if(netPrice == null) {
            netPrice = orderedArticle.getArticle().getUnitPrice().multiply(new BigDecimal(orderedArticle.getAmount()));
        }
        return new ResponseEntity<BigDecimal>(netPrice, HttpStatus.OK);
    }

    public ResponseEntity<Float> getOrderedArticleWeight(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId,
                                                                @ApiParam(value = "",required=true ) @PathVariable("OrderedArticleId") Integer orderedArticleId) {
        OrderedArticle orderedArticle = BaseModel.getModelHelper(orderedArticleRepository, orderedArticleId);
        checkOrder(orderId, orderedArticle);

        Float weight = orderedArticle.getWeight();
        if(weight == null) {
            weight = orderedArticle.getArticle().getWeight() * orderedArticle.getAmount();
        }
        return new ResponseEntity<Float>(weight, HttpStatus.OK);
    }

    public ResponseEntity<List<OrderedArticle>> getOrderedArticles(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId) {
        Order_ order = BaseModel.getModelHelper(orderRepository, orderId);
        List<OrderedArticle> orderedArticles = (List<OrderedArticle>) orderedArticleRepository.findAllByOrderId(order.getId());
        return new ResponseEntity<List<OrderedArticle>>(orderedArticles, HttpStatus.OK);
    }


    public ResponseEntity<Void> updateOrderedArticle(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId,
                                                     @ApiParam(value = "",required=true ) @PathVariable("OrderedArticleId") Integer orderedArticleId,
                                                     @ApiParam(value = "OrderedArticle to create"  )  @Valid @RequestBody OrderedArticle orderedArticle) {
        if(orderedArticle.getId() != null && orderedArticleId != orderedArticle.getId())
            throw new Error("Wrong id");

        OrderedArticle orderedArticleOld = BaseModel.getModelHelper(orderedArticleRepository, orderedArticleId);
        checkOrder(orderId, orderedArticle);

        orderedArticle = BaseModel.combineWithOld(orderedArticleRepository, orderedArticle);
        orderedArticle = BaseModel.dependsOn(Order_.class, orderRepository, orderedArticle);
        orderedArticle = BaseModel.dependsOn(Article.class, articleRepository, orderedArticle);

        orderedArticleRepository.save(orderedArticle);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
