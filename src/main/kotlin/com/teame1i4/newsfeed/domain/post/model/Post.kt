package com.teame1i4.newsfeed.domain.post.model

import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import jakarta.persistence.*
import java.time.LocalDateTime

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

    fun updatePost(title: String, userId: Long, musicUrl: String, content: String) {
        this.title = title
        this.userId = userId
        this.musicUrl = musicUrl
        this.content = content
    }
}

fun Post.toResponse(): PostResponse {
    return PostResponse(
        id = id!!,
        title = title,
        content = content,
        musicUrl = musicUrl,
        // TODO(need to update)
        writer = "username for id $userId",
        timeCreated = LocalDateTime.now(),
        timeUpdated = LocalDateTime.now()
        //
    )
}