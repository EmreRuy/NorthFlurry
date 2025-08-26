package com.example.wouple.manager

import android.content.Context
import android.widget.Toast
import com.example.wouple.model.api.AirQuality
import com.example.wouple.model.api.ApiRequest
import com.example.wouple.model.api.PrecipitationUnit
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.model.api.TemperatureUnit
import com.example.wouple.model.api.WindUnit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherManager {

    private const val OPEN_MET_BASE_URL = "https://api.open-meteo.com"
    private const val GEOCODE_BASE_URL = "https://geocode.maps.co"
    private const val AIR_QUALITY_BASE_URL = "https://air-quality-api.open-meteo.com"
    fun getSearchedLocations(
        context: Context,
        address: String,
        onSuccessCall: (List<SearchedLocation>) -> Unit,
    ) {
        val api = getApiBuilder(GEOCODE_BASE_URL)
        api.getSearchedLocations(address).enqueue(object : Callback<List<SearchedLocation>> {
            override fun onFailure(call: Call<List<SearchedLocation>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<SearchedLocation>>,
                response: Response<List<SearchedLocation>>
            ) {
                response.body()?.let { onSuccessCall(it) }
            }
        })
    }

    fun getAirQuality(
        context: Context,
        longitude: String,
        latitude: String,
        onSuccessCall: (AirQuality) -> Unit
    ) {
        val api = getApiBuilder(AIR_QUALITY_BASE_URL)
        api.getAirQuality(longitude = longitude, latitude = latitude)
            .enqueue(object : Callback<AirQuality> {
                override fun onFailure(call: Call<AirQuality>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<AirQuality>,
                    response: Response<AirQuality>
                ) {
                    response.body()?.let { onSuccessCall(it) }
                }
            })
    }

    fun getCurrentWeather(
        context: Context,
        location: SearchedLocation?,
        onSuccessCall: (TemperatureResponse?) -> Unit,
        temperaUnit: TemperatureUnit,
        windUnit: WindUnit,
        precipitationUnit: PrecipitationUnit,
    ) {
        if (location == null) {
            onSuccessCall(null)
            return
        }
        fetchDataFromBackend(
            context,
            location,
            onSuccessCall,
            temperaUnit,
            windUnit,
            precipitationUnit
        )

    }

    private fun fetchDataFromBackend(
        context: Context,
        location: SearchedLocation,
        onSuccessCall: (TemperatureResponse) -> Unit,
        temperaUnit: TemperatureUnit,
        windUnit: WindUnit,
        precipitationUnit: PrecipitationUnit,
    ) {
        val api = getApiBuilder(OPEN_MET_BASE_URL)

        api.getTemperature(
            location.lat,
            location.lon,
            wind_speed_unit = windUnit.name.lowercase(),
            temperature_unit = temperaUnit.name.lowercase(),
            precipitation_unit = precipitationUnit.name.lowercase(),
        ).enqueue(object : Callback<TemperatureResponse> {
            override fun onFailure(call: Call<TemperatureResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<TemperatureResponse>,
                response: Response<TemperatureResponse>
            ) {
                response.body()?.let { onSuccessCall(it) }
                println("Temperature Response on the work$response")
            }
        })
    }

    private fun getApiBuilder(baseUrl: String) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

}