package com.teame1i4.newsfeed.domain.post.service

import com.teame1i4.newsfeed.domain.comment.model.toResponse
import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.exception.TypeNotFoundException
import com.teame1i4.newsfeed.domain.exception.UnauthorizedAccessException
import com.teame1i4.newsfeed.domain.exception.YouTubeUrlNotValidException
import com.teame1i4.newsfeed.domain.member.adapter.MemberDetails
import com.teame1i4.newsfeed.domain.member.repository.MemberRepository
import com.teame1i4.newsfeed.domain.musictype.model.MusicType
import com.teame1i4.newsfeed.domain.musictype.repository.MusicTypeRepository
import com.teame1i4.newsfeed.domain.post.dto.CreatePostRequest
import com.teame1i4.newsfeed.domain.post.dto.PostResponse
import com.teame1i4.newsfeed.domain.post.dto.PostWithCommentResponse
import com.teame1i4.newsfeed.domain.post.dto.UpdatePostRequest
import com.teame1i4.newsfeed.domain.post.model.Post
import com.teame1i4.newsfeed.domain.post.model.PostStatus
import com.teame1i4.newsfeed.domain.post.model.toResponse
import com.teame1i4.newsfeed.domain.post.model.toWithCommentResponse
import com.teame1i4.newsfeed.domain.post.repository.PostRepository
import com.teame1i4.newsfeed.domain.upvote.repository.UpvoteRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    private val musicTypeRepository: MusicTypeRepository,
    private val memberRepository: MemberRepository,
    private val upvoteRepository: UpvoteRepository
) {

    @PreAuthorize("hasRole('USER')")
    fun createPost(
        member: MemberDetails,
        request: CreatePostRequest
    ): PostResponse {

        if (!musicTypeRepository.existsById(request.musicType)) throw TypeNotFoundException(request.musicType)
        val youtubeId = extractYoutubeId(request.musicUrl)

        val post = Post(
            title = request.title,
            content = request.content,
            musicUrl = youtubeId,
            memberId = member.id,
            musicType = request.musicType,
            tags = "#" + request.tags.joinToString("#") + "#"
        )

        val musicType: MusicType =
            musicTypeRepository.findByIdOrNull(request.musicType) ?: throw TypeNotFoundException(request.musicType)
        musicType.updateCountPost(true)
        musicTypeRepository.save(musicType)

        return postRepository.save(post).toResponse(
            memberRepository.findByIdOrNull(post.memberId)!!,
            upvoteRepository.existsByMemberIdAndPostId(member.id, post.id!!)
        )
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Transactional
    fun deletePost(
        member: MemberDetails,
        postId: Long
    ) {

        val post: Post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        if (post.memberId != member.id) throw UnauthorizedAccessException()
        if (post.postStatus == PostStatus.HIDDEN && member.authorities.toList()[0].toString() == "ROLE_USER")
            throw UnauthorizedAccessException()

        postRepository.delete(post)

        val musicType: MusicType =
            musicTypeRepository.findByIdOrNull(post.musicType) ?: throw TypeNotFoundException(post.musicType)
        musicType.updateCountPost(false)
        musicTypeRepository.save(musicType)
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    fun updatePost(
        member: MemberDetails,
        postId: Long,
        request: UpdatePostRequest
    ): PostResponse {

        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        if (post.memberId != member.id) throw UnauthorizedAccessException()
        var musicType: MusicType =
            musicTypeRepository.findByIdOrNull(request.musicType) ?: throw TypeNotFoundException(request.musicType)

        musicType.updateCountPost(false)
        musicTypeRepository.save(musicType)

        val youtubeId = extractYoutubeId(request.musicUrl)

        post.updatePost(request, youtubeId)

        musicType = musicTypeRepository.findByIdOrNull(post.musicType) ?: throw TypeNotFoundException(post.musicType)
        musicType.updateCountPost(true)
        musicTypeRepository.save(musicType)

        return postRepository.save(post).toResponse(
            memberRepository.findByIdOrNull(post.memberId)!!,
            upvoteRepository.existsByMemberIdAndPostId(member.id, post.id!!)
        )
    }

    fun getPosts(
        member: MemberDetails?,
        tag: String?,
        title: String?,
        musicType: String?,
        memberId: Long?
    ): List<PostResponse> {

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

        return posts.map {
            it.toResponse(
                memberRepository.findByIdOrNull(it.memberId)!!,
                if (member == null) false else upvoteRepository.existsByMemberIdAndPostId(member.id, it.id!!)
            )
        }
    }

    @Transactional
    fun getPostById(
        member: MemberDetails?,
        postId: Long
    ): PostWithCommentResponse {

        val post: Post =
            postRepository.findByIdOrNull(postId).also { it?.view() } ?: throw ModelNotFoundException("Post", postId)
        val hasUpvoted =
            if (member == null) false else upvoteRepository.existsByMemberIdAndPostId(member.id, postId)

        post.comments.sortBy { it.createdAt }
        val commentResponses = post.comments.map { it.toResponse(memberRepository.findByIdOrNull(it.memberId)!!) }

        return post.toWithCommentResponse(
            memberRepository.findByIdOrNull(post.memberId)!!,
            commentResponses,
            hasUpvoted
        )
    }

    private fun extractYoutubeId(musicUrl: String): String {
        val regex =
            Regex("""(?:https?://)?(?:www.|m.)?(?:youtube.com/watch\?v=|youtu.be/|youtube.com/embed/)(?<url>[a-zA-Z0-9_-]{11})""")

        return regex.find(musicUrl)?.groups?.get("url")?.value ?: throw YouTubeUrlNotValidException(musicUrl)
    }

}