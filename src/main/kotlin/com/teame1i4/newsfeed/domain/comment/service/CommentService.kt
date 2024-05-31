package com.teame1i4.newsfeed.domain.comment.service

import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.comment.dto.request.CreateCommentRequest
import com.teame1i4.newsfeed.domain.comment.dto.request.UpdateCommentRequest
import com.teame1i4.newsfeed.domain.comment.dto.response.CommentResponse
import com.teame1i4.newsfeed.domain.comment.model.Comment
import com.teame1i4.newsfeed.domain.comment.repository.CommentRepository
import com.teame1i4.newsfeed.domain.member.repository.MemberRepository
import com.teame1i4.newsfeed.domain.post.repository.PostRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository
) {
    fun getCommentList(postId: Long): List<CommentResponse> {
        return commentRepository.findAllByPostIdOrderByCreatedAtAsc(postId).map { it.toResponse() }
    }

    @Transactional
    fun createComment(postId: Long, request: CreateCommentRequest): CommentResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        memberRepository.findByIdOrNull(request.memberId) ?: throw ModelNotFoundException("member", request.memberId)

        val comment = Comment(
            memberId = request.memberId,
            content = request.content,
            post = post
        )
        post.createComment(comment)
        commentRepository.save(comment)
        postRepository.save(post)
        return comment.toResponse()
    }

    @Transactional
    fun updateComment(
        postId: Long,
        commentId: Long,
        request: UpdateCommentRequest
    ): CommentResponse {
        val comment =
            commentRepository.findByPostIdAndId(postId, commentId) ?: throw ModelNotFoundException("Comment", commentId)

        val (content) = request
        comment.content = content

        return commentRepository.save(comment).toResponse()
    }

    @Transactional
    fun deleteComment(postId: Long, commentId: Long) {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        post.deleteComment(comment)
        commentRepository.delete(comment)
    }

}