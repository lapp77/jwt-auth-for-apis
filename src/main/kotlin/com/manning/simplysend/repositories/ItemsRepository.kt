package com.manning.simplysend.repositories

import com.manning.simplysend.entities.Item
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface ItemsRepository : PagingAndSortingRepository<Item, Long> {

    fun findAllByType(type: String, pageable: Pageable): Page<Item>
}