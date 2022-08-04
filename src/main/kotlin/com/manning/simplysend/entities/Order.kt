package com.manning.simplysend.entities

import com.manning.simplysend.exceptions.OrderAlreadyApprovedException
import com.manning.simplysend.exceptions.OrderAlreadyDeniedException
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: StatusEnum? = null

    @Column(name = "reason")
    var reason: String? = null

    @Column(name = "comment")
    var comment: String? = null

    @OneToOne
    @JoinColumn(name = "user_id", updatable = false)
    var user: User? = null

    @Column(name = "items")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "order_items",
        joinColumns = [JoinColumn(name = "order_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    var items: List<Item>? = null

    fun approve() {
        if (status == StatusEnum.APPROVED) {
            throw OrderAlreadyApprovedException()
        }
        status = StatusEnum.APPROVED
    }

    fun reject() {
        if (status == StatusEnum.DENIED) {
            throw OrderAlreadyDeniedException()
        }
        status = StatusEnum.DENIED
    }

    override fun toString(): String {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                ", type='" + reason + '\'' +
                ", comment='" + comment + '\'' +
                '}'
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Order) return false
        val order = o
        return id == order.id && status == order.status && reason == order.reason && comment == order.comment
    }

    override fun hashCode(): Int {
        return Objects.hash(id, status, reason, comment)
    }

    enum class StatusEnum(private val value: String) {
        REQUESTED("REQUESTED"), APPROVED("APPROVED"), DENIED("DENIED");

        override fun toString(): String {
            return value
        }

        companion object {
            fun fromValue(text: String): StatusEnum? {
                for (b in values()) {
                    if (b.value == text) {
                        return b
                    }
                }
                return null
            }
        }
    }
}