package com.teame1i4.newsfeed.domain.member.dto.parameter

data class UserDetailsParameter(
    val memberId: Long,
    val nickname: String,
    val password: String,
    val role: String
)