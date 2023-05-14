package com.kevin.netkick.presentation.view.general.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityTeamDetailBinding
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.view.viewmodels.PopularTeamViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import javax.inject.Inject

class TeamDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamDetailBinding
    private lateinit var progressBar:AlertDialog

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: PopularTeamViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectInto(this)
        super.onCreate(savedInstanceState)
        binding = ActivityTeamDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setProgressBar()
        checkOnline()
    }
    private fun setProgressBar() {
        progressBar = AlertDialog.Builder(this).setCancelable(false).setView(R.layout.loading).create()
    }

    private fun checkOnline() {
        val onlineCheck = PresentationUtils.isOnline(this)
        if (onlineCheck) {
            setupViews()
        } else {
            PresentationUtils.networkDialog(this)
        }
    }

    private fun setupViews() {
        viewModel.getPopularTeamDetail()
        viewModel
        binding.apply {

        }
    }
}