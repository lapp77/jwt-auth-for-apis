package com.manning.simplysend.api

import com.manning.simplysend.dto.OrderDTO
import com.manning.simplysend.services.OrdersService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping(path = ["/v1/orders"])
class OrdersController(private val ordersService: OrdersService) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun listOrders(
        @RequestParam(value = "status", required = false) status: String?,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int
    ): Page<OrderDTO> {
        return ordersService.listOrders(status, page, limit)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(value = HttpStatus.CREATED)
    fun createOrder(@RequestBody orderDTO: OrderDTO, @AuthenticationPrincipal userEmailId: String) {
        try {
            ordersService.createOrder(orderDTO, userEmailId)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping(value = ["/{orderId}/approve"])
    @ResponseStatus(value = HttpStatus.OK)
    fun approveOrder(@PathVariable("orderId") orderID: Long): Boolean {
        return try {
            ordersService.approveOrder(orderID)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping(value = ["/{orderId}/reject"])
    @ResponseStatus(value = HttpStatus.OK)
    fun rejectOrder(@PathVariable("orderId") orderID: Long): Boolean {
        return try {
            ordersService.rejectOrder(orderID)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}