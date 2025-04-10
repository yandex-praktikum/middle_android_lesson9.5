package com.example.espressotesting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test class that verifies the complete navigation flow from LoginActivity through NewsFragment to NewsDetailsFragment.
 */
@RunWith(AndroidJUnit4::class)
class FullNavigationFlowTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    private var newsFragment: NewsFragment? = null

    @After
    fun cleanup() {
        IdlingRegistry.getInstance().unregister(newsFragment?.getIdlingResource())
    }

    @Test
    fun testCompleteNavigationFlow() {
        // Step 1: Verify we're on the LoginActivity
        onView(withId(R.id.loginButton))
            .check(matches(isDisplayed()))
            .check(matches(isNotEnabled()))  // Button should be disabled initially

        // Step 2: Enter login credentials
        onView(withId(R.id.usernameEditText))
            .perform(typeText("testuser"))

        onView(withId(R.id.passwordEditText))
            .perform(typeText("password"))

        // Step 3: Verify login button is now enabled and click it
        onView(withId(R.id.loginButton))
            .check(matches(isEnabled()))
            .perform(click())

        // Step 5: Wait for transition to MainActivity using IdlingResource

        // Step 6: Verify the NewsFragment's RecyclerView is displayed
        // This verifies we've successfully transitioned to MainActivity
        onView(withId(R.id.newsRecyclerView))
            .check(matches(isDisplayed()))

        // Now find and register the IdlingResource from the current fragment
        getCurrentNewsFragment()

        // Step 7: Click on the second news item
        onView(withId(R.id.newsRecyclerView))
            .perform(RecyclerViewItemClickAction(1))  // Click on the second item (index 1)

        // Step 8: Verify we've navigated to NewsDetailsFragment with our custom view
        // Verify the custom view is displayed
        onView(withId(R.id.newsDetailsView))
            .check(matches(isDisplayed()))

    }

    /**
     * Helper method to find the current NewsFragment and register its IdlingResource
     */
    private fun getCurrentNewsFragment() {
        // Get the current activity
        val currentActivity = getCurrentActivity()

        // Find the NewsFragment from the current activity
        if (currentActivity is MainActivity) {
            InstrumentationRegistry.getInstrumentation().runOnMainSync {
                newsFragment = currentActivity.supportFragmentManager
                    .findFragmentById(R.id.fragmentContainer) as? NewsFragment
                IdlingRegistry.getInstance().register(newsFragment?.getIdlingResource())
            }
        }
    }

    /**
     * Helper method to get the current activity
     */
    private fun getCurrentActivity(): androidx.fragment.app.FragmentActivity? {
        var currentActivity: androidx.fragment.app.FragmentActivity? = null

        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            val resumedActivities = ActivityLifecycleMonitorRegistry.getInstance()
                .getActivitiesInStage(Stage.RESUMED)

            if (resumedActivities.iterator().hasNext()) {
                val activity = resumedActivities.iterator().next()
                if (activity is androidx.fragment.app.FragmentActivity) {
                    currentActivity = activity
                }
            }
        }

        return currentActivity
    }
} 