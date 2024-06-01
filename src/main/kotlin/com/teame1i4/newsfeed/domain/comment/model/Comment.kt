package com.teame1i4.newsfeed.domain.comment.model

import com.teame1i4.newsfeed.domain.comment.dto.response.CommentResponse
import com.teame1i4.newsfeed.domain.member.model.Member
import com.teame1i4.newsfeed.domain.member.model.toResponse
import com.teame1i4.newsfeed.domain.post.model.Post
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

    @Column(name = "member_id", nullable = false)
    val memberId: Long,

    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: Post

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

}

fun Comment.toResponse(member: Member): CommentResponse =
    CommentResponse(
        id = id!!,
        member = member.toResponse(),
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt
    )