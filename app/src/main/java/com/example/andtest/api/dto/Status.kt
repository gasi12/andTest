package com.example.andtest.api.dto

enum class Status(val visibleName: String) {
    PENDING("Pending"),
    IN_PROCESS("In process"),
    ON_HOLD("On hold"),
    FINISHED("Finished")
}