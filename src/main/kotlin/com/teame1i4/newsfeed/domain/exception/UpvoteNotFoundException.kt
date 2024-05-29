package com.teame1i4.newsfeed.domain.exception

data class UpvoteNotFoundException(
    val postId: Long, val userId: Long
) : RuntimeException("Post with post Id: $postId has already reported by the user with user Id: $userId")
