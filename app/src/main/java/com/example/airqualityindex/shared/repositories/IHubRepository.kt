package com.example.airqualityindex.shared.repositories

import com.example.airqualityindex.shared.models.AWSCertification
import io.reactivex.rxjava3.core.Single

interface IHubRepository {
    fun requestCertificationApi(uuid: String): Single<AWSCertification>
}