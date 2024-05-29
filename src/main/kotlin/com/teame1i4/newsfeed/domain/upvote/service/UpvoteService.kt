package com.teame1i4.newsfeed.domain.upvote.service

import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.exception.UpvoteNotFoundException
import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import com.teame1i4.newsfeed.domain.post.model.toResponse
import com.teame1i4.newsfeed.domain.post.repository.PostRepository
import com.teame1i4.newsfeed.domain.upvote.dto.UpvoteRequest
import com.teame1i4.newsfeed.domain.upvote.model.Upvote
import com.teame1i4.newsfeed.domain.upvote.repository.UpvoteRepository
import com.teame1i4.newsfeed.domain.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UpvoteService(
    private val upvoteRepository: UpvoteRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun upvotePost(postId: Long, request: UpvoteRequest): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val user = userRepository.findByIdOrNull(request.userId) ?: throw ModelNotFoundException("User", request.userId)

        if(upvoteRepository.existsByUserIdAndPostId(postId, request.userId)) throw IllegalStateException()

        upvoteRepository.save(Upvote(user,post))

        post.addUpvote()

        return post.toResponse()
    }

    @Transactional
    fun cancelUpvote(postId: Long, request: UpvoteRequest): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        if(!userRepository.existsById(request.userId))  throw ModelNotFoundException("User", request.userId)

        val upvote = upvoteRepository.findByUserIdAndPostId(request.userId, postId) ?: throw UpvoteNotFoundException(
            postId, request.userId
        )

        post.removeUpvote()

        upvoteRepository.delete(upvote)

        return post.toResponse()
    }
}
