package com.kevin.netkick.presentation.view.home.popularteams.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityPopularTeamsListBinding
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.PopularTeamsListAdapter
import com.kevin.netkick.presentation.view.viewmodels.PopularTeamViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularTeamsListActivity : AppCompatActivity() {
    lateinit var binding: ActivityPopularTeamsListBinding
    private lateinit var progressBar:AlertDialog
    private lateinit var adapter:PopularTeamsListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: PopularTeamViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectInto(this)
        super.onCreate(savedInstanceState)
        binding = ActivityPopularTeamsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setProgressBar()
        setupAdapter()
        checkOnline()
    }

    private fun checkOnline() {
        val onlineCheck = PresentationUtils.isOnline(this)
        if (onlineCheck) {
            getTeamList()
        } else {
            PresentationUtils.networkDialog(this)
        }
    }

    private fun getTeamList() {
        progressBar.show()
        progressBar.window?.setLayout(400, 400)
        progressBar.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)

        lifecycleScope.launch {
            viewModel.getPopularTeams()
            viewModel.popularTeamsFlow.collectLatest {
                adapter.addDataToList(it.response)
                progressBar.dismiss()
            }
        }

    }

    private fun setupAdapter() {
        binding.apply {
            adapter = PopularTeamsListAdapter(arrayListOf())
            rvPopularTeamsList.layoutManager = LinearLayoutManager(this@PopularTeamsListActivity)
            rvPopularTeamsList.adapter = adapter
        }
    }

    private fun setProgressBar() {
        progressBar = AlertDialog.Builder(this).setCancelable(false).setView(R.layout.loading).create()
    }
}