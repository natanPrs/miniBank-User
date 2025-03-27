package com.user.dtos

import java.math.BigDecimal

data class ResponseTransactionDto(
    val senderName: String,
    val senderDocument: String,
    val receiverName: String,
    val receiverDocument: String,
    val amount: BigDecimal
)
