package com.kevin.netkick.presentation.view.explore.activity

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityFixturesDetailBinding
import com.kevin.netkick.domain.entity.fixtures.ResponseF
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.StatAdapter
import com.kevin.netkick.presentation.intentmodel.StatisticRequirement
import com.kevin.netkick.presentation.view.viewmodels.ExploreViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FixturesDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFixturesDetailBinding
    private lateinit var progressBar: AlertDialog
    private lateinit var adapter: StatAdapter
    private var fixtureData: ResponseF? = null
    private var statRequirement: StatisticRequirement? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ExploreViewModel by viewModels {
        viewModelFactory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectIntoStatisticActivity(this)
        super.onCreate(savedInstanceState)
        binding = ActivityFixturesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        binding.ibBackButton.setOnClickListener {
            finish()
        }
        checkOnline()
    }

    private fun setupAdapter() {
        val noScrollLayoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        binding.apply {
            adapter = StatAdapter(mutableListOf())
            rvStatistics.layoutManager = noScrollLayoutManager
            rvStatistics.adapter = adapter
        }
    }

    private fun checkOnline() {
        val onlineCheck = PresentationUtils.isOnline(this)
        if (onlineCheck) {
            checkIntent()
        } else {
            PresentationUtils.networkDialog(this)
        }
    }

    private fun checkIntent(){
        fixtureData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(PresentationUtils.FIXTURE_FULL_DATA, ResponseF::class.java)
        }else{
            intent.getParcelableExtra(PresentationUtils.FIXTURE_FULL_DATA)
        }

        statRequirement = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(PresentationUtils.FIXTURE_REQUIREMENT, StatisticRequirement::class.java)
        }else{
            intent.getParcelableExtra(PresentationUtils.FIXTURE_REQUIREMENT)
        }

        if (fixtureData != null){
            setProgressBar()
            setLayout()
            getData()
        }
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.getFixtureStatistic(fixtureData!!.fixture.id)
            viewModel.fixtureStatistics.collectLatest {
                adapter.addDataToList(it.response)
                progressBar.dismiss()
            }
        }
    }


    private fun setLayout() {
        val loadingDrawable1 = CircularProgressDrawable(this)
        loadingDrawable1.strokeWidth = 5f
        loadingDrawable1.centerRadius = 30f
        loadingDrawable1.setColorSchemeColors(Color.WHITE)
        loadingDrawable1.start()
        binding.apply {
            tvLeagueName.text = statRequirement!!.leagueName
            tvLeagueSeason.text = statRequirement!!.season.toString()
            tvLeagueRound.text = statRequirement!!.round
            tvFixtureMatches.text = getString(
                R.string.goalsFixture,
                fixtureData!!.goals.home,
                fixtureData!!.goals.away
            )
            tvMatchOpponents.text = getString(
                R.string.vsFixture,
                fixtureData!!.teams.homeTeam.name,
                fixtureData!!.teams.awayTeam.name
            )
            tvFixtureStatus.text = fixtureData!!.fixture.status.long
            tvFixtureWinner.text = if (fixtureData!!.teams.awayTeam.winner){
                fixtureData!!.teams.awayTeam.name
            }else if (fixtureData!!.teams.homeTeam.winner){
                fixtureData!!.teams.homeTeam.name
            }else {
                getString(R.string.unknown)
            }

            Glide.with(this@FixturesDetailActivity)
                .load(statRequirement!!.leagueLogo)
                .placeholder(loadingDrawable1)
                .error(R.drawable.broken_image_icon)
                .into(ivLeagueLogoStatistics)

            Glide.with(this@FixturesDetailActivity)
                .load(fixtureData!!.teams.homeTeam.logo)
                .placeholder(loadingDrawable1)
                .error(R.drawable.broken_image_icon)
                .into(ivLivescoreHomeLogo)

            Glide.with(this@FixturesDetailActivity)
                .load(fixtureData!!.teams.awayTeam.logo)
                .placeholder(loadingDrawable1)
                .error(R.drawable.broken_image_icon)
                .into(ivLivescoreAwayLogo)
        }
    }

    private fun setProgressBar() {
        progressBar = AlertDialog.Builder(this).setCancelable(false).setView(R.layout.loading).create()
        progressBar.show()
        progressBar.window?.setLayout(400, 400)
        progressBar.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)
    }
}