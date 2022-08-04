package com.manning.simplysend.repositories

import com.manning.simplysend.entities.Order
import com.manning.simplysend.entities.Order.StatusEnum
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface OrdersRepository : PagingAndSortingRepository<Order, Long> {

    fun findAllByUser(user: String, pageable: Pageable): Page<Order>

    fun findAllByStatus(status: StatusEnum, pageable: Pageable): Page<Order>

    fun findAllByUserAndStatus(user: String, status: StatusEnum, pageable: Pageable): Page<Order>
}