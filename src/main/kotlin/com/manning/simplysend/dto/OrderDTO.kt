package com.manning.simplysend.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.manning.simplysend.entities.Order.StatusEnum

class OrderDTO {
    @JsonProperty("id")
    var id: Long? = null

    @JsonProperty("status")
    var status: StatusEnum? = null

    @JsonProperty("reason")
    var reason: String? = null

    @JsonProperty("comment")
    var comment: String? = null

    @JsonProperty("items")
    var items: List<ItemDTO>? = null
}