package com.teame1i4.newsfeed.domain.member.model

import com.teame1i4.newsfeed.auth.dto.request.SignUpRequest
import com.teame1i4.newsfeed.auth.dto.response.SignInResponse
import com.teame1i4.newsfeed.auth.dto.response.SignUpResponse
import com.teame1i4.newsfeed.domain.member.dto.parameter.UserDetailsParameter
import com.teame1i4.newsfeed.domain.member.dto.response.MemberResponse
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(name = "member")
class Member(
    @Column(name = "username", unique = true, nullable = false)
    var username: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role : MemberRole =MemberRole.USER
) {

    constructor(request: SignUpRequest, encoder: PasswordEncoder):
            this(request.username, encoder.encode(request.password))

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: MemberRole = MemberRole.USER

    fun toResponse(): MemberResponse = MemberResponse(id!!, username)
    fun toUserDetailsParameter(): UserDetailsParameter = UserDetailsParameter(id!!, username, password, role.toString())
    fun toSignUpResponse(): SignUpResponse = SignUpResponse(id!!, username, role.toString())
    fun toSignInResponse(accessToken: String): SignInResponse = SignInResponse(id!!, username, role.toString(), accessToken)
}