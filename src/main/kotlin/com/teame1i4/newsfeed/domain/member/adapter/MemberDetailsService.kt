package com.teame1i4.newsfeed.domain.member.adapter

import com.teame1i4.newsfeed.domain.member.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MemberDetailsService(
    private val memberRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(tag: String): UserDetails {
        val (id, username) = tag.split(":")
        val member = memberRepository.findByIdAndUsername(id.toLong(), username)
            ?: throw UsernameNotFoundException("Member not found with id $id")
        return MemberDetails(member.toUserDetailsParameter())
    }
}