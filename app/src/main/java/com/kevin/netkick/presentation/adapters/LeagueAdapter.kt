package com.kevin.netkick.presentation.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kevin.netkick.R
import com.kevin.netkick.databinding.LeagueItemBinding
import com.kevin.netkick.domain.entity.league.ResponseL

class LeagueAdapter(private val dataList: MutableList<ResponseL>): RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder>() {
    private lateinit var context: Context
//    private lateinit var dataListMemory: MutableList<ResponseL>

    inner class LeagueViewHolder(private val binding: LeagueItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: ResponseL){
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
                tvLeagueType.text = data.league.type
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        context = parent.context
        val binding = LeagueItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LeagueViewHolder(binding)
    }

    override fun getItemCount(): Int {
         return dataList.size
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

//    fun addDataToList(leagueList: List<ResponseL>) {
//        dataList.clear()
//        dataList.addAll(leagueList)
//        dataListMemory.addAll(leagueList)
//        notifyItemRangeChanged(0,dataList.size)
//    }



}