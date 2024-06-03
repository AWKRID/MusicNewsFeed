package com.teame1i4.newsfeed.domain.member.service

import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.member.dto.request.CreateMemberRequest
import com.teame1i4.newsfeed.domain.member.dto.response.MemberResponse
import com.teame1i4.newsfeed.domain.member.model.Member
import com.teame1i4.newsfeed.domain.member.model.toResponse
import com.teame1i4.newsfeed.domain.member.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    fun getMember(
        id: Long
    ): MemberResponse =
        (memberRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("Member", id)).toResponse()

}