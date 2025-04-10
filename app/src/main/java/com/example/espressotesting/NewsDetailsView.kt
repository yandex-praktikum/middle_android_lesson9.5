package com.example.espressotesting

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Custom view that displays details for a news item, including an image, title, and expandable description.
 */
class NewsDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val imageView: ImageView
    private val titleTextView: TextView
    private val descriptionTextView: TextView
    private val expandCollapseButton: Button
    
    private var isExpanded = false

    init {
        // Inflate the layout
        LayoutInflater.from(context).inflate(R.layout.view_news_details, this, true)
        
        // Initialize views
        imageView = findViewById(R.id.newsImageView)
        titleTextView = findViewById(R.id.newsTitleTextView)
        descriptionTextView = findViewById(R.id.newsDescriptionTextView)
        expandCollapseButton = findViewById(R.id.expandCollapseButton)
        
        // Set up click listener for expand/collapse button
        expandCollapseButton.setOnClickListener {
            toggleContentExpansion()
        }
    }
    
    /**
     * Set the news item to display
     */
    fun setNews(news: News) {
        titleTextView.text = news.title
        descriptionTextView.text = news.description
        
        // In a real app, we would load the image from a URL
        // For now, we're using the launcher background as a placeholder
    }
    
    /**
     * Toggle between expanded and collapsed states
     */
    private fun toggleContentExpansion() {
        isExpanded = !isExpanded
        
        if (isExpanded) {
            // Expand the content
            descriptionTextView.maxLines = Integer.MAX_VALUE
            descriptionTextView.ellipsize = null
            expandCollapseButton.text = "Show Less"
        } else {
            // Collapse the content
            descriptionTextView.maxLines = 5
            descriptionTextView.ellipsize = android.text.TextUtils.TruncateAt.END
            expandCollapseButton.text = "Read More"
        }
    }
    
    /**
     * Return the title TextView for testing purposes
     */
    fun getTitleTextView(): TextView = titleTextView
    
    /**
     * Return the description TextView for testing purposes
     */
    fun getDescriptionTextView(): TextView = descriptionTextView
    
    /**
     * Return the image view for testing purposes
     */
    fun getImageView(): ImageView = imageView
    
    /**
     * Return the expand/collapse button for testing purposes
     */
    fun getExpandCollapseButton(): Button = expandCollapseButton
    
    /**
     * Return whether the content is expanded for testing purposes
     */
    fun isContentExpanded(): Boolean = isExpanded
} 