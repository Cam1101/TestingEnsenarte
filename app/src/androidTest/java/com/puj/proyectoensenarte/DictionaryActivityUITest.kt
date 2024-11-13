package com.puj.proyectoensenarte

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.puj.proyectoensenarte.dictionary.testing.DictionaryTestingActivity
import org.junit.Test

class DictionaryActivityUITest {

    @Test
    fun testSearchFunctionality_categoryFound() {
        val scenario = ActivityScenario.launch(DictionaryTestingActivity::class.java)

        onView(withId(R.id.etSearch)).perform(replaceText("Aseo"))
        onView(withId(R.id.ivSearch)).perform(click())
        onView(withId(R.id.tvSearchMessage))
            .check(matches(isDisplayed()))
            .check(matches(withText("Categorías encontradas: Aseo personal")))

        scenario.close()
    }

    @Test
    fun testSearchFunctionality_categoryNotFound() {
        val scenario = ActivityScenario.launch(DictionaryTestingActivity::class.java)

        onView(withId(R.id.etSearch)).perform(replaceText("Electrónica"))
        onView(withId(R.id.ivSearch)).perform(click())

        onView(withId(R.id.tvSearchMessage))
            .check(matches(isDisplayed()))
            .check(matches(withText("No se encontraron categorías.")))

        scenario.close()
    }

    @Test
    fun testSearchFunctionality_emptyQuery() {
        val scenario = ActivityScenario.launch(DictionaryTestingActivity::class.java)

        onView(withId(R.id.etSearch)).perform(replaceText(""))
        onView(withId(R.id.ivSearch)).perform(click())

        onView(withId(R.id.tvSearchMessage))
            .check(matches(isDisplayed()))
            .check(matches(withText("Por favor, ingrese un término de búsqueda")))

        scenario.close()
    }
}
