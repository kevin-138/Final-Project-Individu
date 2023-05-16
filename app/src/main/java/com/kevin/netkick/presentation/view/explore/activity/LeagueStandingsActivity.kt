package com.kevin.netkick.presentation.view.explore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityLeagueStandingsBinding

class LeagueStandingsActivity : AppCompatActivity() {
    lateinit var binding: ActivityLeagueStandingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeagueStandingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}