package com.teame1i4.newsfeed.domain.upvote.controller

import com.teame1i4.newsfeed.domain.member.adapter.MemberDetails
import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import com.teame1i4.newsfeed.domain.upvote.service.UpvoteService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/posts/{postId}/upvote")
@RestController
class UpvoteController(
    private val upvoteService: UpvoteService
) {
    @PostMapping()
    fun upvotePost(
        @AuthenticationPrincipal member: MemberDetails,
        @PathVariable postId: Long
    ): ResponseEntity<PostResponse> {
        return ResponseEntity.ok(upvoteService.upvotePost(postId, member))
    }

    @DeleteMapping()
    fun upvoteCancelPost(
        @AuthenticationPrincipal member: MemberDetails,
        @PathVariable postId: Long
    ): ResponseEntity<PostResponse> {
        return ResponseEntity.ok(upvoteService.cancelUpvote(postId, member))
    }
}