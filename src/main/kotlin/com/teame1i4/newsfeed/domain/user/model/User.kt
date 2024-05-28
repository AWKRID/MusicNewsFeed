package com.teame1i4.newsfeed.domain.user.model

import com.teame1i4.newsfeed.domain.user.dto.response.UserResponse
import jakarta.persistence.*

@Entity
@Table(name = "member")
class User(
    @Column(name = "username", unique = true, nullable = false)
    var username: String,

    @Column(name = "password", nullable = false)
    var password: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null


    fun toResponse(): UserResponse = UserResponse(id!!, username)
}