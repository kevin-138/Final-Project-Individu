package com.kevin.netkick.presentation.view.home.news.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectInto(this)
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}