package com.example.airqualityindex.repository

import com.example.airqualityindex.model.AWSCertification
import io.reactivex.rxjava3.core.Single

interface IHubRepository {
    fun requestCertificationApi(uuid: String): Single<AWSCertification>
}