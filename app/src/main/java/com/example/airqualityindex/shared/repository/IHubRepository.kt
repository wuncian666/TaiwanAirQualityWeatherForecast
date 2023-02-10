package com.example.airqualityindex.shared.repository

import com.example.airqualityindex.shared.model.AWSCertification
import io.reactivex.rxjava3.core.Observable

interface IHubRepository {
    fun requestCertificationApi(uuid: String): Observable<AWSCertification>
}