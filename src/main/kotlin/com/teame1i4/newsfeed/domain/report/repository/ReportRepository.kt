package com.teame1i4.newsfeed.domain.report.repository

import com.teame1i4.newsfeed.domain.report.model.Report
import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository: JpaRepository<Report, Long> {
    fun existsByMemberIdAndPostId(memberId: Long, postId: Long): Boolean
}