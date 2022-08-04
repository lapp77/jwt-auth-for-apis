package com.manning.simplysend.services.impl

import com.manning.simplysend.dto.OrderDTO
import com.manning.simplysend.entities.Item
import com.manning.simplysend.entities.Order
import com.manning.simplysend.entities.Order.StatusEnum
import com.manning.simplysend.exceptions.*
import com.manning.simplysend.mappers.OrderMapper
import com.manning.simplysend.repositories.ItemsRepository
import com.manning.simplysend.repositories.OrdersRepository
import com.manning.simplysend.repositories.UserRepository
import com.manning.simplysend.services.OrdersService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class OrdersServiceImpl(
    private val ordersRepository: OrdersRepository,
    private val itemsRepository: ItemsRepository,
    private val userRepository: UserRepository
) : OrdersService {

    override  fun listOrders(status: String?, page: Int, limit: Int): Page<OrderDTO> {
        val pageRequest = PageRequest.of(page, limit)

        val orders =
            if (status.isNullOrBlank())
                ordersRepository.findAll(pageRequest)
            else
                ordersRepository.findAllByStatus(StatusEnum.valueOf(status), pageRequest)

        return orders.map(OrderMapper::toDTO)
    }

    override fun createOrder(orderDTO: OrderDTO, userEmailId: String) {
        val order = OrderMapper.fromDTO(orderDTO)
        order.status = StatusEnum.REQUESTED

        // make sure items are valid
        order.items = order.items!!.stream()
            .map { item: Item -> itemsRepository.findById(item.id!!).orElseThrow { SimplySendException() } }
            .collect(Collectors.toList())

        order.user = userRepository.findByEmail(userEmailId).orElseThrow { InvalidProfileException() }

        ordersRepository.save(order)
    }

    override fun approveOrder(orderID: Long): Boolean {
        return try {
            val order = ordersRepository.findById(orderID).orElseThrow { OrderNotFoundException() }
            order.approve()
            ordersRepository.save(order)
            true
        } catch (e: OrderAlreadyApprovedException) {
            true
        } catch (e: Exception) {
            throw e
        }
    }

    override fun rejectOrder(orderID: Long): Boolean {
        return try {
            val order = ordersRepository.findById(orderID).orElseThrow { OrderNotFoundException() }
            order.reject()
            ordersRepository.save(order)
            true
        } catch (e: OrderAlreadyDeniedException) {
            true
        } catch (e: Exception) {
            throw e
        }
    }
}