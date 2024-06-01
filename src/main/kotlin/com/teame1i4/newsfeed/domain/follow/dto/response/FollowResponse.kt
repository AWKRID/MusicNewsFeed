package com.teame1i4.newsfeed.domain.follow.dto.response

data class FollowResponse(
    val id: Long,
    val memberId: Long,
    val followerMemberId: Long
)