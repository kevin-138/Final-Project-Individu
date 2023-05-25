package com.kevin.netkick.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kevin.netkick.R
import com.kevin.netkick.databinding.LiveScoreItemBinding
import com.kevin.netkick.domain.entity.fixtures.ResponseF
import com.kevin.netkick.domain.entity.standings.substandings.Standings


class LiveScoreAdapter(private val dataList: MutableList<ResponseF>, private var dataEmpty: Boolean) :
    RecyclerView.Adapter<LiveScoreAdapter.LiveViewHolder>() {
    private lateinit var context: Context

    inner class LiveViewHolder(private val binding: LiveScoreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: ResponseF, context: Context) {

            val loadingDrawable1 = CircularProgressDrawable(context)
            loadingDrawable1.strokeWidth = 5f
            loadingDrawable1.centerRadius = 30f
            loadingDrawable1.setColorSchemeColors(Color.WHITE)
            loadingDrawable1.start()

            val loadingDrawable2 = CircularProgressDrawable(context)
            loadingDrawable2.strokeWidth = 5f
            loadingDrawable2.centerRadius = 30f
            loadingDrawable2.setColorSchemeColors(Color.WHITE)
            loadingDrawable2.start()

            changeVisibility(1)
            binding.apply {
                tvLeagueName.text = data.league.name
                tvStatusLive.text = data.fixture.status.long
                tvLiveScore.text = context.resources.getString(
                    R.string.goalsFixture,
                    data.goals.home,
                    data.goals.away
                )
                tvMatchOpponents.text = context.resources.getString(
                    R.string.vsFixture,
                    data.teams.homeTeam.name,
                    data.teams.awayTeam.name
                )
                tvTimeMatchStart.text = context.resources.getString(
                    R.string.DateLiveTime,
                    data.fixture.timezone,
                    data.fixture.date.substring(11, 16)
                )
                Glide.with(itemView)
                    .load(data.teams.homeTeam.logo)
                    .placeholder(loadingDrawable1)
                    .error(R.drawable.broken_image_icon)
                    .into(ivLivescoreHomeLogo)

                Glide.with(itemView)
                    .load(data.teams.awayTeam.logo)
                    .placeholder(loadingDrawable2)
                    .error(R.drawable.broken_image_icon)
                    .into(ivLivescoreAwayLogo)
            }
        }

        fun bindDataEmpty() {
            changeVisibility(2)
            binding.apply {
                tvLeagueName.text = context.getString(R.string.no_live)
            }
        }

        private fun changeVisibility(dataState: Int) {
            if (dataState == 1) {
                binding.apply {
                    tvDatabaseStatus.visibility = View.INVISIBLE
                    tvStatementNoData.visibility = View.INVISIBLE
                    tvStatusLive.visibility = View.VISIBLE
                    tvLiveScore.visibility = View.VISIBLE
                    tvMatchOpponents.visibility = View.VISIBLE
                    tvTimeMatchStart.visibility = View.VISIBLE
                    ivLivescoreHomeLogo.visibility = View.VISIBLE
                    ivLivescoreAwayLogo.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    tvDatabaseStatus.visibility = View.VISIBLE
                    tvStatementNoData.visibility = View.VISIBLE
                    tvStatusLive.visibility = View.INVISIBLE
                    tvLiveScore.visibility = View.INVISIBLE
                    tvMatchOpponents.visibility = View.INVISIBLE
                    tvTimeMatchStart.visibility = View.INVISIBLE
                    ivLivescoreHomeLogo.visibility = View.INVISIBLE
                    ivLivescoreAwayLogo.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveViewHolder {
        context = parent.context
        val binding = LiveScoreItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LiveViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (dataEmpty) {
            1
        } else {
            dataList.size
        }
    }

    override fun onBindViewHolder(holder: LiveScoreAdapter.LiveViewHolder, position: Int) {
        if (dataEmpty) {
            holder.bindDataEmpty()
        } else {
            holder.bindData(dataList[position], context)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(inputData: List<ResponseF>, empty:Boolean) {
        dataList.clear()
        dataList.addAll(inputData)
        dataEmpty = empty
        notifyDataSetChanged()
    }

}


