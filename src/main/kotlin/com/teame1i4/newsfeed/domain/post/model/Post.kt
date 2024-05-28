package com.teame1i4.newsfeed.domain.post.model

import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import jakarta.persistence.*
import java.time.LocalDateTime

class Post {
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