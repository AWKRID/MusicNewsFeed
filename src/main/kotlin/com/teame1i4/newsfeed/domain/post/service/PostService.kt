package com.teame1i4.newsfeed.domain.post.service

import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.exception.TypeNotFoundException
import com.teame1i4.newsfeed.domain.exception.UnauthorizedAccessException
import com.teame1i4.newsfeed.domain.exception.YouTubeUrlNotValidException
import com.teame1i4.newsfeed.domain.member.adapter.MemberDetails
import com.teame1i4.newsfeed.domain.post.dto.CreatePostRequest
import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import com.teame1i4.newsfeed.domain.post.dto.PostWithCommentResponse
import com.teame1i4.newsfeed.domain.post.dto.UpdatePostRequest
import com.teame1i4.newsfeed.domain.post.repository.PostRepository
import com.teame1i4.newsfeed.domain.member.repository.MemberRepository
import com.teame1i4.newsfeed.domain.musictype.model.MusicType
import com.teame1i4.newsfeed.domain.musictype.repository.MusicTypeRepository
import com.teame1i4.newsfeed.domain.post.model.*
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    private val musicTypeRepository: MusicTypeRepository,
    private val memberRepository: MemberRepository,
) {

    @PreAuthorize("hasRole('USER')")
    fun createPost(request: CreatePostRequest, member: MemberDetails): PostResponse {
        if(!musicTypeRepository.existsById(request.musicType)) throw TypeNotFoundException(request.musicType)
//        memberRepository.findByIdOrNull(member.memberId) ?: throw ModelNotFoundException("member", member.memberId)
        val youtubeId = extractYoutubeId(request.musicUrl)

        val post = Post(
            title = request.title,
            content = request.content,
            musicUrl = youtubeId,
            memberId = member.memberId,
            musicType = request.musicType,
            tags = "#" + request.tags.joinToString("#") + "#"
        )

        val musicType: MusicType =
            musicTypeRepository.findByIdOrNull(request.musicType) ?: throw TypeNotFoundException(request.musicType)
        musicType.updateCountPost(true)
        musicTypeRepository.save(musicType)

        return postRepository.save(post).toResponse(memberRepository.findByIdOrNull(post.memberId)!!)
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    fun deletePost(postId: Long, member: MemberDetails) {
        val post: Post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        if(post.memberId != member.memberId) throw UnauthorizedAccessException()

        postRepository.delete(post)

        val musicType: MusicType =
            musicTypeRepository.findByIdOrNull(post.musicType) ?: throw TypeNotFoundException(post.musicType)
        musicType.updateCountPost(false)
        musicTypeRepository.save(musicType)
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    fun updatePost(postId: Long, request: UpdatePostRequest, member: MemberDetails): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        if(post.memberId != member.memberId) throw UnauthorizedAccessException()
        var musicType: MusicType =
            musicTypeRepository.findByIdOrNull(request.musicType) ?: throw TypeNotFoundException(request.musicType)

        musicType.updateCountPost(false)
        musicTypeRepository.save(musicType)

        val youtubeId = extractYoutubeId(request.musicUrl)

        post.updatePost(request, youtubeId)

        musicType = musicTypeRepository.findByIdOrNull(post.musicType) ?: throw TypeNotFoundException(post.musicType)
        musicType.updateCountPost(true)
        musicTypeRepository.save(musicType)

        return postRepository.save(post).toResponse(memberRepository.findByIdOrNull(post.memberId)!!)
    }

    fun getPosts(tag: String?, title: String?, musicType: String?, memberId: Long?): List<PostResponse> {

        val posts: List<Post> =
            if (!tag.isNullOrBlank()) postRepository.findAllByTagAndPostStatusOrderByCreatedAtDesc(tag)
            else if (!title.isNullOrBlank()) postRepository.findAllByTitleContainingAndPostStatusOrderByCreatedAtDesc(
                title,
                PostStatus.PUBLIC
            )
            else if (!musicType.isNullOrBlank()) postRepository.findAllByMusicTypeAndPostStatusOrderByCreatedAtDesc(
                musicType,
                PostStatus.PUBLIC
            )
            else if (memberId != null) postRepository.findAllByMemberIdAndPostStatusOrderByCreatedAtDesc(
                memberId,
                PostStatus.PUBLIC
            )
            else postRepository.findAllByPostStatusOrderByCreatedAtDesc(PostStatus.PUBLIC)

        return posts.map { it.toResponse(memberRepository.findByIdOrNull(it.memberId)!!) }
    }

    @Transactional
    fun getPostById(postId: Long): PostWithCommentResponse {
        val post: Post =
            postRepository.findByIdOrNull(postId).also { it?.view() } ?: throw ModelNotFoundException("Post", postId)

        post.comments.sortBy { it.createdAt }

        return post.toWithCommentResponse(memberRepository.findByIdOrNull(post.memberId)!!)
    }

    private fun extractYoutubeId(musicUrl: String): String {
        val regex = Regex("""(?:https?://)?(?:www.|m.)?(?:youtube.com/watch\?v=|youtu.be/|youtube.com/embed/)(?<url>[a-zA-Z0-9_-]{11})""")

        val youtubeId = regex.find(musicUrl)?.groups?.get("url")?.value ?: throw YouTubeUrlNotValidException(musicUrl)

        return youtubeId
    }
}