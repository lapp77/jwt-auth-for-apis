package com.manning.simplysend.services.impl

import com.manning.simplysend.dto.ItemDTO
import com.manning.simplysend.mappers.ItemMapper
import com.manning.simplysend.repositories.ItemsRepository
import com.manning.simplysend.services.ItemsService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ItemsServiceImpl(private val itemsRepository: ItemsRepository) : ItemsService {

    override fun listItems(type: String?, page: Int, limit: Int): Page<ItemDTO> {
        val pageRequest = PageRequest.of(page, limit)
        
        val items =
            if (type.isNullOrBlank())
                itemsRepository.findAll(pageRequest)
            else
                itemsRepository.findAllByType(type, pageRequest)

        return items.map(ItemMapper::toDTO)
    }
}