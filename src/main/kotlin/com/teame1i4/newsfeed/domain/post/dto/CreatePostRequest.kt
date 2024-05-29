package com.teame1i4.newsfeed.domain.post.dto

data class CreatePostRequest(
    val title: String,
    val musicUrl: String,
    val content: String,
    val userId: Long,
    val musicType: String,
    val tags: List<String>
)