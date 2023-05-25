package com.kevin.netkick.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevin.netkick.R
import com.kevin.netkick.databinding.TrophyItemBinding
import com.kevin.netkick.domain.entity.trophies.ResponseTrop
import com.kevin.netkick.presentation.PresentationUtils


class TrophyAdapter(private var dataList: MutableList<ResponseTrop>) :
    RecyclerView.Adapter<TrophyAdapter.TrophyViewHolder>() {
    private lateinit var context: Context

    inner class TrophyViewHolder(private val binding: TrophyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: ResponseTrop) {

            binding.apply {
                tvTrophiesCountry.text = data.country
                tvTrophiesLeague.text = data.league
                tvTrophiesPlace.text = data.place
                tvTrophiesSeason.text = data.season
                when (data.place) {
                    PresentationUtils.WINNER -> {
                        ivTrophyLogo.setImageResource(R.drawable.trophy_gold)
                    }
                    PresentationUtils.SECOND -> {
                        ivTrophyLogo.setImageResource(R.drawable.trophy_silver)
                    }
                    else -> {
                        ivTrophyLogo.setImageResource(R.drawable.trophy_bronze)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrophyAdapter.TrophyViewHolder {
        context = parent.context
        val binding = TrophyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrophyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: TrophyAdapter.TrophyViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(trophyList: List<ResponseTrop>) {
        dataList.clear()
        dataList.addAll(trophyList)
        notifyDataSetChanged()
    }
}