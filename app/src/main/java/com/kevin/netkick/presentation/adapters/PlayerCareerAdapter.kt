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
import com.kevin.netkick.databinding.PlayerCareerItemBinding
import com.kevin.netkick.domain.entity.player.substatistic.Statistic

class PlayerCareerAdapter(private var dataList: MutableList<Statistic>): RecyclerView.Adapter<PlayerCareerAdapter.CareerViewHolder>() {
    private lateinit var context: Context

    inner class CareerViewHolder(private val binding: PlayerCareerItemBinding): RecyclerView.ViewHolder(binding.root) {


        fun bindData(data: Statistic){
            val loadingDrawable1 = CircularProgressDrawable(context)
            loadingDrawable1.strokeWidth = 5f
            loadingDrawable1.centerRadius = 30f
            loadingDrawable1.setColorSchemeColors(Color.WHITE)
            loadingDrawable1.start()

            binding.apply {
                Glide.with(itemView)
                    .load(data.league.logo)
                    .placeholder(loadingDrawable1)
                    .error(R.drawable.broken_image_icon)
                    .into(ivLeagueLogoList)

                tvLeagueName.text = data.league.name
                tvLeagueSeason.text = data.league.season.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CareerViewHolder {
        context = parent.context
        val binding = PlayerCareerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CareerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: CareerViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(careerList: List<Statistic>) {
        dataList.clear()
        dataList.addAll(careerList)
        notifyDataSetChanged()
    }

}