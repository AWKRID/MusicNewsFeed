package com.teame1i4.newsfeed.domain.post.service

import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.post.dto.CreatePostRequest
import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import com.teame1i4.newsfeed.domain.post.dto.PostWithCommentResponse
import com.teame1i4.newsfeed.domain.post.dto.UpdatePostRequest
import com.teame1i4.newsfeed.domain.post.model.Post
import com.teame1i4.newsfeed.domain.post.model.toResponse
import com.teame1i4.newsfeed.domain.post.model.toWithCommentResponse
import com.teame1i4.newsfeed.domain.post.repository.PostRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository
) {

    fun createPost(request: CreatePostRequest): PostResponse {
        val post = Post(
            title = request.title,
            content = request.content,
            musicUrl = request.musicUrl,
            userId = request.userId,
        )
        return postRepository.save(post).toResponse()

    }

    @Transactional
    fun deletePost(postId: Long) {
        val post: Post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        postRepository.delete(post)
    }

    @Transactional
    fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        post.updatePost(request.title, request.userId, request.musicUrl, request.content)

        return postRepository.save(post).toResponse()
    }

    fun getPosts(): List<PostResponse> {
        val posts: List<Post> = postRepository.findAll()
        return posts.map { it.toResponse() }
    }

    fun getPostById(postId: Long): PostWithCommentResponse {
        val post: Post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        return post.toWithCommentResponse()
    }
}