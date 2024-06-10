package com.example.demo

data class PaymentDTO(
    val amount: Long,
    val currency: String,
    val source: String,
    val description: String,
    val userId: Long
)