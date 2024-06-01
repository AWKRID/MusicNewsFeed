package com.teame1i4.newsfeed.domain.post.repository

import com.teame1i4.newsfeed.domain.post.model.Post
import com.teame1i4.newsfeed.domain.post.model.PostStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PostRepository : JpaRepository<Post, Long> {
    @Query("select p from Post p where p.tags like concat('%#',:tag,'#%') and p.postStatus = 'PUBLIC' " +
            "order by p.createdAt desc")
    fun findAllByTagAndPostStatusOrderByCreatedAtDesc(@Param("tag") tag: String): List<Post>
    fun findAllByTitleContainingAndPostStatusOrderByCreatedAtDesc(title: String, postStatus: PostStatus): List<Post>
    fun findAllByMusicTypeAndPostStatusOrderByCreatedAtDesc(musicTypeId: String, postStatus: PostStatus): List<Post>
    fun findAllByMemberIdAndPostStatusOrderByCreatedAtDesc(memberId: Long, postStatus: PostStatus): List<Post>
    fun findAllByPostStatusOrderByCreatedAtDesc(postStatus: PostStatus): List<Post>
    fun findAllByMemberIdInAndPostStatusOrderByCreatedAtDesc(memberIds: List<Long>, postStatus: PostStatus): List<Post>
}