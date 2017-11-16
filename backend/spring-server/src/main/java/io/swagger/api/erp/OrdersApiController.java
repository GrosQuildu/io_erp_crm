package io.swagger.api.erp;

import java.math.BigDecimal;

import io.swagger.model.erp.Order;
import io.swagger.model.erp.OrderedArticle;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Controller
public class OrdersApiController implements OrdersApi {



    public ResponseEntity<Integer> createOrder(@ApiParam(value = "Order to create"  )  @Valid @RequestBody Order order) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Integer> createOrderedArticle(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId,
        @ApiParam(value = "OrderedArticle to create"  )  @Valid @RequestBody OrderedArticle order) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteOrder(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteOrderedArticle(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId,
        @ApiParam(value = "",required=true ) @PathVariable("OrderedArticleId") Integer orderedArticleId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Order> getOrder(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId) {
        // do some magic!
        return new ResponseEntity<Order>(HttpStatus.OK);
    }

    public ResponseEntity<BigDecimal> getOrderDeliveryCosts(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId) {
        // do some magic!
        return new ResponseEntity<BigDecimal>(HttpStatus.OK);
    }

    public ResponseEntity<BigDecimal> getOrderNetPrice(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId) {
        // do some magic!
        return new ResponseEntity<BigDecimal>(HttpStatus.OK);
    }

    public ResponseEntity<OrderedArticle> getOrderedArticle(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId,
        @ApiParam(value = "",required=true ) @PathVariable("OrderedArticleId") Integer orderedArticleId) {
        // do some magic!
        return new ResponseEntity<OrderedArticle>(HttpStatus.OK);
    }

    public ResponseEntity<BigDecimal> getOrderedArticleNetPrice(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId,
        @ApiParam(value = "",required=true ) @PathVariable("OrderedArticleId") Integer orderedArticleId) {
        // do some magic!
        return new ResponseEntity<BigDecimal>(HttpStatus.OK);
    }

    public ResponseEntity<List<OrderedArticle>> getOrderedArticles(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId) {
        // do some magic!
        return new ResponseEntity<List<OrderedArticle>>(HttpStatus.OK);
    }

    public ResponseEntity<List<Order>> getOrders() {
        // do some magic!
        return new ResponseEntity<List<Order>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateOrder(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId,
        @ApiParam(value = "Order to create"  )  @Valid @RequestBody Order order) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateOrderedArticle(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId,
        @ApiParam(value = "",required=true ) @PathVariable("OrderedArticleId") Integer orderedArticleId,
        @ApiParam(value = "OrderedArticle to create"  )  @Valid @RequestBody OrderedArticle orderedArticle) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
