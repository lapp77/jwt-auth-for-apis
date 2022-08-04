package com.manning.simplysend.mappers

import com.manning.simplysend.dto.OrderDTO
import com.manning.simplysend.entities.Order
import java.util.stream.Collectors

object OrderMapper : BaseMapper() {

    fun fromDTO(orderDTO: OrderDTO): Order {
        val order = Order()
        order.reason = orderDTO.reason
        order.comment = orderDTO.comment
        order.status = orderDTO.status

        val items = orderDTO.items!!.stream()
        order.items = items.map(ItemMapper::fromDTO).collect(Collectors.toList())

        return order
    }

    fun toDTO(order: Order): OrderDTO {
        val orderDTO = OrderDTO()
        orderDTO.id = order.id
        orderDTO.reason = htmlEscape(order.reason)
        orderDTO.comment = htmlEscape(order.comment)
        orderDTO.status = order.status

        val items = order.items!!.stream()
        orderDTO.items = items.map(ItemMapper::toDTO).collect(Collectors.toList())

        return orderDTO
    }
}