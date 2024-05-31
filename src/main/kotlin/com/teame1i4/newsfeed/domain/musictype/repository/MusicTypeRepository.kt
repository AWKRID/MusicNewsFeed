package com.teame1i4.newsfeed.domain.musictype.repository

import com.teame1i4.newsfeed.domain.musictype.model.MusicType
import org.springframework.data.jpa.repository.JpaRepository

interface MusicTypeRepository : JpaRepository<MusicType, String> {
    fun findAllByOrderByCountPostDesc() : List<MusicType>
}