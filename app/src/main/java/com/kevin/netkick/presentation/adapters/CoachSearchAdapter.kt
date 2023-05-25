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
import com.kevin.netkick.domain.entity.coach.ResponseC
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.view.trophies.activity.coach.CoachDetailActivity

class CoachSearchAdapter(private var dataList: MutableList<ResponseC>) :
    RecyclerView.Adapter<CoachSearchAdapter.CoachViewHolder>() {
    private lateinit var context: Context

    inner class CoachViewHolder(private val binding: CoachItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: ResponseC) {
            val loadingDrawable1 = CircularProgressDrawable(context)
            loadingDrawable1.strokeWidth = 5f
            loadingDrawable1.centerRadius = 30f
            loadingDrawable1.setColorSchemeColors(Color.WHITE)
            loadingDrawable1.start()

            binding.apply {
                Glide.with(itemView)
                    .load(data.photo)
                    .placeholder(loadingDrawable1)
                    .error(R.drawable.broken_image_icon)
                    .into(ivCoachPhoto)

                tvCoachName.text = data.name
                tvCoachAge.text = data.age.toString()
                tvCoachNationality.text = data.nationality
                tvPlayerBdate.text = data.birth.date

                root.setOnClickListener {
                    val intent = Intent(context, CoachDetailActivity::class.java)
                    intent.putExtra(PresentationUtils.COACH_FULL_DATA, data)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachViewHolder {
        context = parent.context
        val binding = CoachItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoachViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: CoachViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(coachList: List<ResponseC>) {
        dataList.clear()
        dataList.addAll(coachList)
        notifyDataSetChanged()
    }
}