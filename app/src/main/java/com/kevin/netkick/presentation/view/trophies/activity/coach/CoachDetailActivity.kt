package com.kevin.netkick.presentation.view.trophies.activity.coach

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityCoachDetailBinding
import com.kevin.netkick.domain.entity.coach.ResponseC
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.CoachCareerAdapter
import com.kevin.netkick.presentation.adapters.TrophyAdapter
import com.kevin.netkick.presentation.view.viewmodels.TrophiesViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class CoachDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoachDetailBinding
    private lateinit var adapterCareer: CoachCareerAdapter
    private lateinit var adapterTrophy: TrophyAdapter
    private lateinit var progressBar: AlertDialog
    private var coachData: ResponseC? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: TrophiesViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectIntoCoachDetailActivity(this)
        super.onCreate(savedInstanceState)
        binding = ActivityCoachDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        binding.ibBackButton.setOnClickListener {
            finish()
        }
        checkOnline()
    }

    private fun checkOnline() {
        val onlineCheck = PresentationUtils.isOnline(this)
        if (onlineCheck) {
            checkIntent()
        } else {
            PresentationUtils.networkDialog(this)
        }
    }

    private fun checkIntent() {
        coachData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(PresentationUtils.COACH_FULL_DATA, ResponseC::class.java)
        } else {
            intent.getParcelableExtra(PresentationUtils.COACH_FULL_DATA)
        }

        if (coachData != null) {
            setProgressBar()
            setLayout()
            getLiveData()
        }
    }

    private fun getLiveData() {
        lifecycleScope.launch {
            viewModel.getTrophies(coachData!!.id)
            viewModel.trophiesFlow.collectLatest {
                adapterTrophy.addDataToList(it.response)
                Timer().schedule(2000L) {
                    progressBar.dismiss()
                }
            }
        }
    }

    private fun setLayout() {
        val loadingDrawable1 = CircularProgressDrawable(this)
        loadingDrawable1.strokeWidth = 5f
        loadingDrawable1.centerRadius = 30f
        loadingDrawable1.setColorSchemeColors(Color.WHITE)
        loadingDrawable1.start()

        val loadingDrawable2 = CircularProgressDrawable(this)
        loadingDrawable2.strokeWidth = 5f
        loadingDrawable2.centerRadius = 30f
        loadingDrawable2.setColorSchemeColors(Color.WHITE)
        loadingDrawable2.start()

        binding.apply {
            tvCoachBirth.text = coachData?.birth?.date
            tvCoachAge.text = coachData?.age.toString()
            tvCoachName.text = coachData?.name
            tvCoachNationality.text = coachData?.nationality

            Glide.with(root)
                .load(coachData?.photo)
                .placeholder(loadingDrawable1)
                .error(R.drawable.broken_image_icon)
                .into(ivCoachPhoto)

            Glide.with(root)
                .load(coachData?.team?.logo)
                .placeholder(loadingDrawable1)
                .error(R.drawable.broken_image_icon)
                .into(ivTeamLogoTop)

        }

        adapterCareer.addDataToList(coachData!!.career)
    }

    private fun setupAdapter() {
        binding.apply {
            adapterCareer = CoachCareerAdapter(mutableListOf())
            rvCoachCareer.layoutManager =
                LinearLayoutManager(this@CoachDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            rvCoachCareer.adapter = adapterCareer

            adapterTrophy = TrophyAdapter(mutableListOf())
            rvCoachTrophies.layoutManager =
                LinearLayoutManager(this@CoachDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            rvCoachTrophies.adapter = adapterTrophy
        }
    }

    private fun setProgressBar() {
        progressBar =
            AlertDialog.Builder(this).setCancelable(false).setView(R.layout.loading).create()
        progressBar.show()
        progressBar.window?.setLayout(400, 400)
        progressBar.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)
    }
}