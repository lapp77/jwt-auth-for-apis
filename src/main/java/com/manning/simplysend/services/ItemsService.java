package com.manning.simplysend.services;

import com.manning.simplysend.dto.ItemDTO;
import org.springframework.data.domain.Page;

public interface ItemsService {
    Page<ItemDTO> listItems(String type, Integer page, Integer limit);

}
