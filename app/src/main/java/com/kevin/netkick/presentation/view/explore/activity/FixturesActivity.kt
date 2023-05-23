package com.kevin.netkick.presentation.view.explore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityFixturesBinding

class FixturesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFixturesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = ActivityFixturesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}