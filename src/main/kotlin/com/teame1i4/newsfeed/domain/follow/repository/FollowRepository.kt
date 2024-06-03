package com.teame1i4.newsfeed.domain.follow.repository

import com.teame1i4.newsfeed.domain.follow.model.Follow
import org.springframework.data.jpa.repository.JpaRepository

interface FollowRepository : JpaRepository<Follow, Long> {
    fun existsByMemberIdAndFollowerMemberId(memberId: Long,followerMemberId : Long): Boolean
    fun findByMemberIdAndFollowerMemberId(memberId: Long, followerId: Long): Follow?
    fun findAllByMemberId(memberId: Long): List<Follow>
}