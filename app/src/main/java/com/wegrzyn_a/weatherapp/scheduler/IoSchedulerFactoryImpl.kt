package com.wegrzyn_a.weatherapp.scheduler

import com.wegrzyn_a.weatherapp.scheduler.IoSchedulerFactory
import io.reactivex.Scheduler

class IoSchedulerFactoryImpl(val ioscheduler: Scheduler) :
    IoSchedulerFactory {
    override fun getIOScheduler() = ioscheduler
}