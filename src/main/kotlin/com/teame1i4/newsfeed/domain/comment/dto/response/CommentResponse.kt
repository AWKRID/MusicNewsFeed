package com.teame1i4.newsfeed.domain.comment.dto.response

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val username: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
    )
