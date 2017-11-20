package io.swagger.api.erp;

import java.math.BigDecimal;

import io.swagger.ModelHelper;
import io.swagger.model.common.Client;
import io.swagger.model.common.ClientRepository;
import io.swagger.model.common.Employee;
import io.swagger.model.common.EmployeeRepository;
import io.swagger.model.erp.OrderRepository;
import io.swagger.model.erp.Order_;
import io.swagger.model.erp.OrderedArticle;

import io.swagger.annotations.*;

import io.swagger.model.erp.OrderedArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    /** Dependent:
        * orderedArticles
        * proforma
     * Depends on:
        * employee
        * client
     */
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderedArticleRepository orderedArticleRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    private Order_ getOrderHelper(Integer id) {
        Order_ order = orderRepository.findById(id);
        if(order == null)
            throw new Error("Order not found");
        return order;
    }

    public ResponseEntity<Integer> createOrder(@ApiParam(value = "Order_ to create"  )  @Valid @RequestBody Order_ order) {
        Employee employee = employeeRepository.findById(order.getEmployee().getId());
        if(employee == null)
            throw new Error("Employee not found");
        order.setEmployee(employee);

        Client client = clientRepository.findById(order.getClient().getId());
        if(client == null)
            throw new Error("Client not found");
        order.setClient(client);

        order = orderRepository.save(order);
        return new ResponseEntity<Integer>(order.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteOrder(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId) {
        getOrderHelper(orderId);

        for (OrderedArticle orderedArticle :
                orderedArticleRepository.findAllByOrderId(orderId)) {
            orderedArticleRepository.delete(orderedArticle);
        }
        orderRepository.delete(orderId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Order_> getOrder(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId) {
        Order_ order = getOrderHelper(orderId);
        return new ResponseEntity<Order_>(order, HttpStatus.OK);
    }

    public ResponseEntity<BigDecimal> getOrderDeliveryCosts(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId) {
        getOrderHelper(orderId);

        BigDecimal weightSum = new BigDecimal(0);
        Float weight = null;
        for (OrderedArticle orderedArticle :
                orderedArticleRepository.findAllByOrderId(orderId)) {
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
        getOrderHelper(orderId);

        BigDecimal netPriceSum = new BigDecimal(0);
        BigDecimal netPrice = null;
        for (OrderedArticle orderedArticle :
                orderedArticleRepository.findAllByOrderId(orderId)) {
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

        if(orderId != order.getId())
            throw new Error("Wrong order id");

        Order_ orderOld = getOrderHelper(orderId);
        try {
            ModelHelper.combine(orderOld, order);
        } catch (Exception e) {
            throw new Error("Wrong article object");
        }

        Employee employee = employeeRepository.findById(order.getEmployee().getId());
        if(employee == null)
            throw new Error("Employee not found");
        order.setEmployee(employee);

        Client client = clientRepository.findById(order.getClient().getId());
        if(client == null)
            throw new Error("Client not found");
        order.setClient(client);

        orderRepository.save(order);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
