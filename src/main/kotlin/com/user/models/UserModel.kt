package com.user.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "tb_users")
data class UserModel(
    @Id
    val id: UUID = UUID.randomUUID(),

    val firstName: String,

    val lastName: String,

    @Column(unique = true)
    val document: String,

    @Column(unique = true)
    val email: String,

    val balance: BigDecimal = BigDecimal.valueOf(0)

)