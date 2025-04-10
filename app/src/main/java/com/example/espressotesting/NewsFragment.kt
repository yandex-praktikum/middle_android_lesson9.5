package com.example.espressotesting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.idling.CountingIdlingResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private val countingIdlingResource = CountingIdlingResource("NewsFragment")

    // Getter for testing
    fun getAdapter(): NewsAdapter = adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.newsRecyclerView)
        adapter = NewsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        
        // Set up click listener for news items
        adapter.setOnItemClickListener { position ->
            navigateToNewsDetails(position)
        }
        
        loadNewsData()
    }

    private fun navigateToNewsDetails(position: Int) {
        val detailsFragment = NewsDetailsFragment.newInstance(position)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, detailsFragment)
            .addToBackStack(null)
            .commit()
    }

    fun getIdlingResource(): CountingIdlingResource {
        return countingIdlingResource
    }

    private fun loadNewsData() {
        countingIdlingResource.increment()
        lifecycleScope.launch {
            // Simulate network delay
            delay(1000)

            // Sample news data
            val newsList = listOf(
                News("Горячие новости", "Vulkan становится обязательным для Android."),
                News(
                    "Новости технологий",
                    "Google добавила поддержку мультимодального Gemini в Android Studio."
                ),
                News(
                    "Мир музыки",
                    "В Android 16 добавили поддержку Auracast - технология беспроводного аудио-вещания на основе Bluetooth LE Audio."
                ),
                News(
                    "Семья",
                    "Jetpack Room 2.7.0 RC02 - подготовка к первому стабильному релизу Room с поддержкой KMP."
                ),
                News(
                    "Безопасность",
                    "Jetpack Credentials 1.5.0 позволяют переносить учетные данные на новое Android-устройство."
                ),
                News("Новости", "Google добавила поддержку мультимодального Gemini в Android Studio."),
                News(
                    "Мир музыки",
                    "В Android 16 добавили поддержку Auracast - технология беспроводного аудио-вещания на основе Bluetooth LE Audio."
                ),
                News(
                    "Семья",
                    "Jetpack Room 2.7.0 RC02 - подготовка к первому стабильному релизу Room с поддержкой KMP."
                ),
                News(
                    "Безопасность",
                    "Jetpack Credentials 1.5.0 позволяют переносить учетные данные на новое Android-устройство."
                ),
                News(
                    "Разработка",
                    "Новый Android Studio Hedgehog включает улучшенные инструменты для отладки и профилирования приложений."
                ),
            )

            adapter.setItems(newsList)
            countingIdlingResource.decrement()
        }
    }

    fun removeNewsItem(position: Int) {
        countingIdlingResource.increment()
        lifecycleScope.launch {
            adapter.removeItem(position)
            countingIdlingResource.decrement()
        }
    }

    fun getNewsItems(): List<News> {
        return adapter.getItems()
    }
} 