package com.teame1i4.newsfeed.domain.musictype.service

import com.teame1i4.newsfeed.domain.musictype.dto.response.MusicTypeCountResponse
import com.teame1i4.newsfeed.domain.musictype.model.MusicType
import com.teame1i4.newsfeed.domain.musictype.model.toResponse
import com.teame1i4.newsfeed.domain.musictype.repository.MusicTypeRepository
import org.springframework.stereotype.Service

@Service
class MusicTypeService(
    private val musicTypeRepository: MusicTypeRepository
) {

    fun getMusicTypeCount(): List<MusicTypeCountResponse> =
        musicTypeRepository.findAllByOrderByCountPostDesc().map { it.toResponse() }

}
