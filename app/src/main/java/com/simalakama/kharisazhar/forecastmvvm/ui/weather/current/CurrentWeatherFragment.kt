package com.simalakama.kharisazhar.forecastmvvm.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.simalakama.kharisazhar.forecastmvvm.R
import com.simalakama.kharisazhar.forecastmvvm.data.ApixuWeatherApiService
import com.simalakama.kharisazhar.forecastmvvm.data.network.ConnectivityInterceptor
import com.simalakama.kharisazhar.forecastmvvm.data.network.ConnectivityInterceptorImpl
import com.simalakama.kharisazhar.forecastmvvm.data.network.WeatherNetworkDataSource
import com.simalakama.kharisazhar.forecastmvvm.data.network.WeatherNetworkDataSourceImpl
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrentWeatherFragment : Fragment() {

    companion object {
        fun newInstance() = CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java)
        // TODO: Use the ViewModel

        val apiServices = ApixuWeatherApiService(ConnectivityInterceptorImpl(this.context!!))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiServices)

        weatherNetworkDataSource.downloadedCurrentWeather.observe(this, Observer {
            currentWeatherText.text = it.toString()
        })

        GlobalScope.launch(Dispatchers.Main){
            weatherNetworkDataSource.fetchCurrentWeather("jakarta")

        }
    }
}
