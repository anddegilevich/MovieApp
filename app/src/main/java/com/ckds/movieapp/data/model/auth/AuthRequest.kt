package com.ckds.movieapp.data.model.auth

data class AuthRequest(
    val username: String,
    val password: String,
    val request_token: String,
)