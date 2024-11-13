package com.puj.proyectoensenarte

import com.puj.proyectoensenarte.dictionary.testing.SearchHelper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DictionaryTest {

    private lateinit var searchHelper: SearchHelper

    @Before
    fun setUp() {
        searchHelper = SearchHelper()
    }

    @Test
    fun testPerformSearch_categoryFound() {
        val result = searchHelper.performSearch("Aseo")
        val expectedMessage = "Categorías encontradas: Aseo personal"
        assertEquals(expectedMessage, result)
    }

    @Test
    fun testPerformSearch_categoryNotFound() {
        val result = searchHelper.performSearch("Electrónica")
        val expectedMessage = "No se encontraron categorías."
        assertEquals(expectedMessage, result)
    }

    @Test
    fun testPerformSearch_emptyQuery() {
        val result = searchHelper.performSearch("")
        val expectedMessage = "Por favor, ingrese un término de búsqueda"
        assertEquals(expectedMessage, result)
    }
}
