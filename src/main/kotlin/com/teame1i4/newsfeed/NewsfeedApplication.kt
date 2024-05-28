package com.teame1i4.newsfeed

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class NewsfeedApplication

fun main(args: Array<String>) {
    runApplication<NewsfeedApplication>(*args)
}
