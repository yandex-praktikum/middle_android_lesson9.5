package com.example.espressotesting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
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
        onView(withId(R.id.loginButton))
            .check(matches(isDisplayed()))
            .check(matches(isNotEnabled()))

        onView(withId(R.id.usernameEditText))
            .perform(typeText("testuser"))

        onView(withId(R.id.passwordEditText))
            .perform(typeText("password"))

        onView(withId(R.id.loginButton))
            .check(matches(isEnabled()))
            .perform(click())

        onView(withId(R.id.newsRecyclerView))
            .check(matches(isDisplayed()))

        getCurrentNewsFragment()

        onView(withId(R.id.newsRecyclerView))
            .perform(RecyclerViewItemClickAction(1))

        onView(withId(R.id.newsDetailsView))
            .check(matches(isDisplayed()))

    }

    /**
     * Helper method to find the current NewsFragment and register its IdlingResource
     */
    private fun getCurrentNewsFragment() {
        val currentActivity = getCurrentActivity()

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