package com.example.airqualityindex

class SchedulerProvider {
    companion object {
        fun computaion() = Schedulers.computation()
        fun mainThread() = AndroidSchedulers.mainThread()!!
        fun io() = Schedulers.io()
    }
}