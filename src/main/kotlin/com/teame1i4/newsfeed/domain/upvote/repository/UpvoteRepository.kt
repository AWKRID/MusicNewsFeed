package com.teame1i4.newsfeed.domain.upvote.repository

import com.teame1i4.newsfeed.domain.upvote.model.Upvote
import org.springframework.data.jpa.repository.JpaRepository

interface UpvoteRepository: JpaRepository<Upvote,Long> {
    fun existsByUserIdAndPostId(userId: Long, postId: Long): Boolean

    fun findByUserIdAndPostId(userId: Long, postId: Long): Upvote?
}