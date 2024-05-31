package com.teame1i4.newsfeed.domain.musictype.model

import jakarta.persistence.*

@Entity
@Table(name = "music_type")
class MusicType(
) {
    @Id
    val type: String? = null

    @Column(name = "count_post", nullable = false)
    private var countPost: Long = 0

    fun updateCountPost(increase : Boolean) {
        when(increase) {
            true -> countPost++
            false -> countPost--
        }
    }
}