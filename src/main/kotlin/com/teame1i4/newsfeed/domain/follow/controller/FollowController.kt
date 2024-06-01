package com.teame1i4.newsfeed.domain.follow.controller

import com.teame1i4.newsfeed.domain.exception.UnauthorizedAccessException
import com.teame1i4.newsfeed.domain.follow.dto.response.FollowResponse
import com.teame1i4.newsfeed.domain.follow.service.FollowService
import com.teame1i4.newsfeed.domain.member.adapter.MemberDetails
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/follows")
@RestController
class FollowController(
    private val followService: FollowService
) {

    @PostMapping
    fun createFollow(
        @AuthenticationPrincipal member: MemberDetails?,
        @RequestParam(value = "follower_member_id") followerMemberId: Long
    ): ResponseEntity<FollowResponse> {
        if (member == null) throw UnauthorizedAccessException()
        return ResponseEntity.status(HttpStatus.CREATED).body(followService.createFollow(member, followerMemberId))
    }

    @DeleteMapping("/{memberId}")
    fun deleteFollow(
        @AuthenticationPrincipal member: MemberDetails?,
        @PathVariable(value = "memberId") id: Long
    ): ResponseEntity<Unit> {
        if (member == null) throw UnauthorizedAccessException()
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(followService.deleteFollow(member, id))
    }
}