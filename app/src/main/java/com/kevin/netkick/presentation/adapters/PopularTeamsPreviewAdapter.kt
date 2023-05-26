package com.kevin.netkick.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kevin.netkick.R
import com.kevin.netkick.databinding.PopularTeamItemBinding
import com.kevin.netkick.domain.entity.teams.ResponseT
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.view.general.activity.TeamDetailActivity

class PopularTeamsPreviewAdapter(private val dataList: MutableList<ResponseT>, private var season: Int) :
    RecyclerView.Adapter<PopularTeamsPreviewAdapter.TeamsViewHolder>() {
    private lateinit var context: Context

    inner class TeamsViewHolder(private val binding: PopularTeamItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: ResponseT) {

            binding.apply {
                Glide.with(itemView)
                    .load(data.team.logo)
                    .placeholder(PresentationUtils.loadingDrawableBar(context))
                    .error(R.drawable.broken_image_icon)
                    .into(ivPopularTeamLogo)

                binding.root.setOnClickListener {
                    val intent = Intent(context, TeamDetailActivity::class.java)
                    intent.putExtra(PresentationUtils.TEAM_ID, data.team.id)
                    intent.putExtra(PresentationUtils.TEAM_SEASON, season)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        context = parent.context
        val binding =
            PopularTeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamsViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(inputData: List<ResponseT>, inputSeason:Int) {
        dataList.clear()
        dataList.addAll(inputData)
        season = inputSeason
        notifyDataSetChanged()
    }


}