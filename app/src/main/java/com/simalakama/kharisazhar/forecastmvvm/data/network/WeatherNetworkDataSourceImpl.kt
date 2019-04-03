package com.simalakama.kharisazhar.forecastmvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.simalakama.kharisazhar.forecastmvvm.data.ApixuWeatherApiService
import com.simalakama.kharisazhar.forecastmvvm.data.network.response.CurrentWeatherResponse
import com.simalakama.kharisazhar.forecastmvvm.internal.NoConnectivityException
import java.io.IOException

class WeatherNetworkDataSourceImpl(
    private val apixuWeatherApiService: ApixuWeatherApiService
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String) {
        try {
            val fetchedCurrentWeather = apixuWeatherApiService
                .getCurrentWeather(location)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity","No Internet Connection ",e)
        }
    }
}