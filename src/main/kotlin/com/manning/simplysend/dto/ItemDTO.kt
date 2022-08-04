package com.manning.simplysend.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.manning.simplysend.entities.Item.TypeEnum

class ItemDTO {
    @JsonProperty("id")
    var id: Long? = null

    @JsonProperty("type")
    var type: TypeEnum? = null

    @JsonProperty("name")
    var name: String? = null

    @JsonProperty("price")
    var price: Int? = null

    @JsonProperty("description")
    var description: String? = null
}