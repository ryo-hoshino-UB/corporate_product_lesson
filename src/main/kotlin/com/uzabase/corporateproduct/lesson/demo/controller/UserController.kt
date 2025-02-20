package com.uzabase.corporateproduct.lesson.demo.controller

import com.uzabase.corporateproduct.lesson.demo.validator.StartBeforeEnd
import com.uzabase.corporateproduct.lesson.demo.validator.ValidSearchRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

data class User(val id: UUID, val name: String)

@StartBeforeEnd
data class TimeRangeRequest(
    val start: Long,
    val end: Long,
)


data class Email(val email: String)

@ValidSearchRequest
data class SearchRequest(
    @field:Size(max = 30, message = "wordは30文字以下で指定してください")
    val word: String?,
    @field:Min(0, message = "0以上の値を指定してください")
    val offset: Int? = 0,
    @field:Min(1, message = "1以上の値を指定してください")
    val limit: Int? = 20,
    @field:Email(message = "emailの形式で指定してください")
    @field:Size(max = 254, message = "emailは254文字以下で指定してください")
    val email: String?,
)

data class SearchResult(val users: List<User>)

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

    @RequestMapping("/users/search", method = [RequestMethod.POST])
    fun searchUsers(@Valid @RequestBody body: SearchRequest): ResponseEntity<SearchResult> {
        val logger = LoggerFactory.getLogger(javaClass)
        logger.debug("\nword: {} \noffset: {}\nlimit: {}\nemail: {}", body.word, body.offset, body.limit, body.email)
        return ResponseEntity.ok(SearchResult(Collections.emptyList()))
    }
}
