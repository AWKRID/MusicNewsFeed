package com.teame1i4.newsfeed.domain.upvote.controller

import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import com.teame1i4.newsfeed.domain.upvote.dto.UpvoteRequest
import com.teame1i4.newsfeed.domain.upvote.service.UpvoteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/posts/{postId}/upvote")
@RestController
class UpvoteController(
    private val upvoteService: UpvoteService
) {
    @PostMapping()
    fun upvotePost(@PathVariable postId: Long,
               @RequestBody request: UpvoteRequest
    ): ResponseEntity<PostResponse> {
        return ResponseEntity.ok(upvoteService.upvotePost(postId, request))
    }

    @DeleteMapping()
    fun upvoteCancelPost(@PathVariable postId: Long,
                   @RequestBody request: UpvoteRequest
    ): ResponseEntity<PostResponse> {
        return ResponseEntity.ok(upvoteService.cancelUpvote(postId, request))
    }
}