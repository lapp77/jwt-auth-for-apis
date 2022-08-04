package com.manning.simplysend.entities

import javax.persistence.*

@Entity
@Table(name = "user_credentials")
class UserCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "email", insertable = false, updatable = false)
    var user: User? = null

    @Column(name = "password")
    var password: String? = null

    @Column(name = "username")
    var username: String? = null

    @Column(name = "enabled")
    var enabled: Boolean? = null
}