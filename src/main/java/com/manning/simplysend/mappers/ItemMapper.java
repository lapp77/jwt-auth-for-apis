package com.manning.simplysend.mappers;

import com.manning.simplysend.dto.ItemDTO;
import com.manning.simplysend.entities.Item;

public class ItemMapper {

    public static Item fromDTO(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setPrice(itemDTO.getPrice());
        item.setType(itemDTO.getType());
        return item;
    }

    public static ItemDTO toDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setType(item.getType());
        return itemDTO;
    }
}
