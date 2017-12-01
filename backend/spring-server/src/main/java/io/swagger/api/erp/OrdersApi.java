/**
 * NOTE: This class is auto generated by the swagger code generator program (2.2.3).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api.erp;

import java.math.BigDecimal;
import io.swagger.model.Error;
import io.swagger.model.erp.Order_;
import io.swagger.model.erp.OrderedArticle;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Api(value = "orders", description = "the orders API")
public interface OrdersApi {

    @ApiOperation(value = "Create new order", notes = "", response = Integer.class, authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "ERP - orders", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = Integer.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/erp/orders",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Integer> createOrder(@ApiParam(value = "Order_ to create"  )  @Valid @RequestBody Order_ order);


    @ApiOperation(value = "Delete order", notes = "", response = Void.class, authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "ERP - orders", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Deleted", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/erp/orders/{orderId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteOrder(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId);


    @ApiOperation(value = "Returns Order_", notes = "", response = Order_.class, authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "ERP - orders", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = Order_.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/erp/orders/{orderId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<Order_> getOrder(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId);


    @ApiOperation(value = "Returns order's delivery costs", notes = "", response = BigDecimal.class, authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "ERP - orders", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Get or compute (if null) delivery cost of order", response = BigDecimal.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/erp/orders/{orderId}/deliveryCosts",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<BigDecimal> getOrderDeliveryCosts(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId);


    @ApiOperation(value = "Returns order's net price", notes = "", response = BigDecimal.class, authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "ERP - orders", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Compute net price of order", response = BigDecimal.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/erp/orders/{orderId}/netPrice",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<BigDecimal> getOrderNetPrice(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId);



    @ApiOperation(value = "Returns list of Orders", notes = "", response = Order_.class, responseContainer = "List", authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "ERP - orders", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = Order_.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/erp/orders",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<Order_>> getOrders();


    @ApiOperation(value = "Update existing order", notes = "", response = Void.class, authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "ERP - orders", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Updated", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/erp/orders/{orderId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateOrder(@ApiParam(value = "",required=true ) @PathVariable("orderId") Integer orderId,@ApiParam(value = "Order_ to create"  )  @Valid @RequestBody Order_ order);


}
