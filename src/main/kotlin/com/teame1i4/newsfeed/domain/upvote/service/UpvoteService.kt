package com.teame1i4.newsfeed.domain.upvote.service

import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.exception.UnauthorizedAccessException
import com.teame1i4.newsfeed.domain.exception.UpvoteAlreadyExistException
import com.teame1i4.newsfeed.domain.exception.UpvoteNotFoundException
import com.teame1i4.newsfeed.domain.member.adapter.MemberDetails
import com.teame1i4.newsfeed.domain.member.repository.MemberRepository
import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import com.teame1i4.newsfeed.domain.post.model.toResponse
import com.teame1i4.newsfeed.domain.post.repository.PostRepository
import com.teame1i4.newsfeed.domain.upvote.model.Upvote
import com.teame1i4.newsfeed.domain.upvote.repository.UpvoteRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class UpvoteService(
    private val upvoteRepository: UpvoteRepository,
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository
) {

    @PreAuthorize("hasRole('USER')")
    @Transactional
    fun upvotePost(
        member: MemberDetails,
        postId: Long
    ): PostResponse {

        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val user =
            memberRepository.findByIdOrNull(member.id) ?: throw ModelNotFoundException("Member", member.id)
        if (upvoteRepository.existsByMemberIdAndPostId(user.id!!, postId)) throw UpvoteAlreadyExistException()

        upvoteRepository.save(Upvote(user, post))
        post.addUpvote()

        return post.toResponse(
            memberRepository.findByIdOrNull(user.id!!)!!,
            true
        )
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    fun cancelUpvote(
        member: MemberDetails,
        postId: Long
    ): PostResponse {

        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        if (!memberRepository.existsById(member.id)) throw ModelNotFoundException("Member", member.id)
        val upvote = upvoteRepository.findByMemberIdAndPostId(member.id, postId) ?: throw UpvoteNotFoundException(
            postId, member.id
        )

        post.removeUpvote()
        upvoteRepository.delete(upvote)

        return post.toResponse(
            memberRepository.findByIdOrNull(member.id)!!,
            false
        )
    }

}