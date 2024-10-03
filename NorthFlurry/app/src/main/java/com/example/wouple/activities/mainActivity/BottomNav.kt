package com.example.wouple.activities.mainActivity

sealed class Screens(val route: String){
    object Home: Screens(route = "Home_route")
    object Details: Screens(route = "Details_route")
    object Settings: Screens(route = "Settings_route")
}