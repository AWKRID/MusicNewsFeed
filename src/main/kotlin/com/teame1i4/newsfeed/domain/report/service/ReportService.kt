package com.teame1i4.newsfeed.domain.report.service

import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.post.model.PostStatus
import com.teame1i4.newsfeed.domain.post.repository.PostRepository
import com.teame1i4.newsfeed.domain.report.dto.CreateReportRequest
import com.teame1i4.newsfeed.domain.report.model.Report
import com.teame1i4.newsfeed.domain.report.repository.ReportRepository
import com.teame1i4.newsfeed.domain.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class ReportService(
    private val reportRepository: ReportRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun createReport(postId: Long, request: CreateReportRequest) {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val user = userRepository.findByIdOrNull(request.userId) ?: throw ModelNotFoundException("User", request.userId)

        if(reportRepository.existsByUserIdAndPostId(request.userId, postId)) throw RuntimeException("이미 신고된 게시글입니다")

        post.reportCount += 1

        if (post.reportCount >= 5) post.postStatus = PostStatus.HIDDEN

        val report = Report(user, post)

        reportRepository.save(report)
    }
}