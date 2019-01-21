package com.wegrzyn_a.weatherapp

import android.support.test.InstrumentationRegistry
import android.support.test.internal.runner.junit4.statement.UiThreadStatement
import org.mockito.Mockito
import java.io.File
import java.lang.Exception
import java.nio.charset.Charset

fun getJson(path: String): String {
    var isInstrumented = true

    try {
        InstrumentationRegistry.getContext()
    }
    catch (e: Exception){
        isInstrumented = false
    }

    if(isInstrumented)
    {
        val stream = InstrumentationRegistry.getContext().resources.assets.open(path)
        val size = stream.available();
        val buffer = ByteArray(size);
        stream.read(buffer);
        stream.close();
        return buffer.toString(Charset.defaultCharset())
    }
    else {
        val uri = object {}.javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}

fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}

private fun <T> uninitialized(): T = null as T

fun <T> runOnView(view: T, f: (T) -> Unit) {
    UiThreadStatement.runOnUiThread { f(view) }
}