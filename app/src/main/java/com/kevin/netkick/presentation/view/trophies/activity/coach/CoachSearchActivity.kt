package com.kevin.netkick.presentation.view.trophies.activity.coach

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityCoachSearchBinding
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.CoachSearchAdapter
import com.kevin.netkick.presentation.view.viewmodels.TrophiesViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class CoachSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoachSearchBinding
    private lateinit var adapter: CoachSearchAdapter
    private lateinit var progressBar: AlertDialog

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: TrophiesViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectIntoCoachSearchActivity(this)
        super.onCreate(savedInstanceState)
        binding = ActivityCoachSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        binding.ibBackButton.setOnClickListener {
            finish()
        }
        checkOnline()
    }

    private fun setupAdapter() {
        binding.apply {
            adapter = CoachSearchAdapter(mutableListOf())
            rvCoach.layoutManager = LinearLayoutManager(this@CoachSearchActivity)
            rvCoach.adapter = adapter
        }
    }

    private fun setObserver() {
        viewModel.coachSearchResults.observe(this) {
            if (it.error.isNotBlank()) {
                PresentationUtils.errorToast(this,it.error)
            } else {
                if (it.response.isEmpty()) {
                    layoutVisibility(true)
                } else {
                    layoutVisibility(false)
                    adapter.addDataToList(it.response)
                }
            }
        }
    }

    private fun layoutVisibility(isEmpty: Boolean) {
        binding.apply {
            when (isEmpty) {
                true -> {
                    tvNothingFoundTopsc.visibility = View.VISIBLE
                    rvCoach.visibility = View.INVISIBLE
                }
                else -> {
                    tvNothingFoundTopsc.visibility = View.INVISIBLE
                    rvCoach.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun checkOnline() {
        val onlineCheck = PresentationUtils.isOnline(this)
        if (onlineCheck) {
            setObserver()
            setSearchBar()
        } else {
            PresentationUtils.networkDialog(this)
        }
    }

    private fun setSearchBar() {
        binding.apply {
            svCoach.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (query.length < 4) {
                        Toast.makeText(
                            this@CoachSearchActivity, "Search Field must be at least 3 characters!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        setProgressBar()
                        viewModel.setCoachSearchQuery(query)
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
        progressBar =
            AlertDialog.Builder(this).setCancelable(false).setView(R.layout.loading).create()
        progressBar.show()
        progressBar.window?.setLayout(400, 400)
        progressBar.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)
    }
}