package com.kevin.netkick.presentation.view.general.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityTeamDetailBinding
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.view.viewmodels.PopularTeamViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        (application as NetkickApplication).appComponent.injectIntoTeamDetailActivity(this)
        super.onCreate(savedInstanceState)
        binding = ActivityTeamDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setProgressBar()
        binding.ibBackButton.setOnClickListener {
            finish()
        }
        checkOnline()
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
            setupViews()
        } else {
            PresentationUtils.networkDialog(this)
        }
    }

    private fun setupViews() {

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

        if (intent!=null){
            lifecycleScope.launch {
                viewModel.getPopularTeamDetail(intent.getIntExtra(PresentationUtils.TEAM_ID,0))
                viewModel.detailTeamsFlow.collectLatest {
                    val dataTeam = it.response[0]
                    binding.apply {
                        Glide.with(binding.root)
                            .load(dataTeam.venue.image)
                            .placeholder(loadingDrawable1)
                            .error(R.drawable.broken_image_icon)
                            .into( ivVenueStadium)

                        Glide.with(binding.root)
                            .load(dataTeam.team.logo)
                            .placeholder(loadingDrawable2)
                            .error(R.drawable.broken_image_icon)
                            .into(ivTeamLogoDetail)

                        tvTeamDetailName.text = dataTeam.team.name
                        tvTeamDetailCode.text = getString(R.string.country_code,dataTeam.team.code)
                        tvTeamDetailDate.text = getString(R.string.est_year,dataTeam.team.founded)
                        tvTeamCountryCode.text = dataTeam.team.country
                        tvTeamVenueName.text = dataTeam.venue.name
                        tvTeamVenueCity.text = dataTeam.venue.city
                        tvTeamVenueSurface.text = dataTeam.venue.surface
                        tvTeamVenueCapacity.text = dataTeam.venue.capacity.toString()
                        tvTeamVenueAddress.text = dataTeam.venue.address

                        binding.btnSeeAllPlayers.setOnClickListener {
                            val intentPlayers = Intent(this@TeamDetailActivity,AllPlayerInTeamsActivity::class.java)
                            intentPlayers.putExtra(PresentationUtils.TEAM_FULL_DATA,dataTeam)
                            this@TeamDetailActivity.startActivity(intentPlayers)
                        }
                        progressBar.dismiss()
                    }
                }
            }
        }
    }
}