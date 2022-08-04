package com.manning.simplysend.api

import com.manning.simplysend.dto.ItemDTO
import com.manning.simplysend.services.ItemsService
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1/items"])
class ItemsController(private val itemsService: ItemsService) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun listItems(
        @RequestParam(value = "type", required = false) type: String?,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int
    ): Page<ItemDTO> {
        return itemsService.listItems(type, page, limit)
    }
}