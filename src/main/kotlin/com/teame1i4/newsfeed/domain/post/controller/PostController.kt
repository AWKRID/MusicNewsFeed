package com.teame1i4.newsfeed.domain.post.controller


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

    @GetMapping("")
    fun getPosts(
        @AuthenticationPrincipal member: MemberDetails?,
        @RequestParam(required = false, value = "tag") tag: String?,
        @RequestParam(required = false, name = "title") title: String?,
        @RequestParam(required = false, name = "music_type") musicType: String?,
        @RequestParam(required = false, name = "memberId") memberId: Long?
    ): ResponseEntity<List<PostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPosts(tag, title, musicType, memberId, member))
    }

    @GetMapping("/{postId}")
    fun getPost(
        @AuthenticationPrincipal member: MemberDetails?,
        @PathVariable postId: Long
    ): ResponseEntity<PostWithCommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostById(postId, member))
    }


    @PostMapping
    fun createPost(
        @AuthenticationPrincipal member: MemberDetails?,
        @RequestBody createPostRequest: CreatePostRequest
    ): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(createPostRequest, member))
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        @AuthenticationPrincipal member: MemberDetails?,
        @PathVariable(value = "postId") id: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(postService.deletePost(id, member))
    }

    @PutMapping("/{postId}")
    fun updatePost(
        @AuthenticationPrincipal member: MemberDetails?,
        @PathVariable postId: Long,
        @RequestBody updatePostRequest: UpdatePostRequest
    ): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(postId, updatePostRequest, member))
    }


}