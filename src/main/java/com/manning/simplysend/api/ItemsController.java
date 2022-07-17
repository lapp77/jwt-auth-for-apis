package com.manning.simplysend.api;

import com.manning.simplysend.dto.ItemDTO;
import com.manning.simplysend.exceptions.ForbiddenException;
import com.manning.simplysend.exceptions.InvalidProfileException;
import com.manning.simplysend.services.ItemsService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/v1/items")
public class ItemsController {

    private ItemsService itemsService;

    public ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GetMapping(name = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Page<ItemDTO> listOrders(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        try {
            return itemsService.listItems(type, page, limit);
        } catch (InvalidProfileException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (ForbiddenException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "an internal server error occurred");
        }
    }
}
