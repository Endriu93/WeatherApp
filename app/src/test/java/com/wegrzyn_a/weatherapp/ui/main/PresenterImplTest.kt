package com.wegrzyn_a.weatherapp.ui.main

import com.wegrzyn_a.weatherapp.any
import com.wegrzyn_a.weatherapp.mockInteractorGetTemps
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class PresenterImplTest {

    @Mock
    lateinit var view: MVP.View

    @Mock
    lateinit var interactor: MVP.Interactor

    lateinit var presenter: PresenterImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PresenterImpl(interactor)
    }

    @Test
    fun testSubscribe() {
        mockInteractorGetTemps(interactor,emptyList<String>())

        assertNull(presenter._view)
        presenter.subscribe(view)
        assertNotNull(presenter._view)
    }

    @Test
    fun testUnSubscribe() {
        presenter.unSubscribe()
        assertNull(presenter._view)
    }

    @Test
    fun testActionOnSubscribe() {
        val temp = "1"
        mockInteractorGetTemps(interactor,listOf(temp))

        presenter.subscribe(view)

        verify(view).showTempForToday(temp)
    }



    @Test
    fun testInteractorReturnsEmptyList() {
        mockInteractorGetTemps(interactor,emptyList<String>())

        presenter.subscribe(view)

        verify(view).showError(any())
    }

}