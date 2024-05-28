package com.teame1i4.newsfeed.domain.user.repository

import com.teame1i4.newsfeed.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
}