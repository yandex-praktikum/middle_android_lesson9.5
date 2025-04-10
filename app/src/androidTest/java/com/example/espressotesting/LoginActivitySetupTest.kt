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
        // Initialize test data before each test
        testUsername = "testUser123"
        testPassword = "testPass123"
        
        // You could also do other setup here like:
        // - Clear shared preferences
        // - Initialize test data
        // - Set up mock objects
    }

    @After
    fun teardown() {
        // Clean up after each test
        // This could include:
        // - Clearing data
        // - Closing resources
        // - Resetting state
    }

    @Test
    fun testLoginFieldsWithTestData() {
        // Type the username that was set up in @Before
        onView(withId(R.id.usernameEditText))
            .perform(typeText(testUsername))
            .check(matches(withText(testUsername)))

        // Type the password that was set up in @Before
        onView(withId(R.id.passwordEditText))
            .perform(typeText(testPassword))
            .check(matches(withText(testPassword)))

        // Verify both fields are displayed
        onView(withId(R.id.usernameEditText))
            .check(matches(isDisplayed()))
        onView(withId(R.id.passwordEditText))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testAnotherTestUsingSetupData() {
        // This test also has access to testUsername and testPassword
        onView(withId(R.id.usernameEditText))
            .perform(typeText(testUsername))
            .check(matches(withText(testUsername)))
    }
} 