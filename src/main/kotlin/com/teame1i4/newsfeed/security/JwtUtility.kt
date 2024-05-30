package com.teame1i4.newsfeed.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*

@Component
class JwtUtility (
    @Value("\${jwt.issuer}")
    private val issuer: String,

    @Value("\${jwt.secret}")
    private val secret: String,

    @Value("\${jwt.expirationHours.access}")
    private val accessTokenExpirationHours: Int
) {

    private val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))


    fun validateToken(token: String): Result<Jws<Claims>> =
        kotlin.runCatching {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
        }

    private fun generateToken(id: Long, role: String, expirationPeriod: Duration): String {
        var claims = Jwts.claims()
            .add(mapOf("id" to id, "role" to role))
            .build()

        val now = Instant.now()

        return Jwts.builder()
            .issuer(issuer).claims(claims)
            .issuedAt(Date.from(now)).expiration((Date.from(now.plus(expirationPeriod))))
            .signWith(key)
            .compact()
    }

    fun generateAccessToken(id: Long, role: String): String =
        generateToken(id, role, Duration.ofHours(accessTokenExpirationHours.toLong()))

    fun parseClaims(token: String): Claims =
        Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token).payload

    fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader("Authorization").let { bearerToken ->
            if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) bearerToken.substring(7)
            else null
        }
}