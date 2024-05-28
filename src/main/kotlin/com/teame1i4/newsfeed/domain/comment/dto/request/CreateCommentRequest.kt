package com.teame1i4.newsfeed.domain.comment.dto.request


data class CreateCommentRequest(
    val userId: Long,
    val content: String
)
