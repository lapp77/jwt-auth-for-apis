package com.manning.simplysend.services.impl;

import com.manning.simplysend.dto.ItemDTO;
import com.manning.simplysend.entities.Item;
import com.manning.simplysend.mappers.ItemMapper;
import com.manning.simplysend.repositories.ItemsRepository;
import com.manning.simplysend.services.ItemsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ItemsServiceImpl implements ItemsService {

    ItemsRepository itemsRepository;

    public ItemsServiceImpl(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Override
    public Page<ItemDTO> listItems(String type, Integer page, Integer limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);

        Page<Item> items = (type == null || type.isBlank()) ? itemsRepository.findAll(pageRequest)
                : itemsRepository.findAllByType(type, pageRequest);

        return items.map(ItemMapper::toDTO);
    }
}
