package com.example.airqualityindex.units

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.airqualityindex.api.ApiInstances
import com.example.airqualityindex.model.Location
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers


class AlarmReceiver: BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        getWeather(SystemTime().getCurrentTime())
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun getWeather(date: String): Single<List<Location>> {
        return ApiInstances.getWeatherInstance().getWeatherForecast(date)
            .map { it.records.location }
    }
}