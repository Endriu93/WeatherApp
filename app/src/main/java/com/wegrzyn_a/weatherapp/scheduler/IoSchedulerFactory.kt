package com.wegrzyn_a.weatherapp.scheduler

import io.reactivex.Scheduler

interface IoSchedulerFactory {
    fun getIOScheduler(): Scheduler
}