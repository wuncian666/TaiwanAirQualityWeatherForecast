package com.example.airqualityindex

import io.reactivex.rxjava3.schedulers.Schedulers

class SchedulerProvider {
    companion object {
        fun computation() = Schedulers.trampoline()
        fun mainThread() = Schedulers.trampoline()
        fun io() = Schedulers.trampoline()
    }
}