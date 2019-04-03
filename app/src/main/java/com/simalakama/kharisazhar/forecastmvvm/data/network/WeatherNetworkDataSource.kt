package com.simalakama.kharisazhar.forecastmvvm.data.network

import androidx.lifecycle.LiveData
import com.simalakama.kharisazhar.forecastmvvm.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String
    )
}