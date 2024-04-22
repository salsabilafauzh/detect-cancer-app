package com.dicoding.asclepius.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.remote.ArticlesItem
import com.dicoding.asclepius.databinding.ItemArticleBinding

class ExploreAdapter : ListAdapter<ArticlesItem, ExploreAdapter.ArticleViewHolder>(DIFF_CALLBACK) {
    private lateinit var itemOnClickListener: OnClickListener
    fun itemOnClickListener(clickListener: OnClickListener) {
        this.itemOnClickListener = clickListener
    }

    interface OnClickListener {
        fun onItemClick(news: ArticlesItem?)
    }

    class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: ArticlesItem?) {
            if (news?.author != null) {
                binding.apply {
                    tvItemTitle.text = news.title
                    tvItemDescription.text = news.description
                    Glide.with(root)
                        .load(news.urlToImage)
                        .placeholder(R.drawable.ic_place_holder)
                        .into(imgItemPhoto)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
        holder.itemView.setOnClickListener {
            itemOnClickListener.onItemClick(news)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(
                oldItem: ArticlesItem,
                newItem: ArticlesItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ArticlesItem,
                newItem: ArticlesItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}