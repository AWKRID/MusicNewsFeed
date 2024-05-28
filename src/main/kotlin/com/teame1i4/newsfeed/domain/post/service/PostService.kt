package com.teame1i4.newsfeed.domain.post.service

import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import com.teame1i4.newsfeed.domain.post.dto.UpdatePostRequest
import org.springframework.stereotype.Service

@Service
class PostService {

    fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse {
        // 포스트를 요청에따라 업데이트하고 DB에 저장한 뒤 Response Dto로 반환
        TODO()
    }

}