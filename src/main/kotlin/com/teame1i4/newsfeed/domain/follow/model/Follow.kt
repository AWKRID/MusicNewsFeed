package com.teame1i4.newsfeed.domain.follow.model

import com.teame1i4.newsfeed.domain.follow.dto.response.FollowResponse
import jakarta.persistence.*

@Entity
@Table(name = "follow")
class Follow(

    @Column(name = "member_id", nullable = false)
    val memberId: Long,

    @Column(name = "follower_member_id", nullable = false)
    val followerMemberId: Long
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

fun Follow.toResponse(): FollowResponse = FollowResponse(id = id!!, memberId, followerMemberId)