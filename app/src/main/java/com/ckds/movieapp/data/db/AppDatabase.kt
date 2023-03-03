package com.ckds.movieapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ckds.movieapp.data.db.converters.Converters
import com.ckds.movieapp.data.db.entities.StoredMovie
import com.ckds.movieapp.data.db.entities.StoredSeries
import com.ckds.movieapp.data.model.movie.Movie
import com.ckds.movieapp.data.model.series.Series

@Database(entities = [
    StoredMovie::class,
    Movie::class,
    StoredSeries::class,
    Series::class],
    version = 1,
    exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getDao(): AppDao


}