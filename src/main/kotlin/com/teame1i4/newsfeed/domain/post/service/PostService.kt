package com.teame1i4.newsfeed.domain.post.service

import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.exception.TypeNotFoundException
import com.teame1i4.newsfeed.domain.post.dto.CreatePostRequest
import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import com.teame1i4.newsfeed.domain.post.dto.PostWithCommentResponse
import com.teame1i4.newsfeed.domain.post.dto.UpdatePostRequest
import com.teame1i4.newsfeed.domain.post.model.Post
import com.teame1i4.newsfeed.domain.post.model.toResponse
import com.teame1i4.newsfeed.domain.post.model.toWithCommentResponse
import com.teame1i4.newsfeed.domain.post.repository.MusicTypeRepository
import com.teame1i4.newsfeed.domain.post.repository.PostRepository
import com.teame1i4.newsfeed.domain.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    private val musicTypeRepository: MusicTypeRepository,
    private val memberRepository: MemberRepository,
) {

    fun createPost(request: CreatePostRequest): PostResponse {
        musicTypeRepository.findByIdOrNull(request.musicType) ?: throw TypeNotFoundException(request.musicType)
        memberRepository.findByIdOrNull(request.memberId) ?: throw ModelNotFoundException("member", request.memberId)
        val post = Post(
            title = request.title,
            content = request.content,
            musicUrl = request.musicUrl,
            memberId = request.memberId,
            musicType = request.musicType,
            tags = "#" + request.tags.joinToString("#") + "#"
        )
        return postRepository.save(post).toResponse()
    }

    @Transactional
    fun deletePost(postId: Long) {
        val post: Post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        postRepository.delete(post)
    }

    @Transactional
    fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        musicTypeRepository.findByIdOrNull(request.musicType) ?: throw TypeNotFoundException(request.musicType)
        post.updatePost(request)
        return postRepository.save(post).toResponse()
    }

    fun getPosts(tag: String?, title: String?, musicType: String?, memberId: Long?): List<PostResponse> {

        val posts: List<Post> = if (!tag.isNullOrBlank()) postRepository.findAllByTagOrderByCreatedAtDesc(tag)
        else if (!title.isNullOrBlank()) postRepository.findAllByTitleContainingOrderByCreatedAtDesc(title)
        else if (!musicType.isNullOrBlank()) postRepository.findAllByMusicTypeOrderByCreatedAtDesc(musicType)
        else if (memberId != null) postRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId)
        else postRepository.findAllByOrderByCreatedAtDesc()

        return posts.map { it.toResponse() }
    }

    @Transactional
    fun getPostById(postId: Long): PostWithCommentResponse {
        val post: Post =
            postRepository.findByIdOrNull(postId).also { it?.view() } ?: throw ModelNotFoundException("Post", postId)
        return post.toWithCommentResponse()
    }
}