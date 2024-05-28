package com.teame1i4.newsfeed.domain.user.controller

import com.teame1i4.newsfeed.domain.user.dto.request.CreateUserRequest
import com.teame1i4.newsfeed.domain.user.dto.response.UserResponse
import com.teame1i4.newsfeed.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/users")
@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<UserResponse> =
        ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.createUser(request))

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): ResponseEntity<UserResponse> =
        ResponseEntity.status(HttpStatus.OK)
            .body(userService.getUser(userId))
}