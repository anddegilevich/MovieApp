package com.ckds.movieapp.data.db.entities

import androidx.room.Entity

@Entity(tableName = "stored_series", primaryKeys = ["id","category"])
data class StoredSeries(
    val id: Int,
    val category: String
)