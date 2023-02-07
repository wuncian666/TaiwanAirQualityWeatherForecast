package com.example.airqualityindex.shared.api

class ApiInstances {
    companion object {
        private const val BASE_URL = "https://data.epa.gov.tw/api/v2/"

         fun getRetrofitInstance(): AQIService {
            return RetrofitInstances.getRetrofitInstance(BASE_URL)
                .create(AQIService::class.java)
        }

        private const val SENSOR_THINGS_URL = "https://sta.ci.taiwan.gov.tw/STA_AirQuality_EPAIoT/v1.0/"

        fun getSensorThingsInstance(): SensorThingsService {
            return RetrofitInstances.getX509RetrofitInstance(SENSOR_THINGS_URL)
                .create(SensorThingsService::class.java)
        }

        private const val CERTIFICATION_URL = "http://35.155.109.138:3000/api/certification/"

        fun getCertificationInstance(): CertificationService {
            return RetrofitInstances.getRetrofitInstance(CERTIFICATION_URL)
                .create(CertificationService::class.java)
        }

        private const val WEATHER_URL = "https://opendata.cwb.gov.tw/api/v1/"

        fun getWeatherInstance(): WeatherForecastService {
            return RetrofitInstances.getRetrofitInstance(WEATHER_URL)
                .create(WeatherForecastService::class.java)
        }
    }
}