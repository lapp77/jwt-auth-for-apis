package com.manning.simplysend.mappers;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.manning.simplysend.dto.ItemDTO;
import com.manning.simplysend.dto.OrderDTO;
import com.manning.simplysend.entities.Item;
import com.manning.simplysend.entities.Order;

public class OrderMapper extends BaseMapper {

    public static Order fromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setReason(orderDTO.getReason());
        order.setComment(orderDTO.getComment());
        order.setStatus(orderDTO.getStatus());

        Stream<ItemDTO> items = orderDTO.getItems().stream();
        order.setItems(items.map(ItemMapper::fromDTO).collect(Collectors.toList()));
        return order;
    }

    public static OrderDTO toDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setReason(htmlEscape(order.getReason()));
        orderDTO.setComment(htmlEscape(order.getComment()));
        orderDTO.setStatus(order.getStatus());

        Stream<Item> items = order.getItems().stream();
        orderDTO.setItems(items.map(ItemMapper::toDTO).collect(Collectors.toList()));
        return orderDTO;
    }
}
