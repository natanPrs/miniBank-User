package com.user.dtos

import jakarta.validation.constraints.Email

data class UserDto(
    val firstName: String,
    val lastName: String,
    val document: String,
    @Email
    val email: String
)
