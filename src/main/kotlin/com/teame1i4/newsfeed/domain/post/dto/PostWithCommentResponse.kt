package com.teame1i4.newsfeed.domain.post.dto

import com.teame1i4.newsfeed.domain.comment.dto.response.CommentResponse
import java.time.LocalDateTime

data class PostWithCommentResponse(
    val id: Long,
    val title: String,
    val username: String,
    val musicUrl: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    var comments: List<CommentResponse>
)