package com.manning.simplysend.mappers

import com.manning.simplysend.dto.ItemDTO
import com.manning.simplysend.entities.Item

object ItemMapper : BaseMapper() {

    fun fromDTO(itemDTO: ItemDTO): Item {
        val item = Item()
        item.id = itemDTO.id
        item.name = itemDTO.name
        item.description = itemDTO.description
        item.price = itemDTO.price
        item.type = itemDTO.type
        return item
    }

    fun toDTO(item: Item): ItemDTO {
        val itemDTO = ItemDTO()
        itemDTO.id = item.id
        itemDTO.name = htmlEscape(item.name)
        itemDTO.description = htmlEscape(item.description)
        itemDTO.price = item.price
        itemDTO.type = item.type
        return itemDTO
    }
}