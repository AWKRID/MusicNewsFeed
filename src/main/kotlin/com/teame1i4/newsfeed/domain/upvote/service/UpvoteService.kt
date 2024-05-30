package com.teame1i4.newsfeed.domain.upvote.service

import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.exception.UpvoteNotFoundException
import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import com.teame1i4.newsfeed.domain.post.model.toResponse
import com.teame1i4.newsfeed.domain.post.repository.PostRepository
import com.teame1i4.newsfeed.domain.upvote.dto.UpvoteRequest
import com.teame1i4.newsfeed.domain.upvote.model.Upvote
import com.teame1i4.newsfeed.domain.upvote.repository.UpvoteRepository
import com.teame1i4.newsfeed.domain.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UpvoteService(
    private val upvoteRepository: UpvoteRepository,
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun upvotePost(postId: Long, request: UpvoteRequest): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val member = memberRepository.findByIdOrNull(request.memberId) ?: throw ModelNotFoundException("Member", request.memberId)

        if(upvoteRepository.existsByMemberIdAndPostId(postId, request.memberId)) throw IllegalStateException()

        upvoteRepository.save(Upvote(member,post))

        post.addUpvote()

        return post.toResponse()
    }

    @Transactional
    fun cancelUpvote(postId: Long, request: UpvoteRequest): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        if(!memberRepository.existsById(request.memberId))  throw ModelNotFoundException("Member", request.memberId)

        val upvote = upvoteRepository.findByMemberIdAndPostId(request.memberId, postId) ?: throw UpvoteNotFoundException(
            postId, request.memberId
        )

        post.removeUpvote()

        upvoteRepository.delete(upvote)

        return post.toResponse()
    }
}
