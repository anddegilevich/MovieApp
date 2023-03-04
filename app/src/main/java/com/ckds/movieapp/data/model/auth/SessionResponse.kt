package com.ckds.movieapp.data.model.auth

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session")
data class SessionResponse(
    val success: Boolean,
    @PrimaryKey
    val session_id: String
)