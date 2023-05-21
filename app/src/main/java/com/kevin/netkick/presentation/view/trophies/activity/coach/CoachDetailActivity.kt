package com.kevin.netkick.presentation.view.trophies.activity.coach

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kevin.netkick.databinding.ActivityCoachDetailBinding

class CoachDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoachDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoachDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}