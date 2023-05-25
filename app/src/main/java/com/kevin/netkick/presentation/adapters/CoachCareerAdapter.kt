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
import com.kevin.netkick.databinding.CareerItemBinding
import com.kevin.netkick.domain.entity.coach.Career

class CoachCareerAdapter(private var dataList: MutableList<Career>) :
    RecyclerView.Adapter<CoachCareerAdapter.CoachViewHolder>() {
    private lateinit var context: Context

    inner class CoachViewHolder(private val binding: CareerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: Career) {
            val loadingDrawable1 = CircularProgressDrawable(context)
            loadingDrawable1.strokeWidth = 5f
            loadingDrawable1.centerRadius = 30f
            loadingDrawable1.setColorSchemeColors(Color.WHITE)
            loadingDrawable1.start()

            binding.apply {
                Glide.with(itemView)
                    .load(data.team.logo)
                    .placeholder(loadingDrawable1)
                    .error(R.drawable.broken_image_icon)
                    .into(ivTeamLogoCareer)

                tvEndCareer.text = data.end
                tvStartCareer.text = data.start
                tvTeamNameCareer.text = data.team.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachViewHolder {
        context = parent.context
        val binding = CareerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoachViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: CoachViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(careerList: List<Career>) {
        dataList.clear()
        dataList.addAll(careerList)
        notifyDataSetChanged()
    }
}