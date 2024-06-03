package com.teame1i4.newsfeed.domain.comment.service

import com.teame1i4.newsfeed.domain.comment.dto.request.CreateCommentRequest
import com.teame1i4.newsfeed.domain.comment.dto.request.UpdateCommentRequest
import com.teame1i4.newsfeed.domain.comment.dto.response.CommentResponse
import com.teame1i4.newsfeed.domain.comment.model.Comment
import com.teame1i4.newsfeed.domain.comment.model.toResponse
import com.teame1i4.newsfeed.domain.comment.repository.CommentRepository
import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.exception.UnauthorizedAccessException
import com.teame1i4.newsfeed.domain.member.adapter.MemberDetails
import com.teame1i4.newsfeed.domain.member.repository.MemberRepository
import com.teame1i4.newsfeed.domain.post.repository.PostRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository
) {

    fun getCommentList(postId: Long): List<CommentResponse> =
        commentRepository.findAllByPostIdOrderByCreatedAtAsc(postId)
            .map { it.toResponse(memberRepository.findByIdOrNull(it.memberId)!!) }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    fun createComment(
        member: MemberDetails,
        postId: Long,
        request: CreateCommentRequest
    ): CommentResponse {

        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val user =
            memberRepository.findByIdOrNull(member.id) ?: throw ModelNotFoundException("member", member.id)

        val comment = Comment(
            memberId = member.id,
            content = request.content,
            post = post
        )

        post.addComment(comment)
        commentRepository.save(comment)
        postRepository.save(post)

        return comment.toResponse(user)
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    fun updateComment(
        member: MemberDetails,
        postId: Long,
        commentId: Long,
        request: UpdateCommentRequest
    ): CommentResponse {

        val comment =
            commentRepository.findByPostIdAndId(postId, commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (comment.memberId != member.id) throw UnauthorizedAccessException()
        comment.content = request.content

        return commentRepository.save(comment).toResponse(memberRepository.findByIdOrNull(member.id)!!)
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    fun deleteComment(
        member: MemberDetails,
        postId: Long,
        commentId: Long
    ) {

        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        if (comment.memberId != member.id) throw UnauthorizedAccessException()

        post.deleteComment(comment)
        commentRepository.delete(comment)
    }

}