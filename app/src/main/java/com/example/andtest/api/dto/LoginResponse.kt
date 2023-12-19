package com.example.andtest.api.dto

class LoginResponse (
    val token :String,
    val refreshToken: String,
    val username: String,
    val firstName:String, //todo zmienic na camelcase
    val lastName: String //todo zmienic as well
) {
    override fun toString(): String {
        return "LoginResponse(token='$token', refreshToken='$refreshToken', username='$username', firstname='$firstName', lastname='$lastName')"
    }
}

