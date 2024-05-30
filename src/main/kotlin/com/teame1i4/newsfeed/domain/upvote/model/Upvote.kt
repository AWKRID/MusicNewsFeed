package com.teame1i4.newsfeed.domain.upvote.model

import com.teame1i4.newsfeed.domain.post.model.Post
import com.teame1i4.newsfeed.domain.member.model.Member
import jakarta.persistence.*

@Entity
@Table(name = "upvote")
class Upvote(

    @ManyToOne
    @JoinColumn(name = "member_id")
    var member: Member,

    @ManyToOne
    @JoinColumn(name = "post_id")
    var post: Post

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}