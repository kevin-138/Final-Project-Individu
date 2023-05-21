package com.kevin.netkick.presentation.view.trophies.activity.coach

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kevin.netkick.databinding.ActivityCoachSearchBinding

class CoachSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoachSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoachSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}