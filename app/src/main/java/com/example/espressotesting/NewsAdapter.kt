package com.example.espressotesting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val newsList = mutableListOf<News>()
    private var onItemClickListener: ((position: Int) -> Unit)? = null

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.newsTitleTextView)
        val descriptionTextView: TextView = view.findViewById(R.id.newsDescriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.titleTextView.text = news.title
        holder.descriptionTextView.text = news.description
        
        // Set click listener for the entire item
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(position)
        }
    }

    override fun getItemCount() = newsList.size

    fun setItems(items: List<News>) {
        newsList.clear()
        newsList.addAll(items)
        notifyDataSetChanged()
    }

    fun getItems(): List<News> = newsList.toList()
    
    fun removeItem(position: Int) {
        if (position < newsList.size) {
            newsList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    
    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        onItemClickListener = listener
    }
} 