package com.teame1i4.newsfeed.domain.comment.model

import com.teame1i4.newsfeed.domain.comment.dto.response.CommentResponse
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime


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

    @CreatedDate()
    @Column(name = "time_created")
    var timeCreated: LocalDateTime? = null

    @LastModifiedDate()
    @Column(name = "time_updated")
    var timeUpdated: LocalDateTime? = null

    fun toResponse(): CommentResponse = CommentResponse(
        id = id!!,
        userName = "" ,
        content = content,
        timeCreated = timeCreated,
        timeUpdated = timeUpdated
    )
}

