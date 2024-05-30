package com.teame1i4.newsfeed.domain.member.model

import com.teame1i4.newsfeed.domain.member.dto.response.MemberResponse
import jakarta.persistence.*

@Entity
@Table(name = "member")
class Member(
    @Column(name = "username", unique = true, nullable = false)
    var username: String,

    @Column(name = "password", nullable = false)
    var password: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null


    fun toResponse(): MemberResponse = MemberResponse(id!!, username)
}