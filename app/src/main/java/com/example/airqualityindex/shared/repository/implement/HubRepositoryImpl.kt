package com.example.airqualityindex.shared.repository.implement

import com.example.airqualityindex.shared.api.ApiInstances
import com.example.airqualityindex.shared.model.AWSCertification
import com.example.airqualityindex.shared.repository.IHubRepository
import com.example.airqualityindex.shared.util.JsonFormatTransform
import io.reactivex.rxjava3.core.Observable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class HubRepositoryImpl : IHubRepository {

    override fun requestCertificationApi(uuid: String): Observable<AWSCertification> {
        val jsonFormatTransform = JsonFormatTransform()
        val requestBody: RequestBody =
            jsonFormatTransform.turnJsonFormat(uuid)
                .toRequestBody("application/json".toMediaTypeOrNull())
        return ApiInstances.getCertificationInstance().getCertification(requestBody)
    }
}