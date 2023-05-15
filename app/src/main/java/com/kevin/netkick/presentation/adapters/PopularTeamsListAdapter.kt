package com.kevin.netkick.presentation.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kevin.netkick.databinding.TeamListItemBinding
import com.kevin.netkick.domain.entity.teams.ResponseT
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.view.general.activity.TeamDetailActivity

class PopularTeamsListAdapter(private val dataList: ArrayList<ResponseT>, private val season: Int): RecyclerView.Adapter<PopularTeamsListAdapter.TeamViewHolder>() {
    lateinit var context: Context

    inner class TeamViewHolder(private val binding: TeamListItemBinding): RecyclerView.ViewHolder(binding.root)  {

        fun bindData(data: ResponseT){
            val loadingDrawable1 = CircularProgressDrawable(context)
            loadingDrawable1.strokeWidth = 5f
            loadingDrawable1.centerRadius = 30f
            loadingDrawable1.setColorSchemeColors(Color.WHITE)
            loadingDrawable1.start()

            binding.apply {
                tvTeamName.text = data.team.name
                tvCountryOrigin.text = data.team.country
                Glide.with(itemView)
                    .load(data.team.logo)
                    .placeholder(loadingDrawable1)
                    .into(ivTeamLogoList)

                root.setOnClickListener {
                    val intent = Intent(context,TeamDetailActivity::class.java)
                    intent.putExtra(PresentationUtils.TEAM_ID,data.team.id)
                    intent.putExtra(PresentationUtils.TEAM_SEASON,season)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        context = parent.context
        val binding = TeamListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TeamViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size

    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    fun addDataToList(teamList: List<ResponseT>) {
        dataList.clear()
        dataList.addAll(teamList)
        notifyItemRangeChanged(0,dataList.size)
    }
}