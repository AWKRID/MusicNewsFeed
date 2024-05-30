package com.teame1i4.newsfeed.domain.member.adapter

import com.teame1i4.newsfeed.domain.member.dto.parameter.UserDetailsParameter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

class MemberDetails (
    val memberId: Long,
    val nickname: String,
    private val pw: String,
    private val role: String
): User(nickname, pw, arrayListOf<GrantedAuthority>(SimpleGrantedAuthority("ROLE_$role"))) {
    constructor(param: UserDetailsParameter): this(param.memberId, param.nickname, param.password, param.role)
}