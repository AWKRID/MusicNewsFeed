package com.teame1i4.newsfeed.auth.service

import com.teame1i4.newsfeed.auth.dto.request.SignInRequest
import com.teame1i4.newsfeed.auth.dto.request.SignUpRequest
import com.teame1i4.newsfeed.auth.dto.response.SignInResponse
import com.teame1i4.newsfeed.auth.dto.response.SignUpResponse
import com.teame1i4.newsfeed.domain.exception.MemberExistentException
import com.teame1i4.newsfeed.domain.member.model.Member
import com.teame1i4.newsfeed.domain.member.repository.MemberRepository
import com.teame1i4.newsfeed.security.JwtUtility
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val encoder: PasswordEncoder,
    private val jwtUtility: JwtUtility
) {

    fun signUp(request: SignUpRequest): SignUpResponse {
        if(memberRepository.existsByUsername(request.username)) throw MemberExistentException(request.username)
        return memberRepository.save(Member(request, encoder)).toSignUpResponse()
    }

    fun signIn(request: SignInRequest): SignInResponse {
        val member = memberRepository.findByUsername(request.username) ?: throw UsernameNotFoundException("Member not found")
        if(!encoder.matches(request.password, member.password)) throw BadCredentialsException("Incorrect password")
        val accessToken = jwtUtility.generateAccessToken(member.id!!, member.username)

        return member.toSignInResponse(accessToken)
    }
}