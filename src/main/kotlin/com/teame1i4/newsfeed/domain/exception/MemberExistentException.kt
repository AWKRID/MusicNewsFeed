package com.teame1i4.newsfeed.domain.exception

class MemberExistentException(username: String?) : RuntimeException("Member already existent with username $username")