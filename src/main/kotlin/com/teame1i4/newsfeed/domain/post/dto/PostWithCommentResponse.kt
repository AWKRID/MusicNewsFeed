package com.teame1i4.newsfeed.domain.post.dto

import com.teame1i4.newsfeed.domain.comment.dto.response.CommentResponse
import com.teame1i4.newsfeed.domain.member.dto.response.MemberResponse
import java.time.LocalDateTime

data class PostWithCommentResponse(
    val id: Long,
    val title: String,
    val member: MemberResponse,
    val musicUrl: String,
    val content: String,
    val tags: List<String>,
    val musicType: String,
    val viewCount: Long,
    val upvoteCount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    var comments: List<CommentResponse>
)