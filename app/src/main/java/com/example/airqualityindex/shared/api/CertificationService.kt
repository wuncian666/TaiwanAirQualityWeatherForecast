package com.example.airqualityindex.shared.api

import com.example.airqualityindex.shared.model.AWSCertification
import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface CertificationService {
    @POST(".")
    fun getCertification(@Body body: RequestBody): Observable<AWSCertification>
}