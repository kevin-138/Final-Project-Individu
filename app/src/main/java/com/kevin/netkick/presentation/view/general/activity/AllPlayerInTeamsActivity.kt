package com.kevin.netkick.presentation.view.general.activity

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
import com.kevin.netkick.databinding.ActivityAllPlayerInTeamsBinding
import com.kevin.netkick.domain.entity.teams.ResponseT
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.PlayersPagingAdapter
import com.kevin.netkick.presentation.view.viewmodels.PopularTeamViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

@Suppress("DEPRECATION")
class AllPlayerInTeamsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllPlayerInTeamsBinding
    private lateinit var adapter: PlayersPagingAdapter
    private lateinit var progressBar: AlertDialog
    private var teamData: ResponseT? = null
    private var season: Int? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: PopularTeamViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectIntoAllPlayerInTeamsActivity(this)
        super.onCreate(savedInstanceState)
        binding = ActivityAllPlayerInTeamsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setProgressBar()
        binding.ibBackButton.setOnClickListener {
            finish()
        }
        setupAdapter()
        getTeamDataIntent()
        checkOnline()
    }


    private fun getTeamDataIntent() {
        teamData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(PresentationUtils.TEAM_FULL_DATA,ResponseT::class.java)
        }else{
            intent.getParcelableExtra(PresentationUtils.TEAM_FULL_DATA)
        }

        season =  intent.getIntExtra(PresentationUtils.TEAM_SEASON,0)

        if (teamData!=null){
            setTeamDisplay()
        }
    }

    private fun setTeamDisplay(){
        val loadingDrawable1 = CircularProgressDrawable(this)
        loadingDrawable1.strokeWidth = 5f
        loadingDrawable1.centerRadius = 30f
        loadingDrawable1.setColorSchemeColors(Color.WHITE)
        loadingDrawable1.start()

        binding.apply {
            tvTeamNamePlayer.text = teamData?.team?.name ?: "No Data"
            Glide.with(root)
                .load(teamData?.team?.logo ?: "")
                .placeholder(loadingDrawable1)
                .error(R.drawable.broken_image_icon)
                .into(ivTeamLogoDetail)
        }
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
            viewModel.getTeamPlayers(this, teamData!!.team.id,season!!)
                .collectLatest {
                    adapter.submitData(lifecycle,it)

                    Timer().schedule(2000L) {
                        progressBar.dismiss()
                    }
            }
        }
    }

}