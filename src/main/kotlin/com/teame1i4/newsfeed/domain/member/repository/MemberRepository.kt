package com.teame1i4.newsfeed.domain.member.repository

import com.teame1i4.newsfeed.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): Member?
    fun findByIdAndUsername(id: Long, username: String): Member?
    fun findAllByIdIn(id : Set<Long>): List<Member>
}