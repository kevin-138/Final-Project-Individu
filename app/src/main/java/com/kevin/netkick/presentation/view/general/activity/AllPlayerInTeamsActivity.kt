package com.kevin.netkick.presentation.view.general.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityAllPlayerInTeamsBinding
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.PlayersPagingAdapter
import com.kevin.netkick.presentation.view.viewmodels.PopularTeamViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllPlayerInTeamsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllPlayerInTeamsBinding
    private lateinit var adapter: PlayersPagingAdapter
    private lateinit var progressBar: AlertDialog

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: PopularTeamViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectInto(this)
        super.onCreate(savedInstanceState)
        binding = ActivityAllPlayerInTeamsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setProgressBar()
        binding.ibBackButton.setOnClickListener {
            finish()
        }
        setupAdapter()
        checkOnline()
    }

    private fun setupAdapter() {
        binding.rvTeamPlayers.layoutManager = LinearLayoutManager(this)
        adapter = PlayersPagingAdapter()
        binding.rvTeamPlayers.adapter = adapter
    }

    private fun setProgressBar() {
        progressBar = AlertDialog.Builder(this).setCancelable(false).setView(R.layout.loading).create()
        progressBar.show()
        progressBar.window?.setLayout(400, 400)
        progressBar.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)
    }

    private fun checkOnline() {
        val onlineCheck = PresentationUtils.isOnline(this)
        if (onlineCheck) {
            getPlayerList()
        } else {
            PresentationUtils.networkDialog(this)
        }
    }

    private fun getPlayerList() {
        lifecycleScope.launch {
            viewModel.getTeamPlayers(this,
            intent.getIntExtra(PresentationUtils.TEAM_ID,0),
                intent.getIntExtra(PresentationUtils.TEAM_SEASON,0)
                ).collectLatest {
                    adapter.submitData(lifecycle,it)
                progressBar.dismiss()
            }
        }
    }

}