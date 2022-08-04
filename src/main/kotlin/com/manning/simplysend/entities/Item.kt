package com.manning.simplysend.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "items")
class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    var type: TypeEnum? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "price")
    var price: Int? = null

    @Column(name = "description")
    var description: String? = null

    override fun toString(): String {
        return "Item{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}'
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Item) return false
        val item = o
        return id == item.id && type == item.type && name == item.name && price == item.price && description == item.description
    }

    override fun hashCode(): Int {
        return Objects.hash(id, type, name, price, description)
    }

    enum class TypeEnum(private val value: String) {
        HARDWARE("HARDWARE"), SOFTWARE("SOFTWARE"), STATIONARY("STATIONARY"), TRAINING("TRAINING"), MSIC("MSIC");

        override fun toString(): String {
            return value
        }

        companion object {
            fun fromValue(text: String): TypeEnum? {
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