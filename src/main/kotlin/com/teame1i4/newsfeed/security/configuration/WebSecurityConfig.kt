package com.teame1i4.newsfeed.security.configuration

import com.teame1i4.newsfeed.domain.member.adapter.MemberDetailsService
import com.teame1i4.newsfeed.security.JwtUtility
import com.teame1i4.newsfeed.security.filter.ExceptionHandlerFilter
import com.teame1i4.newsfeed.security.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class WebSecurityConfig (
    private val entryPoint: AuthenticationEntryPoint,
    private val accessDeniedHandler: AccessDeniedHandler,
    private val memberDetailsService: MemberDetailsService,
    private val jwtUtility: JwtUtility
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { it
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**").permitAll()
                .anyRequest().permitAll()
            }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }
            .cors(Customizer.withDefaults())
            .addFilterBefore(JwtAuthenticationFilter(memberDetailsService, jwtUtility), UsernamePasswordAuthenticationFilter::class.java)
//            .addFilterBefore(ExceptionHandlerFilter(), JwtAuthenticationFilter::class.java)
            .exceptionHandling { it
                .authenticationEntryPoint(entryPoint)
                .accessDeniedHandler(accessDeniedHandler)
            }
            .build()
}