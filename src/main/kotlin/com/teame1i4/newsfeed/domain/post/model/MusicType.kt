package com.teame1i4.newsfeed.domain.post.model

import jakarta.persistence.*

@Entity
@Table(name = "music_type")
class MusicType(
) {
    @Id
    val type: String? = null
}