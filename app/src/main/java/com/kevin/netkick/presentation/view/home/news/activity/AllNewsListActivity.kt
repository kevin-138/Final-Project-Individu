package com.kevin.netkick.presentation.view.home.news.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.databinding.ActivityAllNewsListBinding

class AllNewsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllNewsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectInto(this)
        super.onCreate(savedInstanceState)
        binding = ActivityAllNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}