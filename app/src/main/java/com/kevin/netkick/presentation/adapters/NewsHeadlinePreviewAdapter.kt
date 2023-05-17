package com.kevin.netkick.presentation.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevin.netkick.databinding.NewsHeadlineItemBinding
import com.kevin.netkick.domain.entity.news.Article
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.view.home.news.activity.ArticleActivity

class NewsHeadlinePreviewAdapter(private val dataList: MutableList<Article>, private val dataEmpty: Boolean):RecyclerView.Adapter<NewsHeadlinePreviewAdapter.NewsViewHolder>() {
    lateinit var context: Context

    inner class NewsViewHolder(private val binding: NewsHeadlineItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: Article){
            changeVisibility(1)
            binding.apply {
                tvArticleAuthor.text = data.author
                tvNewsArticleTitle.text = data.title
                tvArticleDate.text = PresentationUtils.newsDateFormatter(data.publishedAt)
                btnNewsDetail.setOnClickListener {
                    val intent = Intent(context,ArticleActivity::class.java)
                    intent.putExtra(PresentationUtils.NEWS_URL,data.url)
                    context.startActivity(intent)
                }
            }
        }

        fun bindDataEmpty() {
            changeVisibility(2)
        }

        private fun changeVisibility(state: Int) {
            when(state){
                1 -> {
                    binding.apply {
                        tvNewsArticleTitle.visibility = View.VISIBLE
                        tvArticleAuthor.visibility = View.VISIBLE
                        tvNoHeadlineData.visibility = View.INVISIBLE
                        tvArticleDate.visibility = View.VISIBLE
                        btnNewsDetail.visibility = View.VISIBLE
                    }
                }
                2 -> {
                    binding.apply {
                        tvNewsArticleTitle.visibility = View.INVISIBLE
                        tvArticleAuthor.visibility = View.INVISIBLE
                        tvNoHeadlineData.visibility = View.VISIBLE
                        tvArticleDate.visibility = View.INVISIBLE
                        btnNewsDetail.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        context = parent.context
        val binding =
            NewsHeadlineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (dataEmpty){
            1
        }else{
            dataList.size
        }
    }

    override fun onBindViewHolder(holder: NewsHeadlinePreviewAdapter.NewsViewHolder, position: Int) {
        if (dataEmpty){
            holder.bindDataEmpty()
        } else {
            holder.bindData(dataList[position])
        }
    }
    fun addDataToList(articleList: List<Article>) {
        dataList.clear()
        dataList.addAll(articleList)
        notifyItemRangeChanged(0,dataList.size)
    }
}