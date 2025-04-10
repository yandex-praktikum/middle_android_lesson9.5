package com.example.espressotesting

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsDetailsFragmentTest {

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
        
        onView(withId(R.id.newsRecyclerView))
            .perform(RecyclerViewItemClickAction(0))
    }

    @After
    fun cleanup() {
        IdlingRegistry.getInstance().unregister(newsFragment?.getIdlingResource())
    }

    @Test
    fun testImageIsDisplayed() {
        onView(withId(R.id.newsDetailsView))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.newsImageView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testTitleIsDisplayed() {
        onView(withId(R.id.newsTitleTextView))
            .check(matches(isDisplayed()))
            .check(matches(withText("Горячие новости")))
    }

    @Test
    fun testDescriptionIsCollapsedInitially() {
        onView(withId(R.id.newsDescriptionTextView))
            .check(matches(isDisplayed()))
            .check(matches(withMaxLines(5)))
    }

    @Test
    fun testExpandCollapseButtonTogglesDescription() {
        onView(withId(R.id.newsDescriptionTextView))
            .check(matches(withMaxLines(5)))
        
        onView(withId(R.id.expandCollapseButton))
            .check(matches(withText("Read More")))
        
        onView(withId(R.id.expandCollapseButton))
            .perform(click())
        
        onView(withId(R.id.newsDescriptionTextView))
            .check(matches(withMaxLines(Integer.MAX_VALUE)))
        
        onView(withId(R.id.expandCollapseButton))
            .check(matches(withText("Show Less")))
        
        onView(withId(R.id.expandCollapseButton))
            .perform(click())
        
        onView(withId(R.id.newsDescriptionTextView))
            .check(matches(withMaxLines(5)))
        
        onView(withId(R.id.expandCollapseButton))
            .check(matches(withText("Read More")))
    }

    private fun withMaxLines(maxLines: Int) = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("with maxLines: $maxLines")
        }

        override fun matchesSafely(view: View): Boolean {
            if (view !is android.widget.TextView) return false
            return view.maxLines == maxLines
        }
    }
} 