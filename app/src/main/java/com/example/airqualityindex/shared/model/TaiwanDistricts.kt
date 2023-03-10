package com.example.airqualityindex.shared.model

data class CityWithDistricts(val districts: List<District>, val name: String)

data class District(val zip: String, val name: String)