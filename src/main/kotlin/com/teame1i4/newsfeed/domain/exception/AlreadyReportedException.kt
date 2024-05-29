package com.teame1i4.newsfeed.domain.exception

data class AlreadyReportedException(
    val postId: Long, val userId: Long
) : RuntimeException("Upvote not found with given Post Id: $postId and given User Id: $userId")
