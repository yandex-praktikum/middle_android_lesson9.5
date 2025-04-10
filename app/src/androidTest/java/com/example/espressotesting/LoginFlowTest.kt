package com.example.espressotesting

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFlowTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    private lateinit var context: Context
    private lateinit var prefs: SharedPreferences
    private lateinit var validUsername: String
    private lateinit var validPassword: String

    @Before
    fun setup() {
        // Get the context and shared preferences
        context = ApplicationProvider.getApplicationContext()
        prefs = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

        // Clear any existing preferences
        prefs.edit().clear().apply()

        // Set up test credentials
        validUsername = "test.user@example.com"
        validPassword = "SecurePass123!"
    }

    @After
    fun teardown() {
        // Clean up preferences after each test
        prefs.edit().clear().apply()
    }

    @Test
    fun testSuccessfulLoginFlow() {
        // 1. Enter valid username
        onView(withId(R.id.usernameEditText))
            .perform(typeText(validUsername), closeSoftKeyboard())

        // 2. Enter valid password
        onView(withId(R.id.passwordEditText))
            .perform(typeText(validPassword), closeSoftKeyboard())

        // 3. Verify login button is enabled
        onView(withId(R.id.loginButton))
            .check(matches(isEnabled()))

        // 4. Click login button
        onView(withId(R.id.loginButton))
            .perform(click())

        // 5. Verify welcome message appears with correct username
        onView(withId(R.id.welcomeTextView))
            .check(matches(isDisplayed()))
            .check(matches(withText("Welcome, $validUsername!")))
    }

    @Test
    fun testLoginButtonInitiallyDisabled() {
        // Verify button is disabled when fields are empty
        onView(withId(R.id.loginButton))
            .check(matches(not(isEnabled())))

        // Enter only username
        onView(withId(R.id.usernameEditText))
            .perform(typeText(validUsername), closeSoftKeyboard())

        // Button should still be disabled
        onView(withId(R.id.loginButton))
            .check(matches(not(isEnabled())))
    }
} 