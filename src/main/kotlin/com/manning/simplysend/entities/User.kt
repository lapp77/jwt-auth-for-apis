package com.manning.simplysend.entities

import java.io.Serializable
import java.util.*
import javax.persistence.*
import kotlin.collections.LinkedHashSet

@Entity
@Table(name = "user_profile")
class User : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "email")
    var email: String? = null

    @Column(name = "first_name")
    var firstName: String? = null

    @Column(name = "last_name")
    var lastName: String? = null

    @Column(name = "age")
    var age: Long? = null

    @Column(name = "phone")
    var phone: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: RoleEnum? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_manager",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "manager_id")]
    )
    var manager: User? = null
        private set

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "manager")
    private var managees: MutableSet<User>? = null

    @Column(name = "address")
    var address: String? = null

    @Column(name = "tag")
    var tag: String? = null

    fun setManager(manager: User?) {
        if (this.manager != null && !this.manager!!.getManagees().isNullOrEmpty()) {
            this.manager!!.setManagees(this.manager!!.getManagees()?.minus(this) as MutableSet<User>?)
        }

        this.manager = manager
        this.manager?.addManagee(this)
    }

    fun getManagees(): Set<User>? {
        return managees
    }

    fun setManagees(managees: MutableSet<User>?) {
        this.managees = managees
    }

    fun addManagee(managee: User) {
        if (managees == null) {
            managees = LinkedHashSet()
        }

        managees!!.add(managee)
    }

    fun id(id: Long?): User {
        this.id = id
        return this
    }

    fun manages(userId: Long): Boolean {
        return managees!!.stream().anyMatch { m: User -> m.id == userId }
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is User) return false
        val user = o
        return id == user.id && firstName == user.firstName && lastName == user.lastName && email == user.email
                && age == user.age && phone == user.phone && role == user.role && address == user.address && tag == user.tag
    }

    override fun hashCode(): Int {
        return Objects.hash(
            id,
            firstName,
            lastName,
            email,
            age,
            phone,
            role,
            address,
            tag
        )
    }

    enum class RoleEnum(private val value: String) {
        MGR("MGR"), REPORTEE("REPORTEE"), ADMIN("ADMIN");

        override fun toString(): String {
            return value
        }

        companion object {
            fun fromValue(text: String): RoleEnum? {
                for (b in values()) {
                    if (b.value == text) {
                        return b
                    }
                }
                return null
            }
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}