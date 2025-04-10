package com.example.espressotesting

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf

/**
 * A custom ViewAction that clicks on a specific RecyclerView item at a given position.
 */
class RecyclerViewItemClickAction(private val position: Int) : ViewAction {
    
    override fun getConstraints(): Matcher<View> {
        return AllOf.allOf(
            ViewMatchers.isAssignableFrom(RecyclerView::class.java),
            ViewMatchers.isDisplayed()
        )
    }

    override fun getDescription(): String {
        return "Click on item at position $position in RecyclerView"
    }

    override fun perform(uiController: UiController, view: View) {
        val recyclerView = view as RecyclerView
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
            ?: throw IllegalStateException("No view holder at position $position")
        
        viewHolder.itemView.performClick()
    }
} 