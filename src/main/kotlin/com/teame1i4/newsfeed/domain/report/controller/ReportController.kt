package com.teame1i4.newsfeed.domain.report.controller

import com.teame1i4.newsfeed.domain.report.dto.CreateReportRequest
import com.teame1i4.newsfeed.domain.report.service.ReportService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts/{postId}/reports")
class ReportController(
    private val reportService: ReportService
) {
//    @GetMapping
//    fun getReporterList(
//        @PathVariable postId: Long
//    ): ResponseEntity<List<ReportResponse>> {
//        val reporterList = reportService.getReporterList(postId)
//        return ResponseEntity.ok(reporterList)
//    }
    @PostMapping
    fun createReport(
        @PathVariable postId: Long,
        @RequestBody request: CreateReportRequest
    ): ResponseEntity<Unit> {
        reportService.createReport(postId, request)
        return ResponseEntity.ok(Unit)
    }
}