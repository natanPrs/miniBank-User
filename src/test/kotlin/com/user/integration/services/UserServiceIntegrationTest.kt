package com.user.integration.services

import com.user.dtos.UserDto
import com.user.services.UserService
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test

@SpringBootTest
@ActiveProfiles("test")
class UserServiceIntegrationTest() {

    @Autowired
    lateinit var userService: UserService


    @Test
    fun `should create user successfully`() {
        val newUser = UserDto(
            firstName = "Geralt",
            lastName = "De Rivia",
            document = "99999999909",
            email = "geralt@email.com"
        )

        userService.createUser(newUser)

        val result = userService.findUserByDocument(newUser.document)

        assertThat(result).isNotNull
        assertThat(result.firstName).isEqualTo(newUser.firstName)
    }
}