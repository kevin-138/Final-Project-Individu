package com.kevin.netkick.presentation.view.general.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityAllPlayerInTeamsBinding
import com.kevin.netkick.presentation.adapters.PlayersPagingAdapter

class AllPlayerInTeamsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllPlayerInTeamsBinding
    private lateinit var adapter: PlayersPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_player_in_teams)
    }
}