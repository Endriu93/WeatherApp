package com.wegrzyn_a.weatherapp

import android.support.test.internal.runner.junit4.statement.UiThreadStatement
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

fun <T> runOnView(view: T,f: (T) -> Unit) {
    UiThreadStatement.runOnUiThread { f(view) }
}