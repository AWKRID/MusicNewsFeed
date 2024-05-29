package com.teame1i4.newsfeed.domain.upvote.model

import com.teame1i4.newsfeed.domain.post.model.Post
import com.teame1i4.newsfeed.domain.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "upvote")
class Upvote(

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne
    @JoinColumn(name = "post_id")
    var post: Post

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}