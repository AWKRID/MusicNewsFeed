package com.teame1i4.newsfeed.domain.post.service

import com.teame1i4.newsfeed.domain.post.dto.CreatePostRequest
import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import org.springframework.stereotype.Service

@Service
class PostService {

    fun createPost(createPostRequest: CreatePostRequest): PostResponse? {
        TODO("Not yet implemented")

    }

    fun deletePost(id: Long) : Unit {
        TODO("not implemented")
    }
}