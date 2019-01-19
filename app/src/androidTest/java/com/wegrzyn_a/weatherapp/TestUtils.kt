package com.wegrzyn_a.weatherapp

import java.io.File

fun getJson(path: String): String {
    val uri = object {}.javaClass.classLoader.getResource(path)
    val file = File(uri.path)
    return String(file.readBytes())
}