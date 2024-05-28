package com.teame1i4.newsfeed.domain.comment.service

import com.teame1i4.newsfeed.domain.comment.dto.request.CreateCommentRequest
import com.teame1i4.newsfeed.domain.comment.dto.request.UpdateCommentRequest
import com.teame1i4.newsfeed.domain.comment.dto.response.CommentResponse
import com.teame1i4.newsfeed.domain.comment.model.Comment
import com.teame1i4.newsfeed.domain.comment.repository.CommentRepository
import com.teame1i4.newsfeed.domain.post.repository.PostRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository
) {
    @Transactional
    fun createComment(postId: Long, request: CreateCommentRequest): CommentResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw RuntimeException("Post not found")

        val comment = Comment(
            userId = request.userId,
            content = request.content,
            postId = postId
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
            commentRepository.findByPostIdAndId(postId, commentId) ?: throw RuntimeException("Comment not found")

        val (content) = request
        comment.content = content

        return commentRepository.save(comment).toResponse()
    }

    @Transactional
    fun deleteComment(postId: Long, commentId: Long) {
        val post = postRepository.findByIdOrNull(postId) ?: throw RuntimeException("Post not found")
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw RuntimeException("Comment not found")
        post.deleteComment(comment)
        commentRepository.delete(comment)
    }

}