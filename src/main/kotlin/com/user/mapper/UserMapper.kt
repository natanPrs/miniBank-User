package com.user.mapper

import com.user.dtos.UserDto
import com.user.models.UserModel

fun UserDto.toUserEntity(userDto: UserDto): UserModel =
    UserModel(
        firstName = this.firstName,
        lastName = this.lastName,
        document = this.document,
        email = this.email
    )
