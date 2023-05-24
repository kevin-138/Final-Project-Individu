package com.kevin.netkick.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kevin.netkick.R
import com.kevin.netkick.databinding.FixturesItemBinding
import com.kevin.netkick.databinding.LeagueItemBinding
import com.kevin.netkick.domain.entity.fixtures.ResponseF
import com.kevin.netkick.domain.entity.league.ResponseL
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.intentmodel.StatisticRequirement
import com.kevin.netkick.presentation.view.explore.activity.FixturesDetailActivity

class FixturesAdapter(private var dataList: MutableList<ResponseF>): RecyclerView.Adapter<FixturesAdapter.FixtureViewHolder>() {
    private lateinit var context: Context
    private var leagueLogo = ""
    private var leagueName = ""
    private var season = 0
    private var round = ""
    inner class FixtureViewHolder(private val binding: FixturesItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: ResponseF){
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
            binding.apply {
                tvMatchDate.text = data.fixture.date.substringBefore('T'," ")
                tvFixtureMatches.text = context.resources.getString(
                    R.string.goalsFixture,
                    data.goals.home,
                    data.goals.away
                )
                tvMatchOpponents.text = context.resources.getString(
                    R.string.vsFixture,
                    data.teams.homeTeam.name,
                    data.teams.awayTeam.name
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

                root.setOnClickListener {
                    val intent = Intent(context,FixturesDetailActivity::class.java)
                    intent.putExtra(PresentationUtils.FIXTURE_FULL_DATA,data)
                    intent.putExtra(PresentationUtils.FIXTURE_REQUIREMENT, StatisticRequirement(
                        leagueName = leagueName,
                        leagueLogo = leagueLogo,
                        season = season,
                        round = round
                    ))
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixtureViewHolder {
        context = parent.context
        val binding = FixturesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FixtureViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setSr(seasonInp:Int, roundInp:String){
        season = seasonInp
        round = roundInp
    }
    fun setLe(name:String, logo:String){
        leagueLogo = logo
        leagueName = name
    }

    override fun onBindViewHolder(holder: FixtureViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(fixtureList: List<ResponseF>) {
        dataList.clear()
        dataList.addAll(fixtureList)
        notifyDataSetChanged()
    }
}