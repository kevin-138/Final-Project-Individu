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
import com.kevin.netkick.databinding.StandingListItemBinding
import com.kevin.netkick.databinding.StandingsHeaderBinding
import com.kevin.netkick.domain.entity.standings.Group
import com.kevin.netkick.domain.entity.standings.substandings.All
import com.kevin.netkick.domain.entity.standings.substandings.GoalsSt
import com.kevin.netkick.domain.entity.standings.substandings.Standings
import com.kevin.netkick.domain.entity.standings.substandings.TeamSt
import com.kevin.netkick.presentation.PresentationUtils

class LeagueStandingsAdapter(private var dataList: ArrayList<List<Standings>>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var context: Context
    private var currentGroup = 0

    fun next(){
        if (currentGroup != getGroupSize()-1){
            currentGroup  += 1
            setGroup(currentGroup)
        }
    }

    fun pref(){
        if (currentGroup != 0){
            currentGroup -= 1
            setGroup(currentGroup)
        }
    }


    inner class StandingsItemViewHolder(private val itemBinding: StandingListItemBinding):RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(data:Standings){
            val loadingDrawable1 = CircularProgressDrawable(context)
            loadingDrawable1.strokeWidth = 5f
            loadingDrawable1.centerRadius = 30f
            loadingDrawable1.setColorSchemeColors(Color.WHITE)
            loadingDrawable1.start()

            itemBinding.apply {
                Glide.with(itemView)
                    .load(data.team.logo)
                    .placeholder(loadingDrawable1)
                    .error(R.drawable.broken_image_icon)
                    .into(ivTeamLogoStandings)

                tvTeamNameStandings.text = data.team.name
                tvRank.text = data.rank.toString()
                tvPoints.text = data.points.toString()
                tvPlayed.text = data.all.played.toString()
                tvWins.text = data.all.win.toString()
                tvDraw.text = data.all.draw.toString()
                tvLose.text = data.all.lose.toString()
                tvGoalsa.text = data.all.goals.goalsAgainst.toString()
                tvGoalsf.text = data.all.goals.goalsFor.toString()
            }
        }
    }

    inner class StandingsHeaderViewHolder(private val headerBinding: StandingsHeaderBinding):RecyclerView.ViewHolder(headerBinding.root)

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> PresentationUtils.TYPE_HEADER
            else ->  PresentationUtils.TYPE_ITEM
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {
        context = parent.context
        return when(viewType){
            PresentationUtils.TYPE_HEADER -> StandingsHeaderViewHolder(StandingsHeaderBinding.inflate(
                LayoutInflater.from(context),parent,false))
            PresentationUtils.TYPE_ITEM -> StandingsItemViewHolder(StandingListItemBinding.inflate(
                LayoutInflater.from(context),parent,false))
            else -> throw IllegalArgumentException("Invalid View Type!")
        }

    }

    fun getGroupSize(): Int {
        return dataList.size
    }

    fun setGroup(group:Int){
        currentGroup = group
//        val newList = dataList[currentGroup] as ArrayList
        dataList[currentGroup].toMutableList().add(0, Standings(0, TeamSt(0,"",""),0,0,"","", All(0, GoalsSt(0,0),0,0,0)))
        notifyDataSetChanged()
    }

    fun getCurrentGroup():String{
        return currentGroup.toString()
    }

    override fun getItemCount(): Int {
            return dataList[currentGroup].size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is StandingsHeaderViewHolder -> {
//            ini bisa ga ada coba dicek dlu
            }
            is StandingsItemViewHolder -> {
                holder.bindData( dataList[currentGroup][position])
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(inputData:  ArrayList<List<Standings>>) {
        dataList.clear()
        dataList.addAll(inputData)
        currentGroup = 0
        notifyDataSetChanged()
    }

//    fun changeGroup(group:Int){
//
//    }

//    fun changeGroup(group: Int){
//        data clear
//        data add data.response[0].league.standings[group].group)
//        notifyDataSetChanged()
//    }

//    globalval


}