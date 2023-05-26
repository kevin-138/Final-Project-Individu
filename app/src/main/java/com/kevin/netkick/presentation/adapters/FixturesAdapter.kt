package com.kevin.netkick.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kevin.netkick.R
import com.kevin.netkick.databinding.FixturesItemBinding
import com.kevin.netkick.domain.entity.fixtures.ResponseF
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.intentmodel.StatisticRequirement
import com.kevin.netkick.presentation.view.explore.activity.FixturesDetailActivity

class FixturesAdapter(private var dataList: MutableList<ResponseF>) :
    RecyclerView.Adapter<FixturesAdapter.FixtureViewHolder>() {
    private lateinit var context: Context
    private var leagueLogo = ""
    private var leagueName = ""
    private var season = 0
    private var round = ""
    private var coverage = false

    inner class FixtureViewHolder(private val binding: FixturesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: ResponseF) {

            binding.apply {
                tvMatchDate.text = data.fixture.date.substringBefore('T', " ")
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
                    .placeholder(PresentationUtils.loadingDrawableBar(context))
                    .error(R.drawable.broken_image_icon)
                    .into(ivLivescoreHomeLogo)

                Glide.with(itemView)
                    .load(data.teams.awayTeam.logo)
                    .placeholder(PresentationUtils.loadingDrawableBar(context))
                    .error(R.drawable.broken_image_icon)
                    .into(ivLivescoreAwayLogo)


                    root.setOnClickListener {
                        if (coverage) {
                        val intent = Intent(context, FixturesDetailActivity::class.java)
                        intent.putExtra(PresentationUtils.FIXTURE_FULL_DATA, data)
                        intent.putExtra(
                            PresentationUtils.FIXTURE_REQUIREMENT, StatisticRequirement(
                                leagueName = leagueName,
                                leagueLogo = leagueLogo,
                                season = season,
                                round = round
                            )
                        )
                        context.startActivity(intent)
                    }else{
                            PresentationUtils.errorToast(context,"Statistic Not Available")
                        }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixtureViewHolder {
        context = parent.context
        val binding =
            FixturesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FixtureViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setCv(coverageIntent: Boolean) {
        coverage = coverageIntent
    }

    fun setSr(seasonInp: Int, roundInp: String) {
        season = seasonInp
        round = roundInp
    }

    fun setLe(name: String, logo: String) {
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