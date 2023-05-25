package com.kevin.netkick.presentation.view.explore.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityLeagueStandingsBinding
import com.kevin.netkick.domain.entity.league.ResponseL
import com.kevin.netkick.domain.entity.league.submembers.Season
import com.kevin.netkick.domain.entity.standings.substandings.Standings
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.LeagueStandingsAdapter
import com.kevin.netkick.presentation.view.viewmodels.ExploreViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import javax.inject.Inject

class LeagueStandingsActivity : AppCompatActivity() {
    lateinit var binding: ActivityLeagueStandingsBinding
    private lateinit var adapter: LeagueStandingsAdapter
    private lateinit var progressBar: AlertDialog
    private var leagueData: ResponseL? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ExploreViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectIntoLeagueStandingActivity(this)
        super.onCreate(savedInstanceState)
        binding = ActivityLeagueStandingsBinding.inflate(layoutInflater)
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
            setGroupButtons()
        } else {
            PresentationUtils.networkDialog(this)
        }
    }

    private fun checkIntent() {
        leagueData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(PresentationUtils.LEAGUE_FULL_DATA, ResponseL::class.java)
        } else {
            intent.getParcelableExtra(PresentationUtils.LEAGUE_FULL_DATA)
        }

        if (leagueData != null) {
            setObserver()
            setLayout(leagueData!!)
            setSpinner(leagueData!!)

        }
    }

    private fun setSpinner(leagueData: ResponseL) {
        val listSeason = leagueData.seasons.map {
            it.year
        }
        val arrayAdapter = ArrayAdapter(
            this,
            com.bumptech.glide.R.layout.support_simple_spinner_dropdown_item,
            listSeason
        )
        binding.apply {
            spLeagueSeason.adapter = arrayAdapter

            spLeagueSeason.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    setProgressBar()
                    val leagueSeasonSelected = leagueData.seasons.filter {
                        it.year == listSeason[position]
                    }

                    adapter.setSeason(leagueSeasonSelected[0].year)
                    changeMatchesButtons(leagueSeasonSelected[0].coverage.fixtures.events)
                    setMatchesButton(leagueSeasonSelected[0])

                    if (leagueSeasonSelected[0].coverage.standings) {
                        noStandingsData(false)
                        getOnlineData(leagueData.league.id, listSeason[position])
                    } else {
                        noStandingsData(true)
                        progressBar.dismiss()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    val leagueSeasonDefault = leagueData.seasons.filter {
                        it.year == listSeason[0]
                    }
                    adapter.setSeason(leagueSeasonDefault[0].year)
                    changeMatchesButtons(leagueSeasonDefault[0].coverage.fixtures.events)
                    setMatchesButton(leagueSeasonDefault[0])

                    if (leagueSeasonDefault[0].coverage.standings) {
                        noStandingsData(false)
                        getOnlineData(leagueData.league.id, listSeason[0])
                    } else {
                        noStandingsData(true)
                        progressBar.dismiss()
                    }
                }
            }

        }
    }

    fun changeMatchesButtons(available: Boolean) {
        binding.apply {
            btMatchesLeague.visibility = if (available) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun setGroupButtons() {
        binding.apply {
            ibGroupNext.setOnClickListener {
                adapter.next()
                tvLeagueGroupTitle.text = getString(R.string.group, adapter.getCurrentGroup())
            }
            ibGroupPrev.setOnClickListener {
                adapter.pref()
                tvLeagueGroupTitle.text = getString(R.string.group, adapter.getCurrentGroup())
            }
        }
    }

    private fun setMatchesButton(season: Season) {
        binding.apply {
            btMatchesLeague.setOnClickListener {
                val intentMatches =
                    Intent(this@LeagueStandingsActivity, FixturesActivity::class.java)
                intentMatches.putExtra(PresentationUtils.LEAGUE_FULL_DATA, leagueData)
                intentMatches.putExtra(PresentationUtils.LEAGUE_SEASON, season)
                startActivity(intentMatches)
            }
        }
    }

    private fun noStandingsData(boolean: Boolean) {
        if (boolean) {
            binding.apply {
                tvNoStandings.visibility = View.VISIBLE
                hsvStandings.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                tvNoStandings.visibility = View.INVISIBLE
                hsvStandings.visibility = View.VISIBLE
            }
        }
    }

    private fun getOnlineData(id: Int, season: Int) {
        viewModel.setSearchQuery(Pair(id, season))
    }

    private fun setLayout(data: ResponseL) {
        val loadingDrawable1 = CircularProgressDrawable(this)
        loadingDrawable1.strokeWidth = 5f
        loadingDrawable1.centerRadius = 30f
        loadingDrawable1.setColorSchemeColors(Color.WHITE)
        loadingDrawable1.start()

        binding.apply {
            tvLeagueName.text = data.league.name
            tvLeagueType.text = data.league.type

            Glide.with(this@LeagueStandingsActivity)
                .load(data.league.logo)
                .placeholder(loadingDrawable1)
                .error(R.drawable.broken_image_icon)
                .into(ivLeagueLogoStandings)

        }
    }

    private fun setupAdapter() {
        binding.apply {
            adapter = LeagueStandingsAdapter(arrayListOf(listOf()))
            rvStandings.layoutManager = LinearLayoutManager(this@LeagueStandingsActivity)
            rvStandings.adapter = adapter
            tvLeagueGroupTitle.text = getString(R.string.group, "1")
        }
    }

    private fun setObserver() {
        viewModel.standingResults.observe(this) {
            adapter.addDataToList(it.response[0].league.standings as ArrayList<List<Standings>>)
            progressBar.dismiss()
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