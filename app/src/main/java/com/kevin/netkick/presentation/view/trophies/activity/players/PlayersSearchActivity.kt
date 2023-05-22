package com.kevin.netkick.presentation.view.trophies.activity.players

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityPlayersSearchBinding
import com.kevin.netkick.domain.entity.teams.ResponseT
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.PlayerAdapter
import com.kevin.netkick.presentation.view.viewmodels.TrophiesViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class PlayersSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayersSearchBinding
    private lateinit var adapter: PlayerAdapter
    private lateinit var progressBar: AlertDialog
    private var teamData: ResponseT? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: TrophiesViewModel by viewModels {
        viewModelFactory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectIntoPlayersSearchActivity(this)
        super.onCreate(savedInstanceState)
        binding = ActivityPlayersSearchBinding.inflate(layoutInflater)
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
            setObserver()
            checkIntent()
        } else {
            PresentationUtils.networkDialog(this)
        }
    }

    private fun checkIntent() {
        teamData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(PresentationUtils.TEAM_SEARCH_DATA, ResponseT::class.java)
        } else {
            intent.getParcelableExtra(PresentationUtils.TEAM_SEARCH_DATA)
        }
        if (teamData != null) {
            setLayout()
            setSearchBar()
        }
    }

    private fun setLayout() {
        val loadingDrawable1 = CircularProgressDrawable(this)
        loadingDrawable1.strokeWidth = 5f
        loadingDrawable1.centerRadius = 30f
        loadingDrawable1.setColorSchemeColors(Color.WHITE)
        loadingDrawable1.start()

        binding.apply {
            Glide.with(this@PlayersSearchActivity)
                .load(teamData!!.team.logo)
                .placeholder(loadingDrawable1)
                .error(R.drawable.broken_image_icon)
                .into(ivTeamLogoTop)
        }
    }


    private fun setSearchBar() {
        binding.apply {
            svPlayer.setOnQueryTextListener(object : SearchView.OnQueryTextListener,android.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                        if (query.length < 4) {
                            Toast.makeText(this@PlayersSearchActivity,"Search Field must be at least 4 characters!",
                                Toast.LENGTH_SHORT).show()
                        }else{
                            setProgressBar()
                            viewModel.setPlayerSearchQuery(Pair(query,teamData!!.team.id))
                            Timer().schedule(2000L) {
                                progressBar.dismiss()
                            }
                    }
                    return false
                }

                override fun onQueryTextChange(inputTxt: String): Boolean {
                    return false
                }
            })
        }
    }

    private fun setProgressBar() {
        progressBar = AlertDialog.Builder(this).setCancelable(false).setView(R.layout.loading).create()
        progressBar.show()
        progressBar.window?.setLayout(400, 400)
        progressBar.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)
    }

    private fun setObserver() {
        viewModel.playerSearchResults.observe(this){
            adapter.addDataToList(it.response)
        }
    }

    private fun setupAdapter() {
        binding.apply {
            adapter = PlayerAdapter(mutableListOf())
            rvPlayerList.layoutManager = LinearLayoutManager(this@PlayersSearchActivity)
            rvPlayerList.adapter = adapter
        }
    }
}