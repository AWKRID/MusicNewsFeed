package com.teame1i4.newsfeed.domain.comment.repository

import com.teame1i4.newsfeed.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findByPostIdAndId(postId: Long, id:Long): Comment?
    fun findAllByPostIdOrderByCreatedAtAsc(postId: Long): List<Comment>
}