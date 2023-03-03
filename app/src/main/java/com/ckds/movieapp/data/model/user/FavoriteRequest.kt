package com.ckds.movieapp.data.model.user

data class FavoriteRequest (
    val media_type: String,
    val media_id: Int,
    val favorite: Boolean
)