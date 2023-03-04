package com.ckds.movieapp.data.model.user

data class UserDetailsResponse (
    val id: Int,
    val name: String,
    val username: String,
    val include_adult: Boolean,
    val iso_639_1: String,
    val iso_3166_1: String,
    val avatar: Avatar
)