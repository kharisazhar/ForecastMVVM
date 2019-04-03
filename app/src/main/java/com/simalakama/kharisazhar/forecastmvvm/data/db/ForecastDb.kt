package com.simalakama.kharisazhar.forecastmvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.simalakama.kharisazhar.forecastmvvm.data.db.entity.CurrentWeatherEntry


@Database(
    entities = [CurrentWeatherEntry::class],
    version = 1
)
abstract class ForecastDb : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: ForecastDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also  { instance = it }
        }


        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ForecastDb::class.java, "forecast.db")
                .build()
    }
}