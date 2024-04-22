package com.dicoding.asclepius.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityDetailArticleBinding

class DetailArticle : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val titleNews = intent.getStringExtra(EXTRA_NEWS_TITLE)
        val descriptionNews = intent.getStringExtra(EXTRA_NEWS_DESCRIPTION)
        val imageUrl = intent.getStringExtra(EXTRA_NEWS_IMAGE_LINK)
        binding.apply {
            title.text = titleNews
           tvItemDescription.text = descriptionNews
            Glide.with(binding.root)
                .load(imageUrl)
                .placeholder(R.drawable.ic_place_holder)
                .into(image)
        }
    }


    companion object {
        const val EXTRA_NEWS_TITLE = "extra_news_title"
        const val EXTRA_NEWS_DESCRIPTION = "extra_news_description"
        const val EXTRA_NEWS_IMAGE_LINK = "extra_news_image_link"
    }
}