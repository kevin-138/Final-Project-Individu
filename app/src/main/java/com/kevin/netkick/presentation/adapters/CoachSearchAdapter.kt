package com.kevin.netkick.presentation.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevin.netkick.databinding.TopscorerItemBinding
import com.kevin.netkick.domain.entity.coach.ResponseC

class CoachSearchAdapter(private var dataList: MutableList<ResponseC>): RecyclerView.Adapter<CoachSearchAdapter.CoachViewHolder>() {
    private lateinit var context: Context

    inner class CoachViewHolder(private val binding: ): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CoachViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}