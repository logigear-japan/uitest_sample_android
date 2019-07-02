package com.example.myapplicationtest

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import android.support.test.filters.LargeTest
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.junit.Rule



/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.myapplication", appContext.packageName)
        Thread.sleep(500)
    }

    @Test
    fun checkTextHelloWorld() {
        onView(withId(R.id.main_content_text)).check(matches(withText(containsString("Hello World!"))))
        Thread.sleep(500)
    }

    @Test
    fun checkButtonEmailFloating() {
        onView(withId(R.id.email_fab_text)).check(matches(withText("+")))
        onView(withId(R.id.email_fab)).perform(click())
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(containsString("Tapped"))))
            .check(matches(isDisplayed()))
        Thread.sleep(500)
    }


}
