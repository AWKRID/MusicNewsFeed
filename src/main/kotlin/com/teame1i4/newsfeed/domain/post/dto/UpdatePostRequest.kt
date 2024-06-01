package com.teame1i4.newsfeed.domain.post.dto

data class UpdatePostRequest(
    val title: String,
    val musicUrl: String,
    val content: String,
    val musicType: String,
    val tags: List<String>
)