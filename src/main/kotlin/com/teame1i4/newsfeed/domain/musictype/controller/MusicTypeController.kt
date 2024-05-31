package com.teame1i4.newsfeed.domain.musictype.controller

import com.teame1i4.newsfeed.domain.musictype.dto.MusicTypeCountResponse
import com.teame1i4.newsfeed.domain.musictype.service.MusicTypeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/music-types")
@RestController
class MusicTypeController(private val musicTypeService: MusicTypeService) {

    @GetMapping("")
    fun getMusicTypeCount(): ResponseEntity<List<MusicTypeCountResponse>> {
        return ResponseEntity.ok(musicTypeService.getMusicTypeCount())
    }
}