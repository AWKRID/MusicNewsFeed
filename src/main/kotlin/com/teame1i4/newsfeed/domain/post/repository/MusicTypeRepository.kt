package com.teame1i4.newsfeed.domain.post.repository

import com.teame1i4.newsfeed.domain.post.model.MusicType
import org.springframework.data.jpa.repository.JpaRepository

interface MusicTypeRepository: JpaRepository<MusicType, String> {
}