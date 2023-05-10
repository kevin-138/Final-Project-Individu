package com.kevin.netkick.presentation.view.home.popularteams.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.databinding.ActivityPopularTeamsListBinding

class PopularTeamsListActivity : AppCompatActivity() {
    lateinit var binding: ActivityPopularTeamsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectInto(this)
        super.onCreate(savedInstanceState)
        binding = ActivityPopularTeamsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}