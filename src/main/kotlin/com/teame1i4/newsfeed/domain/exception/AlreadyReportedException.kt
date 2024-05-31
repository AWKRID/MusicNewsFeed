package com.teame1i4.newsfeed.domain.exception

data class AlreadyReportedException(
    val postId: Long, val memberId: Long
) : RuntimeException("Already reported with given Member Id: $memberId to Post Id: $postId")
