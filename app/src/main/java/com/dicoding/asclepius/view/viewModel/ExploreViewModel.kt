package com.dicoding.asclepius.view.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.remote.ApiConfig
import com.dicoding.asclepius.data.remote.ArticlesItem
import com.dicoding.asclepius.data.remote.ArticlesResponse
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class ExploreViewModel : ViewModel() {
    private val apiService = ApiConfig.getApiService()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _articleData: MutableLiveData<List<ArticlesItem?>?> = MutableLiveData()
    val articleData: LiveData<List<ArticlesItem?>?> = _articleData

    fun loadNews() {
        _isLoading.value = true
        try {
            val client = apiService.getArticle()
            client.enqueue(
                object : retrofit2.Callback<ArticlesResponse> {
                    override fun onResponse(
                        call: Call<ArticlesResponse>,
                        response: Response<ArticlesResponse>
                    ) {
                       _isLoading.value = false
                        if(response.isSuccessful){
                            response.body().let {
                                _articleData.value = it?.articles
                            }
                        }
                    }

                    override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                        _isLoading.value = false
                        Log.e(TAG, "OnFailure: ${t.message.toString()}")
                    }

                }
            )
        } catch (e: IOException) {
            Log.e(TAG, "OnFailure: ${e.message.toString()}")
        }
    }


    companion object {
        const val TAG = "ExploreViewModel"
    }
}