package com.manning.simplysend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.manning.simplysend.entities.Item;

public class ItemDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("type")
    private Item.TypeEnum type;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item.TypeEnum getType() {
        return type;
    }

    public void setType(Item.TypeEnum type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
