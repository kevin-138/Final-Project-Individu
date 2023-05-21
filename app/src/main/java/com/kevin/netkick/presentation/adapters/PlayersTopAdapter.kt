package com.kevin.netkick.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kevin.netkick.R
import com.kevin.netkick.databinding.TopscorerItemBinding
import com.kevin.netkick.domain.entity.player.ResponseP

class PlayersTopAdapter(private var dataList: MutableList<ResponseP>): RecyclerView.Adapter<PlayersTopAdapter.PlayerViewHolder>() {
    private lateinit var context: Context

    inner class PlayerViewHolder(private val binding: TopscorerItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: ResponseP) {
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
                Glide.with(itemView)
                    .load(data.players.photo)
                    .placeholder(loadingDrawable1)
                    .error(R.drawable.broken_image_icon)
                    .into(ivPlayerPhoto)

                Glide.with(itemView)
                    .load(data.statistics[0].team.logo)
                    .placeholder(loadingDrawable2)
                    .error(R.drawable.broken_image_icon)
                    .into(ivTeamLogoTop)

                tvPlayerName.text = data.players.name
                tvPlayerNationality.text = data.players.nationality
                tvPlayerPosition.text = data.statistics[0].games.position
                tvPlayerRating.text = data.statistics[0].games.rating.substring(0,3)
                tvPlayerAppearances.text = data.statistics[0].games.appearances.toString()
                tvPlayerGoals.text = data.statistics[0].goals.total.toString()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        context = parent.context
        val binding = TopscorerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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