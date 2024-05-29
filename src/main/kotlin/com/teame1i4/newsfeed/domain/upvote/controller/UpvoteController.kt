package com.teame1i4.newsfeed.domain.upvote.controller

import com.teame1i4.newsfeed.domain.upvote.dto.UpvoteToggleRequest
import com.teame1i4.newsfeed.domain.upvote.dto.UpvoteToggleResponse
import com.teame1i4.newsfeed.domain.upvote.service.UpvoteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/posts/{postId}")
@RestController
class UpvoteController(
    private val upvoteService: UpvoteService
) {
    @PostMapping()
    fun upvoteToggle(@PathVariable postId: Long,
               @RequestBody request: UpvoteToggleRequest
    ): ResponseEntity<UpvoteToggleResponse> {
        return ResponseEntity.ok(upvoteService.upvoteToggle(postId, request))
    }
}