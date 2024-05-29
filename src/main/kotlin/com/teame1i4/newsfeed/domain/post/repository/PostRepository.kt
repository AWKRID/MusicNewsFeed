package com.teame1i4.newsfeed.domain.post.repository

import com.teame1i4.newsfeed.domain.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PostRepository : JpaRepository<Post, Long> {
    @Query("select p from Post p where p.tags like concat('%#',:tag,'#%')")
    fun findAllByTag(@Param("tag") tag: String): List<Post>
}