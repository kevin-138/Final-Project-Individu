package com.kevin.netkick.presentation.view.general.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityLeagueSearchBinding
import com.kevin.netkick.domain.entity.country.CountryC
import com.kevin.netkick.presentation.adapters.LeagueAdapter

class LeagueSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeagueSearchBinding
    private lateinit var adapter: LeagueAdapter
    private lateinit var dataList: ArrayList<CountryC>
    private lateinit var progressBar: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeagueSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}