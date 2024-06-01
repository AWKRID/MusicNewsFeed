package com.teame1i4.newsfeed.auth.controller

import com.teame1i4.newsfeed.auth.dto.request.SignInRequest
import com.teame1i4.newsfeed.auth.dto.request.SignUpRequest
import com.teame1i4.newsfeed.auth.dto.response.SignInResponse
import com.teame1i4.newsfeed.auth.dto.response.SignUpResponse
import com.teame1i4.newsfeed.auth.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<SignUpResponse> =
        ResponseEntity.status(HttpStatus.CREATED)
            .body(authService.signUp(request))

    @PostMapping("/signin")
    fun signIn(@RequestBody request: SignInRequest): ResponseEntity<SignInResponse> =
        ResponseEntity.status(HttpStatus.OK)
            .body(authService.signIn(request))
}