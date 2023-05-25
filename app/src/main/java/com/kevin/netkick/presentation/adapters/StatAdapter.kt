package com.kevin.netkick.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevin.netkick.databinding.StatItemBinding
import com.kevin.netkick.domain.entity.statistics.ResponseS
import com.kevin.netkick.domain.entity.statistics.Stats

class StatAdapter(private var dataList: MutableList<ResponseS>) :
    RecyclerView.Adapter<StatAdapter.ViewHolder>() {
    private lateinit var context: Context

    inner class ViewHolder(private val binding: StatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(home: Stats, away: Stats) {
            binding.apply {
                tvStTitle1.text = home.type


                val valueHome = if (home.value.contains('%')) {
                    home.value.substringBefore('%').toInt()
                } else if (home.value.contains('.')) {
                    home.value.substringBefore('.').toInt()
                } else {
                    home.value.toInt()
                }

                val valueAway = if (away.value.contains('%')) {
                    away.value.substringBefore('%').toInt()
                } else if (away.value.contains('.')) {
                    away.value.substringBefore('.').toInt()
                } else {
                    away.value.toInt()
                }

                val max = valueHome + valueAway

                tvAwaySog.text = if (away.value.contains('.')) {
                    valueAway.toString()
                } else {
                    away.value
                }
                tvHomeSog.text = if (home.value.contains('.')) {
                    valueHome.toString()
                } else {
                    home.value
                }

                pbSogAway.max = max
                pbSogHome.max = max

                pbSogHome.progress = valueHome
                pbSogAway.progress = valueAway
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = StatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (dataList.isEmpty()) {
            0
        } else {
            dataList[0].statistics.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(dataList[0].statistics[position], dataList[1].statistics[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(statisticList: List<ResponseS>) {
        dataList.clear()
        dataList.addAll(statisticList)
        notifyDataSetChanged()
    }
}