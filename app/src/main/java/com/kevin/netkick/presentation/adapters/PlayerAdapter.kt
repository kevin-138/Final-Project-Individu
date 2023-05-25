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
import com.kevin.netkick.databinding.CoachItemBinding
import com.kevin.netkick.domain.entity.player.ResponseP
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.view.trophies.activity.players.PlayersAchievementActivity

class PlayerAdapter(private var dataList: MutableList<ResponseP>) :
    RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {
    private lateinit var context: Context

    inner class PlayerViewHolder(private val binding: CoachItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: ResponseP) {
            val loadingDrawable1 = CircularProgressDrawable(context)
            loadingDrawable1.strokeWidth = 5f
            loadingDrawable1.centerRadius = 30f
            loadingDrawable1.setColorSchemeColors(Color.WHITE)
            loadingDrawable1.start()

            binding.apply {
                Glide.with(itemView)
                    .load(data.players.photo)
                    .placeholder(loadingDrawable1)
                    .error(R.drawable.broken_image_icon)
                    .into(ivCoachPhoto)

                tvCoachName.text = data.players.name
                tvCoachAge.text = data.players.age.toString()
                tvCoachNationality.text = data.players.nationality
                tvPlayerBdate.text = data.players.birth.date

                root.setOnClickListener {
                    val intent = Intent(context, PlayersAchievementActivity::class.java)
                    intent.putExtra(PresentationUtils.PLAYER_FULL_DATA, data)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        context = parent.context
        val binding = CoachItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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