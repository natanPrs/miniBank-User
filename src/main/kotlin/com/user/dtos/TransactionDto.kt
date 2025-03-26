package com.user.dtos

import java.math.BigDecimal

data class TransactionDto(
    val senderDocument: String,
    val receiverDocument: String,
    val amount: BigDecimal,
)
