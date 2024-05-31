package com.teame1i4.newsfeed.domain.report.service

import com.teame1i4.newsfeed.domain.exception.AlreadyReportedException
import com.teame1i4.newsfeed.domain.exception.ModelNotFoundException
import com.teame1i4.newsfeed.domain.member.adapter.MemberDetails
import com.teame1i4.newsfeed.domain.post.repository.PostRepository
import com.teame1i4.newsfeed.domain.report.model.Report
import com.teame1i4.newsfeed.domain.report.repository.ReportRepository
import com.teame1i4.newsfeed.domain.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service


@Service
class ReportService(
    private val reportRepository: ReportRepository,
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository
) {

    @PreAuthorize("hasRole('USER')")
    @Transactional
    fun createReport(postId: Long, member: MemberDetails) {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val user = memberRepository.findByIdOrNull(member.memberId) ?: throw ModelNotFoundException("Member", member.memberId)

        if(reportRepository.existsByMemberIdAndPostId(user.id!!, postId)) throw AlreadyReportedException(
            postId, user.id!!
        )

        post.addReport()

        reportRepository.save(Report(user,post))
    }
}