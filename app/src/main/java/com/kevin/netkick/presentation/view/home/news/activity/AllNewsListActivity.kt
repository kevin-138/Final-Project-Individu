package com.kevin.netkick.presentation.view.home.news.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityAllNewsListBinding
import com.kevin.netkick.domain.entity.news.NewsResponse
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.NewsHeadlinePreviewAdapter
import com.kevin.netkick.presentation.view.viewmodels.MainViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllNewsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllNewsListBinding
    private lateinit var newsAdapter: NewsHeadlinePreviewAdapter
    private lateinit var progressBar:AlertDialog

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectIntoAllNewsListActivity(this)
        super.onCreate(savedInstanceState)
        binding = ActivityAllNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setProgressBar()
        setupNewsAdapter()
        checkOnline()

        binding.ibBackButton.setOnClickListener {
            finish()
        }
    }

    private fun setProgressBar() {
        progressBar = AlertDialog.Builder(this).setCancelable(false).setView(R.layout.loading).create()
    }

    private fun checkOnline() {
            val onlineCheck = PresentationUtils.isOnline(this)
            if (onlineCheck) {
                getNewsHeadline()
            } else {
                PresentationUtils.networkDialog(this)
            }
    }

    private fun setupNewsAdapter() {
        binding.apply {
            newsAdapter = NewsHeadlinePreviewAdapter(arrayListOf(), false)
            rvNewsHeadline.layoutManager = LinearLayoutManager(this@AllNewsListActivity)
            rvNewsHeadline.adapter = newsAdapter
        }
    }

    private fun getNewsHeadline() {
        progressBar.show()
        progressBar.window?.setLayout(400, 400)
        progressBar.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)
        lifecycleScope.launch {
            viewModel.getNewsHeadline()
            viewModel.newsHeadlineFlow.collectLatest {
                setupNewsLayout(it)
                progressBar.dismiss()
            }
        }
    }

    private fun setupNewsLayout(data: NewsResponse) {
        newsAdapter.addDataToList(data.articles)
    }

}
