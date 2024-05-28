package com.teame1i4.newsfeed.domain.user.service

import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.user.dto.request.CreateUserRequest
import com.teame1i4.newsfeed.domain.user.dto.response.UserResponse
import com.teame1i4.newsfeed.domain.user.model.User
import com.teame1i4.newsfeed.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun createUser(request: CreateUserRequest): UserResponse = userRepository.save(
            User(request.username, request.password)
        ).toResponse()

    fun getUser(userId: Long): UserResponse = (userRepository.findByIdOrNull(userId)
        ?: throw ModelNotFoundException("User", userId))
        .toResponse()
}