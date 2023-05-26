package com.kevin.netkick.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kevin.netkick.R
import com.kevin.netkick.databinding.TeamListItemBinding
import com.kevin.netkick.domain.entity.teams.ResponseT
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.view.trophies.activity.players.PlayersSearchActivity

class TeamSearchAdapter(private val dataList: MutableList<ResponseT>) :
    RecyclerView.Adapter<TeamSearchAdapter.TeamViewHolder>() {
    private lateinit var context: Context

    inner class TeamViewHolder(private val binding: TeamListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: ResponseT) {

            binding.apply {
                tvTeamName.text = data.team.name
                tvCountryOrigin.text = data.team.country
                Glide.with(itemView)
                    .load(data.team.logo)
                    .placeholder(PresentationUtils.loadingDrawableBar(context))
                    .error(R.drawable.broken_image_icon)
                    .into(ivTeamLogoList)

                root.setOnClickListener {
                    val intent = Intent(context, PlayersSearchActivity::class.java)
                    intent.putExtra(PresentationUtils.TEAM_SEARCH_DATA, data)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        context = parent.context
        val binding =
            TeamListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(teamList: List<ResponseT>) {
        dataList.clear()
        dataList.addAll(teamList)
        notifyDataSetChanged()
    }
}