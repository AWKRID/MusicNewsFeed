package com.teame1i4.newsfeed.domain.post.dto

import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val username: String,
    val musicUrl: String,
    val content: String,
    val tags: List<String>,
    val musicType: String,
    val viewCount: Long,
    val upvoteCount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val commentCount: Long,
)