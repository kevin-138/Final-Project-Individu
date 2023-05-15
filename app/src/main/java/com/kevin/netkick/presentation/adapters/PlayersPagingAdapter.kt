package com.kevin.netkick.presentation.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kevin.netkick.databinding.PlayerTeamItemBinding
import com.kevin.netkick.domain.entity.player.ResponseP

class PlayersPagingAdapter: PagingDataAdapter<ResponseP, PlayersPagingAdapter.PlayerViewHolder>(DiffCallback) {
    private lateinit var context: Context
    class PlayerViewHolder(val binding: PlayerTeamItemBinding):ViewHolder(binding.root)

    companion object {

        object DiffCallback : DiffUtil.ItemCallback<ResponseP>() {
            override fun areItemsTheSame(
                oldItem: ResponseP,
                newItem: ResponseP
            ): Boolean {
                return (oldItem == newItem)
            }

            override fun areContentsTheSame(
                oldItem: ResponseP,
                newItem: ResponseP
            ) = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val loadingDrawable1 = CircularProgressDrawable(context)
        loadingDrawable1.strokeWidth = 5f
        loadingDrawable1.centerRadius = 30f
        loadingDrawable1.setColorSchemeColors(Color.WHITE)
        loadingDrawable1.start()

        val data = getItem(position) ?: return
        holder.binding.apply {
            tvPlayerName.text = data.players.name
            tvPlayerNationality.text = data.players.nationality
            tvPlayerAge.text = data.players.age.toString()
            tvPlayerBdate.text = data.players.birth.date
            tvPlayerWeight.text = data.players.weight
            tvPlayerHeight.text = data.players.height

            Glide.with(root)
                .load(data.players.photo)
                .placeholder(loadingDrawable1)
                .into(ivPlayerPhoto)
            
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        context = parent.context
        return PlayerViewHolder(PlayerTeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false ))
    }


}