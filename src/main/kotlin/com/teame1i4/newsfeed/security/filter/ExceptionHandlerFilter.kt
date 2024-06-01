package com.teame1i4.newsfeed.security.filter

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class ExceptionHandlerFilter: OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: SignatureException) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Wrong-signed JWT provided")
        } catch (e: MalformedJwtException) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Malformed JWT provided")
        } catch (e: ExpiredJwtException) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Expired JWT provided")
        } catch (e: UsernameNotFoundException) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.message ?: "User not found")
        }

    }
}