package com.kevin.netkick.presentation.view.trophies.activity.players

import android.app.Activity
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
import com.kevin.netkick.databinding.ActivityPlayersAchievementBinding
import com.kevin.netkick.domain.entity.coach.ResponseC
import com.kevin.netkick.domain.entity.player.ResponseP
import com.kevin.netkick.domain.entity.player.subplayer.Player
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.CoachCareerAdapter
import com.kevin.netkick.presentation.adapters.PlayerCareerAdapter
import com.kevin.netkick.presentation.adapters.TrophyAdapter
import com.kevin.netkick.presentation.view.viewmodels.TrophiesViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class PlayersAchievementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayersAchievementBinding
    private lateinit var adapterTrophy: TrophyAdapter
    private lateinit var adapterCareeer: PlayerCareerAdapter
    private lateinit var progressBar: AlertDialog
    private var playerData: ResponseP? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: TrophiesViewModel by viewModels {
        viewModelFactory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectIntoPlayersAchievementActivity(this)
        super.onCreate(savedInstanceState)
        binding = ActivityPlayersAchievementBinding.inflate(layoutInflater)
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
        playerData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(PresentationUtils.PLAYER_FULL_DATA, ResponseP::class.java)
        } else {
            intent.getParcelableExtra(PresentationUtils.PLAYER_FULL_DATA)
        }

        if (playerData  != null) {
            setProgressBar()
            setLayout()
            getLiveData()
        }
    }

    private fun setupAdapter() {
        binding.apply {
            adapterCareeer = PlayerCareerAdapter(mutableListOf())
            rvPlayerCareer.layoutManager =
                LinearLayoutManager(this@PlayersAchievementActivity, LinearLayoutManager.HORIZONTAL, false)
            rvPlayerCareer.adapter =  adapterCareeer

            adapterTrophy = TrophyAdapter(mutableListOf())
            rvPlayerTrophies.layoutManager =
                LinearLayoutManager(this@PlayersAchievementActivity, LinearLayoutManager.HORIZONTAL, false)
            rvPlayerTrophies.adapter = adapterTrophy
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

            Glide.with(root)
                .load(playerData!!.players.photo)
                .placeholder(loadingDrawable1)
                .error(R.drawable.broken_image_icon)
                .into(ivPlayerPhoto)

            Glide.with(root)
                .load(playerData!!.statistics[0].team.logo)
                .placeholder(loadingDrawable1)
                .error(R.drawable.broken_image_icon)
                .into(ivInTeam)

            tvPlayerName.text = playerData!!.players.name
            tvTeamSelected.text = playerData!!.statistics[0].team.name

            tvPlayerFirstname.text = playerData!!.players.firstname
            tvPlayerLastname.text = playerData!!.players.lastname
            tvPlayerAge.text = playerData!!.players.age.toString()
            tvPlayerBirthDate.text = playerData!!.players.birth.date
            tvPlayerNationality.text = playerData!!.players.nationality
            tvPlayerHeight.text = playerData!!.players.height
            tvPlayerWeight.text = playerData!!.players.weight

        }

        adapterCareeer.addDataToList(playerData!!.statistics)
    }

    private fun getLiveData() {
        lifecycleScope.launch {
            viewModel.getPlayerTrophies(playerData!!.players.id)
            viewModel.playerTrophiesFlow.collectLatest {
                adapterTrophy.addDataToList(it.response)
                Timer().schedule(2000L) {
                    progressBar.dismiss()
                }
            }
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