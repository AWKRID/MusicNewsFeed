package com.teame1i4.newsfeed.domain.post.controller


import com.teame1i4.newsfeed.domain.exception.UnauthorizedAccessException
import com.teame1i4.newsfeed.domain.member.adapter.MemberDetails
import com.teame1i4.newsfeed.domain.post.dto.CreatePostRequest
import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import com.teame1i4.newsfeed.domain.post.dto.PostWithCommentResponse
import com.teame1i4.newsfeed.domain.post.dto.UpdatePostRequest
import com.teame1i4.newsfeed.domain.post.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/posts")
@RestController
class PostController(
    private val postService: PostService
) {
    @GetMapping("/feeds")
    fun getFeeds(
        @AuthenticationPrincipal member : MemberDetails?
    ) : ResponseEntity<List<PostResponse>>{

        if (member == null) throw UnauthorizedAccessException()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getFeeds(member))
    }

    @GetMapping
    fun getPosts(
        @AuthenticationPrincipal member: MemberDetails?,
        @RequestParam(required = false, value = "tag") tag: String?,
        @RequestParam(required = false, name = "title") title: String?,
        @RequestParam(required = false, name = "music_type") musicType: String?,
        @RequestParam(required = false, name = "memberId") memberId: Long?
    ): ResponseEntity<List<PostResponse>> = ResponseEntity
        .status(HttpStatus.OK)
        .body(postService.getPosts(member, tag, title, musicType, memberId))

    @GetMapping("/{postId}")
    fun getPost(
        @AuthenticationPrincipal member: MemberDetails?,
        @PathVariable postId: Long
    ): ResponseEntity<PostWithCommentResponse> = ResponseEntity
        .status(HttpStatus.OK)
        .body(postService.getPostById(member, postId))


    @PostMapping
    fun createPost(
        @AuthenticationPrincipal member: MemberDetails?,
        @RequestBody createPostRequest: CreatePostRequest
    ): ResponseEntity<PostResponse> = ResponseEntity
        .status(HttpStatus.CREATED)
        .body(postService.createPost(member ?: throw UnauthorizedAccessException(), createPostRequest))


    @DeleteMapping("/{postId}")
    fun deletePost(
        @AuthenticationPrincipal member: MemberDetails?,
        @PathVariable postId: Long
    ): ResponseEntity<Unit> = ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(postService.deletePost(member ?: throw UnauthorizedAccessException(), postId))


    @PutMapping("/{postId}")
    fun updatePost(
        @AuthenticationPrincipal member: MemberDetails?,
        @PathVariable postId: Long,
        @RequestBody updatePostRequest: UpdatePostRequest
    ): ResponseEntity<PostResponse> = ResponseEntity
        .status(HttpStatus.OK)
        .body(postService.updatePost(member ?: throw UnauthorizedAccessException(), postId, updatePostRequest))

}