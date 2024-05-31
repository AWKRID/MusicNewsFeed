package com.teame1i4.newsfeed.security.filter

import com.teame1i4.newsfeed.domain.member.adapter.MemberDetailsService
import com.teame1i4.newsfeed.security.JwtUtility
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val memberDetailsService: MemberDetailsService,
    private val jwtUtility: JwtUtility
): OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val token = authorizationHeader.substring(7)
            jwtUtility.validateToken(token).onFailure { throw it }

            val memberId = jwtUtility.parseClaims(token)["id"]
            val memberDetails = memberDetailsService.loadUserByUsername(memberId.toString())

            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.authorities)
        }

        filterChain.doFilter(request, response)
    }
}