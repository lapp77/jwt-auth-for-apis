package com.manning.simplysend.api;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.manning.simplysend.dto.OrderDTO;
import com.manning.simplysend.services.OrdersService;

@RestController
@RequestMapping(path = "/v1/orders")
public class OrdersController {

    private OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping(name = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Page<OrderDTO> listOrders(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return ordersService.listOrders(status, page, limit);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void createOrder(@RequestBody OrderDTO orderDTO, @AuthenticationPrincipal String userEmailId) {
        try {
            ordersService.createOrder(orderDTO, userEmailId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{orderId}/approve")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean approveOrder(@PathVariable("orderId") Long orderID) {
        try {
            return ordersService.approveOrder(orderID);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{orderId}/reject")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean rejectOrder(@PathVariable("orderId") Long orderID) {
        try {
            return ordersService.rejectOrder(orderID);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
