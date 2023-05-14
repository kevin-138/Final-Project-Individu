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
import com.kevin.netkick.databinding.CountryItemBinding
import com.kevin.netkick.domain.entity.country.CountryC
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.view.general.activity.LeagueSearchActivity

class CountriesAdapter(private var dataList: ArrayList<CountryC>):RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {
    private lateinit var dataListMemory: MutableList<CountryC>
    lateinit var context: Context

    inner class CountryViewHolder(private val binding: CountryItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bindData(data:CountryC){
            val loadingDrawable1 = CircularProgressDrawable(context)
            loadingDrawable1.strokeWidth = 5f
            loadingDrawable1.centerRadius = 30f
            loadingDrawable1.setColorSchemeColors(Color.WHITE)
            loadingDrawable1.start()

            binding.apply {

                tvCountryName.text = data.name

            Glide.with(itemView)
                .load(data.flag)
                .placeholder(loadingDrawable1)
                .into(ivCountryFlag)

                root.setOnClickListener {
                    val intent = Intent(context,LeagueSearchActivity::class.java)
                    intent.putExtra(PresentationUtils.COUNTRY_CODE,data.code)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        context = parent.context
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterlist: ArrayList<CountryC>){

        dataList = filterlist
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CountriesAdapter.CountryViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }


}