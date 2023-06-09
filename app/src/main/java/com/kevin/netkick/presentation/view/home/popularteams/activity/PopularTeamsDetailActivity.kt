package com.kevin.netkick.presentation.view.home.popularteams.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kevin.netkick.databinding.ActivityPopularTeamsDetailBinding

class PopularTeamsDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityPopularTeamsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPopularTeamsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}