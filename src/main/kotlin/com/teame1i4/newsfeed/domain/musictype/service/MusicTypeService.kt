package com.teame1i4.newsfeed.domain.musictype.service

import com.teame1i4.newsfeed.domain.musictype.dto.response.MusicTypeCountResponse
import com.teame1i4.newsfeed.domain.musictype.model.MusicType
import com.teame1i4.newsfeed.domain.musictype.model.toResponse
import com.teame1i4.newsfeed.domain.musictype.repository.MusicTypeRepository
import com.teame1i4.newsfeed.domain.post.model.PostStatus
import com.teame1i4.newsfeed.domain.post.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class MusicTypeService(
    private val musicTypeRepository: MusicTypeRepository,
    private val postRepository: PostRepository,
) {

    fun getMusicTypeCount(): List<MusicTypeCountResponse> =
        musicTypeRepository.findAllByOrderByCountPostDesc().map { it.toResponse() }

    fun updateMusicTypeAllCount(): List<MusicTypeCountResponse> {
        val musicTypes: List<MusicType> = musicTypeRepository.findAll()
        musicTypes.forEach {
            it.countPost = postRepository.findAllByMusicTypeAndPostStatus(it.type, PostStatus.PUBLIC).count().toLong()
        }
        musicTypeRepository.saveAll(musicTypes)
        return musicTypes.map { it.toResponse() }
    }

}
