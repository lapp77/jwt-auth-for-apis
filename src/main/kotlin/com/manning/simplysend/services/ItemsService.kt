package com.manning.simplysend.services

import com.manning.simplysend.dto.ItemDTO
import org.springframework.data.domain.Page

interface ItemsService {

    fun listItems(type: String?, page: Int, limit: Int): Page<ItemDTO>
}