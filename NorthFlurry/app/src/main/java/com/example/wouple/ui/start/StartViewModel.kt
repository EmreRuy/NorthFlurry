package com.example.wouple.ui.start

import android.app.Application
import android.location.Geocoder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wouple.data.preferences.PreferenceManager
import com.example.wouple.model.api.SearchedLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class StartViewModel(application: Application) : AndroidViewModel(application) {

    private val preferenceManager = PreferenceManager(application.applicationContext)
    private val defaultLocation = SearchedLocation("40.7128", "-74.0060", "New York")

    private val _locationStatus = MutableStateFlow("Detecting location...")
    val locationStatus: StateFlow<String> = _locationStatus.asStateFlow()

    private val _isLocationReady = MutableStateFlow(false)
    val isLocationReady: StateFlow<Boolean> = _isLocationReady.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun updateLocation(lat: Double, lon: Double) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val geocoder = Geocoder(getApplication(), Locale.getDefault())
            val locationName = try {
                val addresses = geocoder.getFromLocation(lat, lon, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses.first()
                    address.locality
                        ?: address.subAdminArea
                        ?: address.adminArea
                        ?: "Current Location"
                } else {
                    "Current Location"
                }
            } catch (_: Exception) {
                "Current Location"
            }

            val searchedLoc = SearchedLocation(lat.toString(), lon.toString(), locationName)
            preferenceManager.setSearchedLocation(searchedLoc)

            _locationStatus.value = "Location Detected: $locationName"
            _isLocationReady.value = true
            _isLoading.value = false
        }
    }

    fun fallbackLocation() {
        _locationStatus.value = "Falling back to: ${defaultLocation.display_name}"
        _isLocationReady.value = true
        _isLoading.value = false
        preferenceManager.setSearchedLocation(defaultLocation)
    }

    fun saveAndFinish(selectedLocation: SearchedLocation) {
        preferenceManager.setSearchedLocation(selectedLocation)
    }
}
