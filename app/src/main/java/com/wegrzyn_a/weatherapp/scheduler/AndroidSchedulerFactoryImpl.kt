package com.wegrzyn_a.weatherapp.scheduler

import io.reactivex.Scheduler

class AndroidSchedulerFactoryImpl(val androScheduler: Scheduler) :
    AndroidSchedulerFactory {
    override fun getAndroidScheduler() = androScheduler
}