package com.uzabase.corporateproduct.lesson.demo.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

data class User(val id: UUID, val name: String)

@RestController
class UserController {
    @RequestMapping("/users/{userId}/{name}", method = [RequestMethod.GET])
    fun getUserByID(
        @PathVariable("userId") userId: String,
        @PathVariable("name") name: String,
        @RequestParam("option") option: String,
        // defaultValueを渡すと、requiredは暗黙的にfalseになる
        //  https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html#defaultValue()
        @RequestParam("greeting", defaultValue = "Hello") greeting: String,
    ): ResponseEntity<User> {
        val logger = LoggerFactory.getLogger(javaClass)
        logger.debug("\nuserId: {} \nname: {}\noption: {}\ngreeting: {}", userId, name, option, greeting)
        return ResponseEntity.accepted().build()
    }
}