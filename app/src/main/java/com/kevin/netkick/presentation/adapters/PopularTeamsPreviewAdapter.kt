package com.kevin.netkick.presentation.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kevin.netkick.databinding.PopularTeamItemBinding
import com.kevin.netkick.domain.entity.teams.ResponseT
import com.kevin.netkick.presentation.view.home.popularteams.activity.PopularTeamsDetailActivity

class PopularTeamsPreviewAdapter(private val dataList:List<ResponseT>):RecyclerView.Adapter<PopularTeamsPreviewAdapter.TeamsViewHolder>() {
    lateinit var context: Context

    inner class TeamsViewHolder(private val binding: PopularTeamItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data:ResponseT){
            val loadingDrawable1 = CircularProgressDrawable(context)
            loadingDrawable1.strokeWidth = 5f
            loadingDrawable1.centerRadius = 30f
            loadingDrawable1.setColorSchemeColors(Color.WHITE)
            loadingDrawable1.start()

            binding.apply {
                Glide.with(itemView)
                    .load(data.team.logo)
                    .placeholder(loadingDrawable1)
                    .into(ivPopularTeamLogo)

                binding.root.setOnClickListener {
                    val intent = Intent(context,PopularTeamsDetailActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        context = parent.context
        val binding =
            PopularTeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamsViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

}