package com.teame1i4.newsfeed.domain.musictype.model

import com.teame1i4.newsfeed.domain.musictype.dto.response.MusicTypeCountResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "music_type")
class MusicType(
    @Id
    val type: String,

    @Column(name = "count_post", nullable = false)
    var countPost: Long
) {

    fun updateCountPost(increase: Boolean) {
        when (increase) {
            true -> countPost++
            false -> countPost--
        }
    }
}

fun MusicType.toResponse(): MusicTypeCountResponse = MusicTypeCountResponse(
    musicType = type,
    count = countPost
)