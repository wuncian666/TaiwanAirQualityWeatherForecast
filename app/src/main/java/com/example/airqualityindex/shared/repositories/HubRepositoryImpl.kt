package com.example.airqualityindex.repository

import com.example.airqualityindex.api.ApiInstances
import com.example.airqualityindex.model.AWSCertification
import com.example.airqualityindex.units.JsonFormatTransform
import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class HubRepositoryImpl: IHubRepository {
    override fun requestCertificationApi(uuid: String): Single<AWSCertification> {
        val jsonFormatTransform = JsonFormatTransform()
        val requestBody: RequestBody =
            jsonFormatTransform.turnJsonFormat(uuid).toRequestBody("application/json".toMediaTypeOrNull())
        return ApiInstances.getCertificationInstance().getCertification(requestBody)
    }
}