package com.teame1i4.newsfeed.domain.comment.dto.response

import com.teame1i4.newsfeed.domain.member.dto.response.MemberResponse
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val member: MemberResponse,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)