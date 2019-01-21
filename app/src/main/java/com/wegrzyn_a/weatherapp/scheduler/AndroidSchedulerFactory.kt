package com.wegrzyn_a.weatherapp.scheduler

import io.reactivex.Scheduler

interface AndroidSchedulerFactory {
    fun getAndroidScheduler(): Scheduler
}