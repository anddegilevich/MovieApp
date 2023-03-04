package com.ckds.movieapp.data.db.entities

import androidx.room.Entity

@Entity(tableName = "stored_movies", primaryKeys = ["id","category"])
data class StoredMovie(
    val id: Int,
    val category: String
)