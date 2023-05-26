package com.kevin.netkick.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kevin.netkick.R
import com.kevin.netkick.databinding.TopscorerItemBinding
import com.kevin.netkick.domain.entity.player.ResponseP
import com.kevin.netkick.presentation.PresentationUtils

class PlayersTopAdapter(private var dataList: MutableList<ResponseP>) :
    RecyclerView.Adapter<PlayersTopAdapter.PlayerViewHolder>() {
    private lateinit var context: Context

    inner class PlayerViewHolder(private val binding: TopscorerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: ResponseP) {


            binding.apply {
                Glide.with(itemView)
                    .load(data.players.photo)
                    .placeholder(PresentationUtils.loadingDrawableBar(context))
                    .error(R.drawable.broken_image_icon)
                    .into(ivPlayerPhoto)

                Glide.with(itemView)
                    .load(data.statistics[0].team.logo)
                    .placeholder(PresentationUtils.loadingDrawableBar(context))
                    .error(R.drawable.broken_image_icon)
                    .into(ivTeamLogoTop)

                tvPlayerName.text = data.players.name
                tvPlayerNationality.text = data.players.nationality
                tvPlayerPosition.text = data.statistics[0].games.position
                tvPlayerRating.text = if (data.statistics[0].games.rating.length > 3) {
                    data.statistics[0].games.rating.substring(0, 3)
                } else {
                    data.statistics[0].games.rating
                }
                tvPlayerAppearances.text = data.statistics[0].games.appearances.toString()
                tvPlayerGoals.text = data.statistics[0].goals.total.toString()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        context = parent.context
        val binding =
            TopscorerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(playerList: List<ResponseP>) {
        dataList.clear()
        dataList.addAll(playerList)
        notifyDataSetChanged()
    }
}