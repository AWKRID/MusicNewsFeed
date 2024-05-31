package com.teame1i4.newsfeed.domain.post.repository

import com.teame1i4.newsfeed.domain.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PostRepository : JpaRepository<Post, Long> {
    @Query("select p from Post p where p.tags like concat('%#',:tag,'#%') order by p.createdAt desc")
    fun findAllByTagOrderByCreatedAtDesc(@Param("tag") tag: String): List<Post>

    fun findAllByTitleContainingOrderByCreatedAtDesc(title: String) : List<Post>
    fun findAllByMusicTypeOrderByCreatedAtDesc(musicTypeId: String) : List<Post>
    fun findAllByMemberIdOrderByCreatedAtDesc(memberId : Long) : List<Post>
    fun findAllByOrderByCreatedAtDesc() : List<Post>

}