package com.example.andtest.api.dto

class LoginResponse (
    val token :String,
    val refreshToken: String,
    val username: String,
    val firstname:String, //todo zmienic na camelcase
    val lastname: String //todo zmienic as well
) {
    override fun toString(): String {
        return "LoginResponse(token='$token', refreshToken='$refreshToken', username='$username', firstname='$firstname', lastname='$lastname')"
    }
}

