package com.user.dtos

import java.util.UUID

data class EmailDto(
    val userId: UUID,
    val userName: String,
    val userEmail: String
)
