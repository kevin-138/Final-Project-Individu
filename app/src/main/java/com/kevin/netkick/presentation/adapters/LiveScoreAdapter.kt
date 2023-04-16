package com.kevin.netkick.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kevin.netkick.R
import com.kevin.netkick.databinding.LiveScoreItemBinding
import com.kevin.netkick.domain.entity.fixtures.ResponseF


class LiveScoreAdapter(private val dataList: List<ResponseF>,val context: Context): RecyclerView.Adapter<LiveScoreAdapter.LiveViewHolder>() {

    class LiveViewHolder(private val binding: LiveScoreItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: ResponseF, context: Context) {
            binding.apply {
                tvLeagueName.text = data.league.name
                tvStatusLive.text = data.fixture.status.long
                tvLiveScore.text = context.resources.getString(R.string.goalsFixture, data.goals.home,data.goals.away)
                tvMatchOpponents.text = context.resources.getString(R.string.vsFixture, data.teams.homeTeam.name, data.teams.awayTeam.name)
                Glide.with(itemView).load(data.teams.homeTeam.logo).into(ivLivescoreHomeLogo)
                Glide.with(itemView).load(data.teams.awayTeam.logo).into(ivLivescoreAwayLogo)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveViewHolder {
        val binding = LiveScoreItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LiveViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: LiveViewHolder, position: Int) {
        holder.bindData(dataList[position],context)
    }
}


