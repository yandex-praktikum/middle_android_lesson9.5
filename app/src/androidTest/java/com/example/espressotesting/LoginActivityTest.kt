package com.example.espressotesting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun testUsernameEditTextIsVisible() {
        onView(withId(R.id.usernameEditText))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testLoginButtonNotEnabled() {
        onView(withText("Login"))
            .check(matches(isNotEnabled()))
    }

    @Test
    fun testUsernameEditTextShowsTypedText() {
        val testUsername = "testuser"
        onView(withId(R.id.usernameEditText))
            .perform(typeText(testUsername))
            .check(matches(withText(testUsername)))
    }

    @Test
    fun testLoginButtonEnabledWhenBothFieldsHaveText() {
        val testUsername = "testuser"
        val testPassword = "testpass"

        onView(withId(R.id.usernameEditText))
            .perform(typeText(testUsername))

        onView(withId(R.id.passwordEditText))
            .perform(typeText(testPassword))

        onView(withId(R.id.loginButton))
            .check(matches(isEnabled()))
    }
} 