package com.manning.simplysend.services

import com.manning.simplysend.dto.OrderDTO
import org.springframework.data.domain.Page

interface OrdersService {

    fun listOrders(status: String?, page: Int, limit: Int): Page<OrderDTO>

    fun createOrder(orderDTO: OrderDTO, userEmailId: String)

    fun approveOrder(orderID: Long): Boolean

    fun rejectOrder(orderID: Long): Boolean
}