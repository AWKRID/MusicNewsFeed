package com.teame1i4.newsfeed.domain.exception

data class TypeNotFoundException(val type: String) : RuntimeException("Type not found with given type: $type")
