package com.teame1i4.newsfeed.domain.upvote.service

import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.exception.UnauthorizedAccessException
import com.teame1i4.newsfeed.domain.exception.UpvoteAlreadyExistException
import com.teame1i4.newsfeed.domain.exception.UpvoteNotFoundException
import com.teame1i4.newsfeed.domain.member.adapter.MemberDetails
import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import com.teame1i4.newsfeed.domain.post.model.toResponse
import com.teame1i4.newsfeed.domain.post.repository.PostRepository
import com.teame1i4.newsfeed.domain.upvote.model.Upvote
import com.teame1i4.newsfeed.domain.upvote.repository.UpvoteRepository
import com.teame1i4.newsfeed.domain.member.repository.MemberRepository
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
    fun upvotePost(postId: Long, member: MemberDetails): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val user = memberRepository.findByIdOrNull(member.memberId) ?: throw ModelNotFoundException("Member", member.memberId)

        if(upvoteRepository.existsByMemberIdAndPostId(user.id!!, postId)) throw UpvoteAlreadyExistException()

        upvoteRepository.save(Upvote(user,post))

        post.addUpvote()

        return post.toResponse(memberRepository.findByIdOrNull(user.id!!)!!)
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    fun cancelUpvote(postId: Long, member: MemberDetails): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        if(!memberRepository.existsById(member.memberId))  throw ModelNotFoundException("Member", member.memberId)

        val upvote = upvoteRepository.findByMemberIdAndPostId(member.memberId, postId) ?: throw UpvoteNotFoundException(
            postId, member.memberId
        )

        post.removeUpvote()

        upvoteRepository.delete(upvote)

        return post.toResponse(memberRepository.findByIdOrNull(member.memberId)!!)
    }
}
