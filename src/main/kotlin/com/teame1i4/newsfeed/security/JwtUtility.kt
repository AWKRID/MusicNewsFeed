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
class JwtUtility(
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

    private fun generateToken(id: Long, name: String, expirationPeriod: Duration): String {
        val claims = Jwts.claims()
            .add(mapOf("id" to id, "name" to name))
            .build()
        val now = Instant.now()

        return Jwts.builder()
            .issuer(issuer).claims(claims)
            .issuedAt(Date.from(now)).expiration((Date.from(now.plus(expirationPeriod))))
            .signWith(key)
            .compact()
    }

    fun generateAccessToken(id: Long, name: String): String =
        generateToken(id, name, Duration.ofHours(accessTokenExpirationHours.toLong()))

    fun parseClaims(token: String): Claims =
        validateToken(token).getOrNull()!!.payload

    fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader("Authorization").let { bearerToken ->
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) bearerToken.substring(7)
            else null
        }
}