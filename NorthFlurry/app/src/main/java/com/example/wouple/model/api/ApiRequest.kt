package com.example.wouple.model.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {
    //temperature_unit&windspeed_unit&precipitation_unit
    @GET("https://api.open-meteo.com/v1/forecast/")
    fun getTemperature(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("hourly") hourly: String = "precipitation,temperature_2m,relativehumidity_2m,dewpoint_2m,apparent_temperature,precipitation_probability,weathercode,visibility,windspeed_10m,uv_index,is_day,surface_pressure,cloud_cover,wind_direction_10m",
        @Query("models") models: String = "best_match",
        @Query("daily") daily: String = "showers_sum,temperature_2m_max,temperature_2m_min,weathercode,sunrise,sunset,uv_index_max,precipitation_sum,rain_sum,sunshine_duration",
        @Query("timezone") timezone: String = "auto",
        @Query("forecast_days") forecast_days: Int = 9,
        @Query("current_weather") current_weather: String = "true",
        @Query("temperature_unit") temperature_unit: String,
        @Query("precipitation_unit") precipitation_unit: String,
        @Query("wind_speed_unit") wind_speed_unit: String,
    ): Call<TemperatureResponse>

    // https://air-quality-api.open-meteo.com/v1/air-quality?latitude=52.52&longitude=13.41&current=european_aqi
    @GET("https://air-quality-api.open-meteo.com/v1/air-quality")
    fun getAirQuality(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("current") current: String = "european_aqi"
    ): Call<AirQuality>
    
    @GET("https://geocode.maps.co/search")
    fun getSearchedLocations(
        @Query("q") address: String,
        @Query("api_key") apiKey: String = "65951b3415cd3773735054mvw77735a"
    ): Call<List<SearchedLocation>>
}