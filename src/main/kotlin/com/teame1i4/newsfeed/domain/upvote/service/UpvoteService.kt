package com.teame1i4.newsfeed.domain.upvote.service

import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.post.repository.PostRepository
import com.teame1i4.newsfeed.domain.upvote.dto.UpvoteToggleRequest
import com.teame1i4.newsfeed.domain.upvote.dto.UpvoteToggleResponse
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
    fun upvoteToggle(postId: Long, request: UpvoteToggleRequest): UpvoteToggleResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val user = userRepository.findByIdOrNull(request.userId) ?: throw ModelNotFoundException("User", request.userId)

        if(upvoteRepository.existsByUserIdAndPostId(request.userId, postId)) {

            val upvote = upvoteRepository.findByUserIdAndPostId(request.userId, postId) ?: throw RuntimeException("Upvote not found")

            post.upvoteCount -= 1

            upvoteRepository.delete(upvote)
        } else {

            val upvote = Upvote(user, post)

            upvoteRepository.save(upvote)

            post.upvoteCount += 1

        }
        return UpvoteToggleResponse(
            upvoteCount = post.upvoteCount
        )
    }
}
