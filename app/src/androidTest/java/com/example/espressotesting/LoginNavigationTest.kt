package com.example.espressotesting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * TODO("Add documentation")
 */

@RunWith(AndroidJUnit4::class)
class LoginNavigationTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun typeCredentials() {
        val testUsername = "testuser"
        val testPassword = "testpass"

        onView(withId(R.id.usernameEditText))
            .perform(typeText(testUsername))

        onView(withId(R.id.passwordEditText))
            .perform(typeText(testPassword))

        onView(withId(R.id.loginButton))
            .check(matches(isEnabled()))
    }

    @Test
    fun testNavigationToNews() {
        onView(withId(R.id.loginButton))
            .perform(click())
        onView(withId(R.id.newsRecyclerView))
            .check(matches(isDisplayed()))



        onView(withId(R.id.newsRecyclerView))
            .perform(
                RecyclerViewItemClickAction(0)
            )

        // Verify that we're on the details screen by checking for the custom view
        onView(withId(R.id.newsDetailsView))
            .check(matches(isDisplayed()))
    }
}