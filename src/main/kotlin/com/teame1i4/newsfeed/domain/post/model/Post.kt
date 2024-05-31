package com.teame1i4.newsfeed.domain.post.model

import com.teame1i4.newsfeed.domain.comment.model.Comment
import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import com.teame1i4.newsfeed.domain.post.dto.PostWithCommentResponse
import com.teame1i4.newsfeed.domain.post.dto.UpdatePostRequest
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime


@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "post")
class Post(

    @Column
    var title: String,

    @Column
    var memberId: Long,

    @Column
    var musicUrl: String,

    @Column
    var content: String,


    @Column
    var tags: String,

    @Column(name = "music_type_id")
    var musicType: String,

    @Column
    var viewCount: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column
    var postStatus: PostStatus = PostStatus.PUBLIC,

    @Column
    var upvoteCount: Long = 0,

    @Column
    var reportCount: Long = 0

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @CreatedDate
    @Column(updatable = false, nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()


    @OneToMany(mappedBy = "post")
    var comments: MutableList<Comment> = mutableListOf()

    fun updatePost(request: UpdatePostRequest, musicUrl: String) {
        this.title = request.title
        this.musicUrl = musicUrl
        this.content = request.content
        this.tags = "#" + request.tags.joinToString("#") + "#"
        this.musicType = request.musicType
        this.updatedAt = LocalDateTime.now()
    }

    fun createComment(comment: Comment) {
        this.comments.add(comment)
    }

    fun deleteComment(comment: Comment) {
        this.comments.remove(comment)
    }

    fun addUpvote() {
        upvoteCount += 1
    }

    fun removeUpvote() {
        upvoteCount -= 1
    }

    private fun hidePost() {
        postStatus = PostStatus.HIDDEN
    }

    fun addReport() {
        reportCount += 1
        if (reportCount >= 5) hidePost()
    }

    fun view() {
        viewCount += 1
    }
}

fun Post.toResponse(): PostResponse {
    return PostResponse(
        id = id!!,
        title = title,
        content = content,
        musicUrl = musicUrl,
        // TODO(need to update)
        username = "username for id $memberId",
        musicType = musicType,
        tags = tags.split("#").filter(String::isNotEmpty),
        viewCount = viewCount,
        upvoteCount = upvoteCount,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Post.toWithCommentResponse(): PostWithCommentResponse {
    return PostWithCommentResponse(
        id = id!!,
        title = title,
        content = content,
        musicUrl = musicUrl,
        username = "username for id $memberId",
        musicType = musicType,
        tags = tags.split("#").filter(String::isNotEmpty),
        viewCount = viewCount,
        upvoteCount = upvoteCount,
        createdAt = createdAt,
        updatedAt = updatedAt,
        comments = comments.map { it.toResponse() }
    )
}