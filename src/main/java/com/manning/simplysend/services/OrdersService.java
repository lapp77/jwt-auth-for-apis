package com.manning.simplysend.services;

import org.springframework.data.domain.Page;

import com.manning.simplysend.dto.OrderDTO;

public interface OrdersService {

    Page<OrderDTO> listOrders(String status, Integer page, Integer limit);

    void createOrder(OrderDTO orderDTO, String userEmailId);

    boolean approveOrder(Long orderID);

    boolean rejectOrder(Long orderID);
}
