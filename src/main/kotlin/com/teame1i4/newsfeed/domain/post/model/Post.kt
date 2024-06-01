package com.teame1i4.newsfeed.domain.post.model

import com.teame1i4.newsfeed.domain.comment.dto.response.CommentResponse
import com.teame1i4.newsfeed.domain.comment.model.Comment
import com.teame1i4.newsfeed.domain.member.model.Member
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

    @Column(name = "title")
    var title: String,

    @Column(name = "member_id")
    var memberId: Long,

    @Column(name = "music_url")
    var musicUrl: String,

    @Column(name = "content")
    var content: String,

    @Column(name = "tags")
    var tags: String,

    @Column(name = "music_type_id")
    var musicType: String,

    @Column(name = "view_count")
    var viewCount: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "post_status")
    var postStatus: PostStatus = PostStatus.PUBLIC,

    @Column(name = "upvote_count")
    var upvoteCount: Long = 0,

    @Column(name = "report_count")
    var reportCount: Long = 0,

    @Column(name = "comment_count")
    var commentCount: Long = 0

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at", nullable = false)
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

    fun addComment(comment: Comment) {
        this.comments.add(comment)
        commentCount += 1
    }

    fun deleteComment(comment: Comment) {
        this.comments.remove(comment)
        commentCount -= 1
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

fun Post.toResponse(member: Member, hasUpvoted: Boolean): PostResponse {
    return PostResponse(
        id = id!!,
        title = title,
        content = content,
        musicUrl = musicUrl,
        member = member.toResponse(),
        musicType = musicType,
        tags = tags.split("#").filter(String::isNotEmpty),
        viewCount = viewCount,
        upvoteCount = upvoteCount,
        createdAt = createdAt,
        updatedAt = updatedAt,
        commentCount = commentCount,
        hasUpvoted = hasUpvoted
    )
}

fun Post.toWithCommentResponse(
    member: Member, commentResponses: List<CommentResponse>, hasUpvoted: Boolean
): PostWithCommentResponse {
    return PostWithCommentResponse(
        id = id!!,
        title = title,
        content = content,
        musicUrl = musicUrl,
        member = member.toResponse(),
        musicType = musicType,
        tags = tags.split("#").filter(String::isNotEmpty),
        viewCount = viewCount,
        upvoteCount = upvoteCount,
        createdAt = createdAt,
        updatedAt = updatedAt,
        comments = commentResponses,
        hasUpvoted = hasUpvoted
    )
}