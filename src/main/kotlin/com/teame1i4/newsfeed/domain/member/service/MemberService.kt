package com.teame1i4.newsfeed.domain.member.service

import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.member.dto.request.CreateMemberRequest
import com.teame1i4.newsfeed.domain.member.dto.response.MemberResponse
import com.teame1i4.newsfeed.domain.member.model.Member
import com.teame1i4.newsfeed.domain.member.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    fun createMember(request: CreateMemberRequest): MemberResponse = memberRepository.save(
            Member(request.username, request.password)
        ).toResponse()

    fun getMember(memberId: Long): MemberResponse = (memberRepository.findByIdOrNull(memberId)
        ?: throw ModelNotFoundException("Member", memberId))
        .toResponse()
}