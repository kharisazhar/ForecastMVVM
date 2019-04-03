package com.simalakama.kharisazhar.forecastmvvm.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.simalakama.kharisazhar.forecastmvvm.data.network.ConnectivityInterceptor
import com.simalakama.kharisazhar.forecastmvvm.data.network.ConnectivityInterceptorImpl
import com.simalakama.kharisazhar.forecastmvvm.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "46c8f25bb8dd4768ada55432190204"

//https://api.apixu.com/v1/current.json?key=46c8f25bb8dd4768ada55432190204&q=jakarta

interface ApixuWeatherApiService {


    @GET("current.json")
    fun getCurrentWeather(
        @Query("q") location: String
    ): Deferred<CurrentWeatherResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApixuWeatherApiService{
            val requestInterceptor = Interceptor{chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.apixu.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApixuWeatherApiService::class.java)

        }
    }
}