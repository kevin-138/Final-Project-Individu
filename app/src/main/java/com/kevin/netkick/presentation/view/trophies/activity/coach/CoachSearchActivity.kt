package com.kevin.netkick.presentation.view.trophies.activity.coach

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.databinding.ActivityCoachSearchBinding
import com.kevin.netkick.presentation.adapters.LeagueTopAdapter
import com.kevin.netkick.presentation.view.viewmodels.TrophiesViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import javax.inject.Inject

class CoachSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoachSearchBinding
    private lateinit var adapter: LeagueTopAdapter
    private lateinit var progressBar: AlertDialog

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: TrophiesViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectIntoCoachSearchActivity(this)
        super.onCreate(savedInstanceState)
        binding = ActivityCoachSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        binding.ibBackButton.setOnClickListener {
            finish()
        }
        checkOnline()
    }

    private fun setObserver() {
        viewModel.searchResults.observe(this){
            adapter.addDataToList(it.response)
        }
    }
}