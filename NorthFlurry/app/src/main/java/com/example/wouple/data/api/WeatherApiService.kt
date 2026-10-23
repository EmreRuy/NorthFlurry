package com.example.wouple.data.api

import com.example.wouple.model.api.AirQuality
import com.example.wouple.model.api.OpenMeteoGeocodingResponse
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherApiService {

    @GET("v1/forecast")
    suspend fun getTemperature(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("hourly") hourly: String = "precipitation,temperature_2m,relativehumidity_2m,dewpoint_2m,apparent_temperature,precipitation_probability,weathercode,visibility,windspeed_10m,uv_index,is_day,surface_pressure,cloud_cover,wind_direction_10m",
        @Query("models") models: String = "best_match",
        @Query("daily") daily: String = "showers_sum,temperature_2m_max,temperature_2m_min,weathercode,sunrise,sunset,uv_index_max,precipitation_sum,rain_sum,sunshine_duration",
        @Query("timezone") timezone: String = "auto",
        @Query("forecast_days") forecastDays: Int = 9,
        @Query("current_weather") currentWeather: String = "true",
        @Query("temperature_unit") temperatureUnit: String,
        @Query("precipitation_unit") precipitationUnit: String,
        @Query("wind_speed_unit") windSpeedUnit: String,
    ): TemperatureResponse

    @GET
    suspend fun getAirQuality(
        @Url url: String = "https://air-quality-api.open-meteo.com/v1/air-quality",
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("current") current: String = "european_aqi"
    ): AirQuality

    @GET
    suspend fun searchOpenMeteoLocations(
        @Url url: String = "https://geocoding-api.open-meteo.com/v1/search",
        @Query("name") name: String,
        @Query("count") count: Int = 10,
        @Query("language") language: String = "en",
        @Query("format") format: String = "json"
    ): OpenMeteoGeocodingResponse

    @GET
    suspend fun getSearchedLocations(
        @Url url: String = "https://geocode.maps.co/search",
        @Query("q") address: String,
        @Query("api_key") apiKey: String = "65951b3415cd3773735054mvw77735a"
    ): List<SearchedLocation>
}
