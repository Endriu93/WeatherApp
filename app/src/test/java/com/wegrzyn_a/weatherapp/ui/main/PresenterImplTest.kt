package com.wegrzyn_a.weatherapp.ui.main

import com.wegrzyn_a.weatherapp.any
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.verify
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
        assertNull(presenter.view)
        presenter.subscribe(view)
        assertNotNull(presenter.view)
    }

    @Test
    fun testUnSubscribe() {
        presenter.unSubscribe()
        assertNull(presenter.view)
    }

    @Test
    fun testActionOnSubscribe() {
        val temp = "1"
        mockInteractorGetTemps(listOf(temp))

        presenter.subscribe(view)

        verify(view).showTempForToday(temp)
    }

    @Test
    fun testInteractorReturnsEmptyList() {
        mockInteractorGetTemps(emptyList())

        presenter.subscribe(view)

        verify(view).showError(any())
    }

    private fun mockInteractorGetTemps(temps: List<String>) {
        doAnswer { (it.arguments[0] as (List<String>)->Unit).invoke(temps) }.`when`(
            interactor
        ).getTemps(any(), any())
    }
}