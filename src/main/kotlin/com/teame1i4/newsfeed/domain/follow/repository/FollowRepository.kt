package com.teame1i4.newsfeed.domain.follow.repository

import com.teame1i4.newsfeed.domain.follow.model.Follow
import org.springframework.data.jpa.repository.JpaRepository

interface FollowRepository : JpaRepository<Follow, Long> {
    fun existsByMemberId(memberId: Long): Boolean
    fun findByMemberIdAndFollowerMemberId(memberId: Long, followerId: Long): Follow?

}
