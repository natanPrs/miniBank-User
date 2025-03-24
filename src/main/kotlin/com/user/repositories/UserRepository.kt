package com.user.repositories

import com.user.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<UserModel, UUID> {

    fun findUserByDocument(document: String): UserModel?
}