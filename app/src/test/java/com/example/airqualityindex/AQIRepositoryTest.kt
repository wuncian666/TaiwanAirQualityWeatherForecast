package com.example.airqualityindex

import MockAPIResponse
import MockInterceptor
import Utils

class AQIRepositoryTest {

   // private lateinit var repository: IAQIRepository

    fun getRecord() {
        val interceptor = MockInterceptor()

        interceptor.setInterceptorListener(object : MockInterceptor.MockInterceptorListener {
            override fun setAPIResponse(url: String): MockAPIResponse? {
                val mockAPIResponse = MockAPIResponse()
                mockAPIResponse.status = 200
                mockAPIResponse.responseString =
                    Utils.readStringFromResource("AQX_P_434_Metadata.json")
                return mockAPIResponse
            }
        })

       // val networkService = NetworkService(interceptor)

    }

}