package com.example.andtest.api.dto

class Tokens (
    val token :String,
    val refreshToken: String
) {
    override fun toString(): String {
        return "Tokens(token='$token', refreshToken='$refreshToken')"
    }
}

