package com.manning.simplysend.api;

import com.manning.simplysend.dto.ItemDTO;
import com.manning.simplysend.services.ItemsService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/items")
public class ItemsController {

    private ItemsService itemsService;

    public ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GetMapping(name = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Page<ItemDTO> listItems(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return itemsService.listItems(type, page, limit);
    }
}
