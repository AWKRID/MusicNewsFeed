package com.teame1i4.newsfeed.domain.post.model

import com.teame1i4.newsfeed.domain.comment.model.Comment
import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import com.teame1i4.newsfeed.domain.post.dto.PostWithCommentResponse
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime


@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "post")
class Post(

    @Column
    var title: String,

    @Column
    var userId: Long,

    @Column
    var musicUrl: String,

    @Column
    var content: String,


//    @Column
//    var tags: String,
//
//    @Column
//    var viewCount: Long = 0,

//    @Enumerated(EnumType.STRING)
//    @Column
//    var postStatus: PostStatus = PostStatus.HIDDEN or PUBLIC,
//
//    @Column
//    var upvoteCount: Long = 0,
//
//    @Column
//    var reportCount: Long = 0

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @CreatedDate
    @Column(updatable = false, nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()


    @OneToMany(mappedBy = "post")
    var comments: MutableList<Comment> = mutableListOf()

    fun updatePost(title: String, userId: Long, musicUrl: String, content: String) {
        this.title = title
        this.userId = userId
        this.musicUrl = musicUrl
        this.content = content
    }

    fun createComment(comment: Comment) {
        this.comments.add(comment)
    }

    fun deleteComment(comment: Comment) {
        this.comments.remove(comment)
    }
}

fun Post.toResponse(): PostResponse {
    return PostResponse(
        id = id!!,
        title = title,
        content = content,
        musicUrl = musicUrl,
        // TODO(need to update)
        username = "username for id $userId",
        createdAt = createdAt,
        updatedAt = updatedAt
        //
    )
}

fun Post.toWithCommentResponse(): PostWithCommentResponse = PostWithCommentResponse(
    id = id!!, title = title, content = content,
    musicUrl = musicUrl, username = "username for id $userId",
    createdAt = createdAt, updatedAt = updatedAt,
    comments = comments.map { it.toResponse() }
)