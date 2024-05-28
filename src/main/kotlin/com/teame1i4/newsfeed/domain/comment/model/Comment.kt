package com.teame1i4.newsfeed.domain.comment.model

import com.teame1i4.newsfeed.domain.comment.dto.response.CommentResponse
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "comment")
class Comment(
    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "user_Id", nullable = false)
    val userId: Long,

    @Column(name = "post_id", nullable = false)
    val postId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @CreatedDate
    @Column(updatable = false, nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()

    fun toResponse(): CommentResponse = CommentResponse(
        id = id!!,
        userName = "" ,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

