package com.manning.simplysend.entities;

import com.manning.simplysend.exceptions.OrderAlreadyApprovedException;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "reason")
    private String reason;

    @Column(name = "comment")
    private String comment;

    @OneToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @Column(name = "items")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_items", joinColumns = { @JoinColumn(name = "order_id") }, inverseJoinColumns = {
            @JoinColumn(name = "item_id") })
    private List<Item> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String type) {
        this.reason = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void approve() {
        if (getStatus() == Order.StatusEnum.APPROVED) {
            throw new OrderAlreadyApprovedException();
        }
        setStatus(Order.StatusEnum.APPROVED);
    }

    public void reject() {
        if (getStatus() == Order.StatusEnum.DENIED) {
            throw new OrderAlreadyApprovedException();
        }
        setStatus(Order.StatusEnum.DENIED);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                ", type='" + reason + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Order))
            return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId()) &&
                getStatus() == order.getStatus() &&
                Objects.equals(getReason(), order.getReason()) &&
                Objects.equals(getComment(), order.getComment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStatus(), getReason(), getComment());
    }

    public enum StatusEnum {
        REQUESTED("REQUESTED"),
        APPROVED("APPROVED"),
        DENIED("DENIED");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        public static StatusEnum fromValue(String text) {
            for (StatusEnum b : StatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override

        public String toString() {
            return String.valueOf(value);
        }
    }
}
