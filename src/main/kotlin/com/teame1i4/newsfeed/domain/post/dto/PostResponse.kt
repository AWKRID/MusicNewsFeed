package com.teame1i4.newsfeed.domain.post.dto

import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val writer: String,
    val musicUrl: String,
    val content: String,
    val timeCreated: LocalDateTime,
    val timeUpdated: LocalDateTime,
)