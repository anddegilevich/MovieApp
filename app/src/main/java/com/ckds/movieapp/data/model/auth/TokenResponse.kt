package com.ckds.movieapp.data.model.auth

data class TokenResponse(
    val expires_at: String,
    val request_token: String,
    val success: Boolean,
)