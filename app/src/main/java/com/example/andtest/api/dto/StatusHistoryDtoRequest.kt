package com.example.andtest.api.dto

data class StatusHistoryDtoRequest(
    val status: Status,
    val comment: String,
    val time: String

)