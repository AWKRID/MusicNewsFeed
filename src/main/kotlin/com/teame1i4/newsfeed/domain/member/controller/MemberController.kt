package com.teame1i4.newsfeed.domain.member.controller

import com.teame1i4.newsfeed.domain.member.dto.request.CreateMemberRequest
import com.teame1i4.newsfeed.domain.member.dto.response.MemberResponse
import com.teame1i4.newsfeed.domain.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/members")
@RestController
class MemberController(
    private val memberService: MemberService
) {
    @GetMapping("/{memberId}")
    fun getMember(@PathVariable memberId: Long): ResponseEntity<MemberResponse> =
        ResponseEntity.status(HttpStatus.OK)
            .body(memberService.getMember(memberId))
}