package com.teame1i4.newsfeed.domain.comment.controller

import com.teame1i4.newsfeed.domain.comment.dto.request.CreateCommentRequest
import com.teame1i4.newsfeed.domain.comment.dto.request.UpdateCommentRequest
import com.teame1i4.newsfeed.domain.comment.dto.response.CommentResponse
import com.teame1i4.newsfeed.domain.comment.service.CommentService
import com.teame1i4.newsfeed.domain.exception.UnauthorizedAccessException
import com.teame1i4.newsfeed.domain.member.adapter.MemberDetails
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/posts/{postId}/comments")
@RestController
class CommentController(
    private val commentService: CommentService
) {
    @GetMapping
    fun getCommentList(
        @PathVariable postId: Long
    ): ResponseEntity<List<CommentResponse>> = ResponseEntity
        .status(HttpStatus.OK)
        .body(commentService.getCommentList(postId))

    @PostMapping
    fun createComment(
        @AuthenticationPrincipal member: MemberDetails?,
        @PathVariable postId: Long,
        @RequestBody request: CreateCommentRequest
    ): ResponseEntity<CommentResponse> = ResponseEntity
        .status(HttpStatus.CREATED)
        .body(commentService.createComment(postId, request, member ?: throw UnauthorizedAccessException()))

    @PutMapping("/{commentId}")
    fun updateComment(
        @AuthenticationPrincipal member: MemberDetails?,
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> = ResponseEntity
        .status(HttpStatus.OK)
        .body(commentService.updateComment(postId, commentId, request, member ?: throw UnauthorizedAccessException()))

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @AuthenticationPrincipal member: MemberDetails?,
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
    ): ResponseEntity<Unit> = ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(commentService.deleteComment(postId, commentId, member ?: throw UnauthorizedAccessException()))

}