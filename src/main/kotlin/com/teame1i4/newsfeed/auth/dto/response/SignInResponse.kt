package com.teame1i4.newsfeed.auth.dto.response

data class SignInResponse(
    val id: Long,
    val username:  String,
    val role: String,
    val accessToken: String
)