package com.teame1i4.newsfeed.domain.report.model

import com.teame1i4.newsfeed.domain.post.model.Post
import com.teame1i4.newsfeed.domain.member.model.Member
import jakarta.persistence.*


@Entity
@Table(name = "report")
class Report(

    @ManyToOne
    @JoinColumn(name = "member_id")
    var member: Member,

    @ManyToOne
    @JoinColumn(name = "post_id")
    var post: Post,

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}