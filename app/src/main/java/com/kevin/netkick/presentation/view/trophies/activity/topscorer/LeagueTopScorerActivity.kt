package com.kevin.netkick.presentation.view.trophies.activity.topscorer

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
import com.kevin.netkick.databinding.ActivityLeagueTopScorerBinding
import com.kevin.netkick.domain.entity.league.ResponseL
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.PlayersTopAdapter
import com.kevin.netkick.presentation.view.viewmodels.TrophiesViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class LeagueTopScorerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeagueTopScorerBinding
    private lateinit var adapter: PlayersTopAdapter
    private lateinit var progressBar: AlertDialog
    private var leagueData: ResponseL? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: TrophiesViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectIntoLeagueTopScorerActivity(this)
        super.onCreate(savedInstanceState)
        binding = ActivityLeagueTopScorerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        binding.ibBackButton.setOnClickListener {
            finish()
        }
        checkOnline()
    }

    private fun setupAdapter() {
        binding.apply {
            adapter = PlayersTopAdapter(mutableListOf())
            rvTopScorer.layoutManager = LinearLayoutManager(this@LeagueTopScorerActivity)
            rvTopScorer.adapter = adapter
        }
    }

    private fun setProgressBar() {
        progressBar =
            AlertDialog.Builder(this).setCancelable(false).setView(R.layout.loading).create()
        progressBar.show()
        progressBar.window?.setLayout(400, 400)
        progressBar.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)
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
            this, com.bumptech.glide.R.layout.support_simple_spinner_dropdown_item,
            listSeason
        )
        binding.apply {
            spLeagueSeasonTopsc.adapter = arrayAdapter
            spLeagueSeasonTopsc.onItemSelectedListener = object :
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
                    if (leagueSeasonSelected[0].coverage.topScorers) {
                        noTopScorerData(false)
                        getOnlineData(leagueData.league.id, listSeason[position])
                    } else {
                        noTopScorerData(true)
                        progressBar.dismiss()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    return
                }
            }
        }
    }

    private fun getOnlineData(id: Int, season: Int) {
        viewModel.setTopScoreQuery(Pair(id, season))
    }

    private fun noTopScorerData(boolean: Boolean) {
        if (boolean) {
            binding.apply {
                tvNoScore.visibility = View.VISIBLE
                rvTopScorer.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                tvNoScore.visibility = View.INVISIBLE
                rvTopScorer.visibility = View.VISIBLE
            }
        }
    }


    private fun setObserver() {
        viewModel.topScoreResults.observe(this) {
            adapter.addDataToList(it.response)
            Timer().schedule(2000L) {
                progressBar.dismiss()
            }
        }

    }

    private fun setLayout(data: ResponseL) {
        val loadingDrawable1 = CircularProgressDrawable(this)
        loadingDrawable1.strokeWidth = 5f
        loadingDrawable1.centerRadius = 30f
        loadingDrawable1.setColorSchemeColors(Color.WHITE)
        loadingDrawable1.start()

        binding.apply {
            tvLeagueNameTopsc.text = data.league.name
            tvLeagueTypeTopsc.text = data.league.type

            Glide.with(this@LeagueTopScorerActivity)
                .load(data.league.logo)
                .placeholder(loadingDrawable1)
                .error(R.drawable.broken_image_icon)
                .into(ivLeagueLogoTopsc)
        }
    }


}