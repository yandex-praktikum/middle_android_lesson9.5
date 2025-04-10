package com.example.espressotesting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.test.espresso.idling.CountingIdlingResource

class NewsDetailsFragment : Fragment() {
    private lateinit var newsDetailsView: NewsDetailsView
    
    private var newsId: Int = 0
    private var news: News? = null
    

    companion object {
        private const val ARG_NEWS_ID = "news_id"
        
        fun newInstance(newsId: Int): NewsDetailsFragment {
            val fragment = NewsDetailsFragment()
            val args = Bundle()
            args.putInt(ARG_NEWS_ID, newsId)
            fragment.arguments = args
            return fragment
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newsId = it.getInt(ARG_NEWS_ID)
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_details, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        newsDetailsView = view.findViewById(R.id.newsDetailsView)
        
        // Get the news data
        loadNewsData()
    }
    
    private fun loadNewsData() {

        // In a real app, this would fetch from a repository or service
        // For demo purposes, we'll use the mock data from NewsFragment
        val allNews = getMockNewsData()
        
        if (newsId < allNews.size) {
            news = allNews[newsId]
            news?.let { newsDetailsView.setNews(it) }
        }
        
    }
    
    private fun getMockNewsData(): List<News> {
        // This would usually come from a repository
        return listOf(
            News("Горячие новости", "Vulkan становится обязательным для Android. Эта технология обеспечивает более эффективную работу графики и улучшает производительность игр. Разработчики должны будут адаптировать свои приложения к новому стандарту. Это изменение повлияет на миллионы устройств по всему миру. Пользователи заметят улучшение графики в играх и других графически интенсивных приложениях."),
            News(
                "Новости технологий",
                "Google добавила поддержку мультимодального Gemini в Android Studio. Эта функция позволяет разработчикам создавать более интуитивные и интерактивные приложения. Gemini может анализировать текст, изображения и другие типы данных одновременно. Этот инструмент существенно упрощает разработку приложений с искусственным интеллектом. Многие разработчики уже начали экспериментировать с новыми возможностями."
            ),
            News(
                "Мир музыки",
                "В Android 16 добавили поддержку Auracast - технология беспроводного аудио-вещания на основе Bluetooth LE Audio. Теперь пользователи могут делиться аудио с несколькими устройствами одновременно. Это открывает новые возможности для совместного прослушивания музыки и просмотра видео. Качество звука также улучшено благодаря новым аудиокодекам. Производители наушников уже работают над обновлением своих устройств для поддержки этой функции."
            ),
            News(
                "Семья",
                "Jetpack Room 2.7.0 RC02 - подготовка к первому стабильному релизу Room с поддержкой KMP. Это важный шаг в направлении кроссплатформенной разработки. Разработчики смогут использовать один код для работы с базами данных на Android и iOS. Оптимизации производительности также включены в этот релиз. Команда Android продолжает работать над улучшением инструментов для разработчиков."
            ),
            News(
                "Безопасность",
                "Jetpack Credentials 1.5.0 позволяют переносить учетные данные на новое Android-устройство. Это делает процесс перехода на новый телефон более удобным и безопасным. Пользователи больше не будут терять доступ к своим аккаунтам при смене устройства. Новая система также предлагает улучшенную защиту от фишинга. Многофакторная аутентификация становится более надежной благодаря этому обновлению."
            )
        )
    }
    
    // Added methods to expose views for testing
    
    fun getNewsDetailsView(): NewsDetailsView {
        return newsDetailsView
    }
} 