package com.teame1i4.newsfeed.domain.report.model

import com.teame1i4.newsfeed.domain.post.model.Post
import com.teame1i4.newsfeed.domain.user.model.User
import jakarta.persistence.*


@Entity
@Table(name = "report")
class Report(

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne
    @JoinColumn(name = "post_id")
    var post: Post,

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}