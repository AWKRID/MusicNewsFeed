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
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        jwtUtility.resolveToken(request)?.let { token ->
            jwtUtility.validateToken(token).onFailure { throw it }

            jwtUtility.parseClaims(token).let { it ->
                val id = it["id"]
                val name = it["name"]

                val memberDetails = memberDetailsService.loadUserByUsername("${id}:${name}")
                SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.authorities)
            }
        }

        filterChain.doFilter(request, response)
    }
}