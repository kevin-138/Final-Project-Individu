package com.kevin.netkick.presentation.view.explore.activity

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityFixturesBinding
import com.kevin.netkick.domain.entity.league.ResponseL
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.FixturesAdapter
import com.kevin.netkick.presentation.view.viewmodels.ExploreViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FixturesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFixturesBinding
    private lateinit var adapter: FixturesAdapter
    private lateinit var progressBar: AlertDialog
    private var leagueData: ResponseL? = null
    private var season: Int? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ExploreViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectIntoLeagueMatches(this)
        super.onCreate(savedInstanceState)
        binding = ActivityFixturesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        binding.ibBackButton.setOnClickListener {
            finish()
        }
        checkOnline()

    }

    private fun setupAdapter() {
        binding.apply {
            adapter = FixturesAdapter(mutableListOf())
            rvFixtures.layoutManager = LinearLayoutManager(this@FixturesActivity)
            rvFixtures.adapter = adapter
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
        leagueData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(PresentationUtils.LEAGUE_FULL_DATA, ResponseL::class.java)
        }else{
            intent.getParcelableExtra(PresentationUtils.LEAGUE_FULL_DATA)
        }
        season = intent.getIntExtra(PresentationUtils.LEAGUE_SEASON,0)

        if (leagueData != null){
            setObserver()
            getSpinnerSeason()
            setLayout(leagueData!!)
        }
    }

    private fun getSpinnerSeason() {
        lifecycleScope.launch {
            viewModel.getLeagueRoundsBySeason(leagueData!!.league.id,season!!)
            viewModel.leagueRounds.collectLatest {
                setSpinner(it.response)
//                progressBar.dismiss()
            }
        }
    }

    private fun setSpinner(rounds: List<String>) {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, rounds)
        binding.apply {
            spLeagueRound.adapter = arrayAdapter

            spLeagueRound.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    setProgressBar()
                    val roundSelected = rounds[position]
                    adapter.setSr(season!!,roundSelected)
                    adapter.setLe(leagueData!!.league.name,leagueData!!.league.logo)
                    getOnlineData(leagueData!!.league.id, season!! ,roundSelected)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    setProgressBar()
                    val roundSelected = rounds[0]
                    adapter.setSr(season!!,roundSelected)
                    adapter.setLe(leagueData!!.league.name,leagueData!!.league.logo)
                    getOnlineData(leagueData!!.league.id, season!! ,roundSelected)
                }
            }

        }
    }

    private fun getOnlineData(league:Int, season:Int, round:String) {
        viewModel.setFixtureQuery(Triple(league,season,round))
    }

    private fun setLayout(data:ResponseL) {
        val loadingDrawable1 = CircularProgressDrawable(this)
        loadingDrawable1.strokeWidth = 5f
        loadingDrawable1.centerRadius = 30f
        loadingDrawable1.setColorSchemeColors(Color.WHITE)
        loadingDrawable1.start()

        binding.apply {
            tvLeagueNameMatches.text = data.league.name
            tvLeagueSeason.text = season.toString()

            Glide.with(this@FixturesActivity)
                .load(data.league.logo)
                .placeholder(loadingDrawable1)
                .error(R.drawable.broken_image_icon)
                .into(ivLeagueLogoTopsc)

        }
    }

    private fun setProgressBar() {
        progressBar = AlertDialog.Builder(this).setCancelable(false).setView(R.layout.loading).create()
        progressBar.show()
        progressBar.window?.setLayout(400, 400)
        progressBar.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)
    }

    private fun setObserver() {
        viewModel.fixtureResult.observe(this){
            adapter.addDataToList(it.response)
            progressBar.dismiss()
        }
    }
}