package com.wegrzyn_a.weatherapp

import org.mockito.Mockito
import java.io.File

fun getJson(path: String): String {
    val uri = object {}.javaClass.classLoader.getResource(path)
    val file = File(uri.path)
    return String(file.readBytes())
}

fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}
private fun <T> uninitialized(): T = null as T