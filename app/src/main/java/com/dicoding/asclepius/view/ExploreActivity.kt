package com.dicoding.asclepius.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.remote.ArticlesItem
import com.dicoding.asclepius.databinding.ActivityExploreBinding
import com.dicoding.asclepius.view.adapter.ExploreAdapter
import com.dicoding.asclepius.view.viewModel.ExploreViewModel

class ExploreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExploreBinding
    private val exploreViewModel: ExploreViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        exploreViewModel.loadNews()
        exploreViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        exploreViewModel.articleData.observe(this) {
            setArticleData(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvExplore.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvExplore.addItemDecoration(itemDecoration)
    }

    private fun setArticleData(data: List<ArticlesItem?>?) {
        val adapter = ExploreAdapter()
        adapter.submitList(data)
        binding.rvExplore.adapter = adapter
        adapter.itemOnClickListener(
            object : ExploreAdapter.OnClickListener {
                override fun onItemClick(news: ArticlesItem?) {
                    showDetailArticle(news)
                }
            }
        )
    }

    private fun showDetailArticle(news: ArticlesItem?) {
        val intent = Intent(this@ExploreActivity, DetailArticle::class.java)
        intent.putExtra(DetailArticle.EXTRA_NEWS_TITLE, news?.title)
        intent.putExtra(DetailArticle.EXTRA_NEWS_DESCRIPTION, news?.description)
        intent.putExtra(DetailArticle.EXTRA_NEWS_IMAGE_LINK, news?.urlToImage)
        startActivity(intent)
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}