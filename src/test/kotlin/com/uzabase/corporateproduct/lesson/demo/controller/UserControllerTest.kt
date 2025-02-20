package com.uzabase.corporateproduct.lesson.demo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class UserControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper


    @Test
    @DisplayName("/users/searchのwordが31文字でエラー")
    fun word_must_be_30_characters_or_less() {
        val requestMap = mapOf("word" to "a".repeat(31))
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.word").value("wordは30文字以下で指定してください"))
    }

    @Test
    @DisplayName("/users/searchのwordが30文字でパス")
    fun word_is_30_characters_or_less() {
        val requestMap = mapOf("word" to "a".repeat(30))
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is2xxSuccessful)
    }

    @Test
    @DisplayName("/users/searchのwordが1文字でパス")
    fun word_is_1_character() {
        val requestMap = mapOf("word" to "a")
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is2xxSuccessful)
    }

    @Test
    @DisplayName("/users/searchのwordが空でエラー")
    fun word_must_not_be_blank() {
        val requestMap = mapOf("word" to "")
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is4xxClientError)
    }

    @Test
    @DisplayName("/users/searchのwordが空白文字でエラー")
    fun word_must_not_be_null() {
        val requestMap = mapOf("word" to " ")
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is4xxClientError)
    }

    @Test
    @DisplayName("/users/searchのemailが255文字でエラー")
    fun email_must_be_1024_characters_or_less() {
        val localPart = "a".repeat(60)
        val domainPart = ("b".repeat(59) + ".").repeat(3) + "c".repeat(10) + ".com"
        val email = "$localPart@$domainPart"
        val requestMap = mapOf("email" to email)
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.email").value("emailは254文字以下で指定してください"))
    }

    @Test
    @DisplayName("/users/searchのemailが254文字でパス")
    fun email_is_1024_characters_or_less() {
        val localPart = "a".repeat(60)
        val domainPart = ("b".repeat(59) + ".").repeat(3) + "c".repeat(9) + ".com"
        val email = "$localPart@$domainPart"
        val requestMap = mapOf("email" to email)
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is2xxSuccessful)
    }

    @Test
    @DisplayName("/users/searchのemailがメールアドレス形式でないときエラー")
    fun email_must_be_an_email_address() {
        val requestMap = mapOf("email" to "test@example@")
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.email").value("emailの形式で指定してください"))
    }

    @Test
    @DisplayName("/users/searchのemailが空でエラー")
    fun email_must_not_be_blank() {
        val requestMap = mapOf("email" to "")
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is4xxClientError)
    }


    @Test
    @DisplayName("/users/searchのwordとemailが両方指定されているときエラー")
    fun word_and_email_cannot_be_specified_at_the_same_time() {
        val requestMap = mapOf("word" to "test", "email" to "test@example.com")
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is4xxClientError)
    }

    @Test
    @DisplayName("/users/searchのwordが指定され、emailが指定されていないときパス")
    fun word_is_specified_and_email_is_not_specified() {
        val requestMap = mapOf("word" to "test")
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is2xxSuccessful)
    }

    @Test
    @DisplayName("/users/searchのemailが指定され、wordが指定されていないときパス")
    fun email_is_specified_and_word_is_not_specified() {
        val requestMap = mapOf("email" to "test@example.com")
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is2xxSuccessful)
    }

    @Test
    @DisplayName("/users/searchのwordとemailが両方指定されていないときエラー")
    fun word_and_email_must_be_specified() {
        val requestMap = mapOf<String, String>()
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is4xxClientError)
    }


    @Test
    @DisplayName("/users/searchのoffsetは0以上でなければエラー")
    fun offset_must_be_grater_than_0() {
        val requestMap = mapOf("offset" to "-1")
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.offset").value("0以上の値を指定してください"))
    }

    @Test
    @DisplayName("/users/searchのoffsetが0のときパス")
    fun offset_is_0() {
        val requestMap = mapOf("offset" to "0", "word" to "test")
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is2xxSuccessful)
    }

    @Test
    @DisplayName("/users/searchのlimitが0のときエラー")
    fun limit_must_be_greater_than_0() {
        val requestMap = mapOf("limit" to "0")
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.limit").value("1以上の値を指定してください"))
    }

    @Test
    @DisplayName("/users/searchのlimitが1のときパス")
    fun limit_is_1() {
        val requestMap = mapOf("limit" to "1", "word" to "test")
        val requestJson = objectMapper.writeValueAsString(requestMap)
        mockMvc.perform(post("/users/search").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().is2xxSuccessful)
    }
}
