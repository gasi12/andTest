package com.example.andtest.api.dto

enum class Status(val visibleName: String) {
    PENDING("pending"),
    IN_PROCESS("in process"),
    ON_HOLD("on hold"),
    FINISHED("finished")
}