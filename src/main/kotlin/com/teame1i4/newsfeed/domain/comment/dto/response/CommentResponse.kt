package com.teame1i4.newsfeed.domain.comment.dto.response

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val userName: String,
    val content: String,
    val timeCreated: LocalDateTime?,
    val timeUpdated: LocalDateTime?
    )
