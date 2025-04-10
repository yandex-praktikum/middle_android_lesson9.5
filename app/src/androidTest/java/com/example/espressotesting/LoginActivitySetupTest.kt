package com.example.espressotesting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivitySetupTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    private lateinit var testUsername: String
    private lateinit var testPassword: String

    @Before
    fun setup() {
        testUsername = "testUser123"
        testPassword = "testPass123"
    }

    @Test
    fun testLoginFieldsWithTestData() {
        onView(withId(R.id.usernameEditText))
            .perform(typeText(testUsername))
            .check(matches(withText(testUsername)))

        onView(withId(R.id.passwordEditText))
            .perform(typeText(testPassword))
            .check(matches(withText(testPassword)))

        onView(withId(R.id.usernameEditText))
            .check(matches(isDisplayed()))
        onView(withId(R.id.passwordEditText))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testAnotherTestUsingSetupData() {
        onView(withId(R.id.usernameEditText))
            .perform(typeText(testUsername))
            .check(matches(withText(testUsername)))
    }
} 