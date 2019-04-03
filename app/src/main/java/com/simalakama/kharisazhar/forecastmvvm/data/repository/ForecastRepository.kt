package com.simalakama.kharisazhar.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.simalakama.kharisazhar.forecastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
}