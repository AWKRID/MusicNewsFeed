package com.teame1i4.newsfeed.domain.upvote.repository

import com.teame1i4.newsfeed.domain.upvote.model.Upvote
import org.springframework.data.jpa.repository.JpaRepository

interface UpvoteRepository : JpaRepository<Upvote, Long> {
    fun existsByMemberIdAndPostId(memberId: Long, postId: Long): Boolean
    fun findByMemberIdAndPostId(memberId: Long, postId: Long): Upvote?
}