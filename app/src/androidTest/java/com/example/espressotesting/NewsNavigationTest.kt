package com.example.espressotesting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test class that verifies the navigation between NewsFragment and NewsDetailsFragment.
 */
@RunWith(AndroidJUnit4::class)
class NewsNavigationTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private var newsFragment: NewsFragment? = null

    @Before
    fun setup() {
        activityRule.scenario.onActivity { activity ->
            newsFragment = activity.supportFragmentManager
                .findFragmentById(R.id.fragmentContainer) as? NewsFragment
            IdlingRegistry.getInstance().register(newsFragment?.getIdlingResource())
        }

        onView(withId(R.id.newsRecyclerView))
            .check(matches(isDisplayed()))
    }

    @After
    fun cleanup() {
            IdlingRegistry.getInstance().unregister(newsFragment?.getIdlingResource())
        }
    
    @Test
    fun testNavigationToDetailsAndBackToNews() {
        onView(withId(R.id.newsRecyclerView))
            .perform(
                RecyclerViewItemClickAction(0)
            )
        
        onView(withId(R.id.newsDetailsView))
            .check(matches(isDisplayed()))
        
        pressBack()
        
        onView(withId(R.id.newsRecyclerView))
            .check(matches(isDisplayed()))
    }
} 