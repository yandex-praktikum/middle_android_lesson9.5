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
        // Access the NewsFragment
        activityRule.scenario.onActivity { activity ->
            newsFragment = activity.supportFragmentManager
                .findFragmentById(R.id.fragmentContainer) as? NewsFragment

            // Register the idling resource
            IdlingRegistry.getInstance().register(newsFragment?.getIdlingResource())
        }
        
        // Wait for the RecyclerView to be displayed and click the first item
        onView(withId(R.id.newsRecyclerView))
            .check(matches(isDisplayed()))
        
        // Click on the first news item to navigate to details
        onView(withId(R.id.newsRecyclerView))
            .perform(RecyclerViewItemClickAction(0))
    }

    @After
    fun cleanup() {
        IdlingRegistry.getInstance().unregister(newsFragment?.getIdlingResource())
    }

    @Test
    fun testImageIsDisplayed() {
        // Get the view ID from the custom view
        onView(withId(R.id.newsDetailsView))
            .check(matches(isDisplayed()))
        
        // Directly check the ImageView in the custom view
        onView(withId(R.id.newsImageView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testTitleIsDisplayed() {
        // The first news item title should be displayed
        onView(withId(R.id.newsTitleTextView))
            .check(matches(isDisplayed()))
            .check(matches(withText("Горячие новости")))
    }

    @Test
    fun testDescriptionIsCollapsedInitially() {
        // Check if the description is initially collapsed (maxLines = 5)
        onView(withId(R.id.newsDescriptionTextView))
            .check(matches(isDisplayed()))
            .check(matches(withMaxLines(5)))
    }

    @Test
    fun testExpandCollapseButtonTogglesDescription() {
        // Initially the description should be collapsed
        onView(withId(R.id.newsDescriptionTextView))
            .check(matches(withMaxLines(5)))
        
        // Button should say "Read More"
        onView(withId(R.id.expandCollapseButton))
            .check(matches(withText("Read More")))
        
        // Click the button to expand
        onView(withId(R.id.expandCollapseButton))
            .perform(click())
        
        // Description should be expanded (maxLines = Integer.MAX_VALUE)
        onView(withId(R.id.newsDescriptionTextView))
            .check(matches(withMaxLines(Integer.MAX_VALUE)))
        
        // Button should say "Show Less"
        onView(withId(R.id.expandCollapseButton))
            .check(matches(withText("Show Less")))
        
        // Click again to collapse
        onView(withId(R.id.expandCollapseButton))
            .perform(click())
        
        // Description should be collapsed again
        onView(withId(R.id.newsDescriptionTextView))
            .check(matches(withMaxLines(5)))
        
        // Button should say "Read More" again
        onView(withId(R.id.expandCollapseButton))
            .check(matches(withText("Read More")))
    }

    // Custom matcher to check the maxLines property of a TextView
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