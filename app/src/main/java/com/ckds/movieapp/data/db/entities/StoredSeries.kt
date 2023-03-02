package com.ckds.movieapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stored_series")
data class StoredSeries(
    @PrimaryKey
    val id: Int,
    val category: String
)