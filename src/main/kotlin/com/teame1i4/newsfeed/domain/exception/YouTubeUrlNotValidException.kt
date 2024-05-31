package com.teame1i4.newsfeed.domain.exception

data class YouTubeUrlNotValidException(
    val musicUrl: String
) : RuntimeException("given '$musicUrl' is a invalid YouTube Link")
