package com.example.espressotesting

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private var newsFragment: NewsFragment? = null
    private var adapter: NewsAdapter? = null

    @Before
    fun setup() {

        activityRule.scenario.onActivity { activity ->
            newsFragment = activity.supportFragmentManager
                .findFragmentById(R.id.fragmentContainer) as? NewsFragment
            IdlingRegistry.getInstance().register(newsFragment?.getIdlingResource())
        }
    }

    @After
    fun cleanup() {
        IdlingRegistry.getInstance().unregister(newsFragment?.getIdlingResource())
    }

    @Test
    fun testRecyclerViewIsDisplayed() {
        onView(withId(R.id.newsRecyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerViewHasItems() {
        onView(withId(R.id.newsRecyclerView))
            .check(matches(isDisplayed()))

        activityRule.scenario.onActivity {
            adapter = newsFragment?.getAdapter()
        }
        
        val itemCount = adapter?.itemCount ?: 0
        
        assertTrue("RecyclerView should not be empty", itemCount > 0)
    }

    @Test
    fun testFirstItemHasValidContent() {
        onView(withId(R.id.newsRecyclerView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.newsRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(matches(atPosition(0, hasValidNewsItem())))
    }

    @Test
    fun testLastItemHasValidContent() {
        onView(withId(R.id.newsRecyclerView))
            .check(matches(isDisplayed()))

        activityRule.scenario.onActivity {
            adapter = newsFragment?.getAdapter()
        }

        val itemCount = adapter?.itemCount ?: 0
        assertTrue("RecyclerView should have items", itemCount > 0)

        onView(withId(R.id.newsRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))
            .check(matches(atPosition(itemCount - 1, hasValidNewsItem())))
    }

    private fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                    ?: return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    private fun hasValidNewsItem(): Matcher<View> {
        return object : BoundedMatcher<View, View>(View::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has valid title and description")
            }

            override fun matchesSafely(view: View): Boolean {
                val titleView = view.findViewById<TextView>(R.id.newsTitleTextView)
                val descriptionView = view.findViewById<TextView>(R.id.newsDescriptionTextView)

                return titleView != null && descriptionView != null &&
                        !titleView.text.isNullOrBlank() &&
                        !descriptionView.text.isNullOrBlank()
            }
        }
    }
} 