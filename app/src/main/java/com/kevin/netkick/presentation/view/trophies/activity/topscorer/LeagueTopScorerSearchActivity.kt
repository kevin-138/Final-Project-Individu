package com.kevin.netkick.presentation.view.trophies.activity.topscorer

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
import com.kevin.netkick.databinding.ActivityLeagueTopScorerSearchBinding
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.LeagueTopAdapter
import com.kevin.netkick.presentation.view.viewmodels.TrophiesViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class LeagueTopScorerSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeagueTopScorerSearchBinding
    private lateinit var adapter: LeagueTopAdapter
    private lateinit var progressBar: AlertDialog

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: TrophiesViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectIntoLeagueTopScorerSearchActivity(
            this
        )
        super.onCreate(savedInstanceState)
        binding = ActivityLeagueTopScorerSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        binding.ibBackButton.setOnClickListener {
            finish()
        }
        checkOnline()
    }

    private fun setObserver() {
        viewModel.searchResults.observe(this) {
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
            Timer().schedule(1000L) {
                progressBar.dismiss()
            }
        }
    }

    private fun layoutVisibility(isEmpty: Boolean) {
        binding.apply {
            when (isEmpty) {
                true -> {
                    tvNothingFoundTopsc.visibility = View.VISIBLE
                    rvLeagueTopsc.visibility = View.INVISIBLE
                }
                else -> {
                    tvNothingFoundTopsc.visibility = View.INVISIBLE
                    rvLeagueTopsc.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun setupAdapter() {
        binding.apply {
            adapter = LeagueTopAdapter(mutableListOf())
            rvLeagueTopsc.layoutManager = LinearLayoutManager(this@LeagueTopScorerSearchActivity)
            rvLeagueTopsc.adapter = adapter
        }
    }

    private fun setSearchBar() {
        binding.apply {
            svLeagueTopsc.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (query.length < 3) {
                        Toast.makeText(
                            this@LeagueTopScorerSearchActivity,
                            "Search Field must be at least 3 characters!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        setProgressBar()
                        viewModel.setSearchQuery(query)
                    }
                    return false
                }

                override fun onQueryTextChange(inputTxt: String): Boolean {
                    return false
                }
            })
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

    private fun setProgressBar() {
        progressBar =
            AlertDialog.Builder(this).setCancelable(false).setView(R.layout.loading).create()
        progressBar.show()
        progressBar.window?.setLayout(400, 400)
        progressBar.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)
    }
}