package io.swagger.api.erp;

import io.swagger.annotations.ApiParam;
import io.swagger.model.BaseModel;
import io.swagger.model.common.Client;
import io.swagger.model.common.ClientRepository;
import io.swagger.model.common.Employee;
import io.swagger.model.common.EmployeeRepository;
import io.swagger.model.erp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Controller
public class OrdersApiController implements OrdersApi {

    /** Dependent:
        * orderedArticles (cascade, remove all on delete)
        * proforma (hard, block on delete)
     * Depends on:
        * employee (may be null)
        * client (not null)
        * orderedArticles (may be null, list)
     */
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderedArticleRepository orderedArticleRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ProformaRepository proformaRepository;

    public ResponseEntity<Integer> createOrder(@ApiParam(value = "Order_ to create"  )  @Valid @RequestBody Order_ order) {
        if(order.getEmployee() != null)
            order = BaseModel.dependsOn(Employee.class, employeeRepository, order);
        order = BaseModel.dependsOn(Client.class, clientRepository, order);
        order = orderRepository.save(order);
        return new ResponseEntity<Integer>(order.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteOrder(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId) {
        Order_ order = BaseModel.getModelHelper(orderRepository, orderId);
        BaseModel.dependent(proformaRepository, order);
        orderedArticleRepository.delete(order.getorderedArticles());
        orderRepository.delete(orderId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Order_> getOrder(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId) {
        Order_ order = BaseModel.getModelHelper(orderRepository, orderId);
        return new ResponseEntity<Order_>(order, HttpStatus.OK);
    }

    public ResponseEntity<BigDecimal> getOrderDeliveryCosts(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId) {
        Order_ order = BaseModel.getModelHelper(orderRepository, orderId);

        BigDecimal weightSum = new BigDecimal(0);
        Float weight = null;
        for (OrderedArticle orderedArticle : order.getorderedArticles()) {
            weight = orderedArticle.getWeight();
            if(weight == null) {
                weight = orderedArticle.getArticle().getWeight() * orderedArticle.getAmount();
            }
            weightSum.add(new BigDecimal(weight));
        }
        //todo
        BigDecimal deliveryCost = weightSum;
        return new ResponseEntity<BigDecimal>(deliveryCost, HttpStatus.OK);
    }

    public ResponseEntity<BigDecimal> getOrderNetPrice(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId) {
        Order_ order = BaseModel.getModelHelper(orderRepository, orderId);

        BigDecimal netPriceSum = new BigDecimal(0);
        BigDecimal netPrice = null;
        for (OrderedArticle orderedArticle : order.getorderedArticles()) {
            netPrice = orderedArticle.getNetPrice();
            if(netPrice == null) {
                netPrice = orderedArticle.getArticle().getUnitPrice().multiply(new BigDecimal(orderedArticle.getAmount()));
            }
            netPriceSum.add(netPrice);
        }
        return new ResponseEntity<BigDecimal>(netPriceSum, HttpStatus.OK);
    }


    public ResponseEntity<List<Order_>> getOrders() {
        List<Order_> orders = (List<Order_>) orderRepository.findAll();
        return new ResponseEntity<List<Order_>>(orders, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateOrder(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId,
        @ApiParam(value = "Order_ to create"  )  @Valid @RequestBody Order_ order) {
        if(order.getId() != null && orderId != order.getId())
            throw new Error("Wrong id");

        order = BaseModel.combineWithOld(orderRepository, order);
        if(order.getEmployee() != null)
            order = BaseModel.dependsOn(Employee.class, employeeRepository, order);
        order = BaseModel.dependsOn(Client.class, clientRepository, order);
        orderRepository.save(order);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
