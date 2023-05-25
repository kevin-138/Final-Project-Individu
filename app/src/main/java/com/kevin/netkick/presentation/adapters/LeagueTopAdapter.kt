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
import com.kevin.netkick.databinding.LeagueItemBinding
import com.kevin.netkick.domain.entity.league.ResponseL
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.view.trophies.activity.topscorer.LeagueTopScorerActivity

class LeagueTopAdapter(private var dataList: MutableList<ResponseL>) :
    RecyclerView.Adapter<LeagueTopAdapter.LeagueViewHolder>() {
    private lateinit var context: Context

    inner class LeagueViewHolder(private val binding: LeagueItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: ResponseL) {
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

                root.setOnClickListener {
                    val intent = Intent(context, LeagueTopScorerActivity::class.java)
                    intent.putExtra(PresentationUtils.LEAGUE_FULL_DATA, data)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeagueTopAdapter.LeagueViewHolder {
        context = parent.context
        val binding = LeagueItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeagueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeagueTopAdapter.LeagueViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(leagueList: List<ResponseL>) {
        dataList.clear()
        dataList.addAll(leagueList)
        notifyDataSetChanged()
    }
}

