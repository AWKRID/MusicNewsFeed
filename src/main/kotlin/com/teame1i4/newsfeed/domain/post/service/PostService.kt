package com.teame1i4.newsfeed.domain.post.service

import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.exception.TypeNotFoundException
import com.teame1i4.newsfeed.domain.post.dto.CreatePostRequest
import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import com.teame1i4.newsfeed.domain.post.dto.PostWithCommentResponse
import com.teame1i4.newsfeed.domain.post.dto.UpdatePostRequest
import com.teame1i4.newsfeed.domain.post.model.Post
import com.teame1i4.newsfeed.domain.post.model.toResponse
import com.teame1i4.newsfeed.domain.post.model.toWithCommentResponse
import com.teame1i4.newsfeed.domain.post.repository.MusicTypeRepository
import com.teame1i4.newsfeed.domain.post.repository.PostRepository
import com.teame1i4.newsfeed.domain.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    private val musicTypeRepository: MusicTypeRepository,
    private val userRepository: UserRepository,
) {

    fun createPost(request: CreatePostRequest): PostResponse {
        musicTypeRepository.findByIdOrNull(request.musicType) ?: throw TypeNotFoundException(request.musicType)
        userRepository.findByIdOrNull(request.userId) ?: throw ModelNotFoundException("user", request.userId)
        val post = Post(
            title = request.title,
            content = request.content,
            musicUrl = request.musicUrl,
            userId = request.userId,
            musicType = request.musicType,
            tags = "#"+request.tags.joinToString("#") + "#"
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
        musicTypeRepository.findByIdOrNull(request.musicType) ?: throw TypeNotFoundException(request.musicType)
        post.updatePost(request)
        return postRepository.save(post).toResponse()
    }

    fun getPosts(tag: String?): List<PostResponse> {
        val posts: List<Post> = if (tag.isNullOrBlank()) postRepository.findAll() else postRepository.findAllByTag(tag)
        return posts.map { it.toResponse() }
    }

    @Transactional
    fun getPostById(postId: Long): PostWithCommentResponse {
        val post: Post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        return post.toWithCommentResponse()
    }
}