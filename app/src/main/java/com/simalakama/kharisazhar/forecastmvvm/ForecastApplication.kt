package com.simalakama.kharisazhar.forecastmvvm

import android.app.Application
import com.simalakama.kharisazhar.forecastmvvm.data.ApixuWeatherApiService
import com.simalakama.kharisazhar.forecastmvvm.data.db.ForecastDb
import com.simalakama.kharisazhar.forecastmvvm.data.network.ConnectivityInterceptor
import com.simalakama.kharisazhar.forecastmvvm.data.network.ConnectivityInterceptorImpl
import com.simalakama.kharisazhar.forecastmvvm.data.network.WeatherNetworkDataSource
import com.simalakama.kharisazhar.forecastmvvm.data.network.WeatherNetworkDataSourceImpl
import com.simalakama.kharisazhar.forecastmvvm.data.repository.ForecastRepository
import com.simalakama.kharisazhar.forecastmvvm.data.repository.ForecastRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDb(instance()) }
        bind() from singleton { instance<ForecastDb>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }

    }
}