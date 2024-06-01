package com.teame1i4.newsfeed.domain.report.controller

import com.teame1i4.newsfeed.domain.member.adapter.MemberDetails
import com.teame1i4.newsfeed.domain.report.service.ReportService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts/{postId}/reports")
class ReportController(
    private val reportService: ReportService
) {
    @PostMapping
    fun createReport(
        @AuthenticationPrincipal member: MemberDetails?,
        @PathVariable postId: Long
    ): ResponseEntity<Unit> {
        reportService.createReport(postId, member)
        return ResponseEntity.ok(Unit)
    }
}