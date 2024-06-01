package com.teame1i4.newsfeed.domain.follow.service

import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.exception.UnauthorizedAccessException
import com.teame1i4.newsfeed.domain.follow.dto.response.FollowResponse
import com.teame1i4.newsfeed.domain.follow.model.Follow
import com.teame1i4.newsfeed.domain.follow.model.toResponse
import com.teame1i4.newsfeed.domain.follow.repository.FollowRepository
import com.teame1i4.newsfeed.domain.member.adapter.MemberDetails
import com.teame1i4.newsfeed.domain.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class FollowService(
    private val followRepository: FollowRepository,
    private val memberRepository: MemberRepository
) {

    @PreAuthorize("hasRole('USER')")
    fun createFollow(member: MemberDetails, followerMemberId: Long): FollowResponse {

        if (member.id == followerMemberId) throw IllegalArgumentException("팔로우 신청 member와 당하는 member가 같음") // 예외처리 수정

        if (!memberRepository.existsById(followerMemberId)) throw ModelNotFoundException(
            "member",
            followerMemberId
        ) // 요청 당하는 member가 있나 확인

        if (followRepository.existsByMemberId(member.id)) throw IllegalArgumentException("팔로우 신청을 이미 함") // 예외처리 수정

        val follow: Follow = Follow(member.id, followerMemberId)

        followRepository.save(follow)

        return follow.toResponse()
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    fun deleteFollow(member: MemberDetails, id: Long): Unit {

        val follow: Follow = followRepository.findByMemberIdAndFollowerMemberId(member.id, id)
            ?: throw ModelNotFoundException("follow", id)

        if (member.id != follow.memberId) throw UnauthorizedAccessException()

        followRepository.delete(follow)
    }

}