package com.manning.simplysend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.manning.simplysend.entities.Order;

import java.util.List;

public class OrderDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("status")
    private Order.StatusEnum status;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("items")
    private List<ItemDTO> items;

    public OrderDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order.StatusEnum getStatus() {
        return status;
    }

    public void setStatus(Order.StatusEnum status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
