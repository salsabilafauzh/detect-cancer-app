package com.dicoding.asclepius.view


import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.local.entity.HistoryResult
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.view.viewModel.HistoryViewModel
import com.dicoding.asclepius.view.viewModel.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var historyViewModel: HistoryViewModel
    private var historyResult: HistoryResult? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        historyViewModel = obtainViewModel(this@ResultActivity)


        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        binding.apply {
            val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
            val scoreResult = intent.getStringExtra(EXTRA_RESULT_SCORE)
            val index = intent.getBooleanExtra(EXTRA_RESULT_INDEX, false)

            if (scoreResult != null && imageUri != null) {

                val scoreCalculate = scoreResult.toFloat() * 100
                score.text = "$scoreCalculate %"
                resultImage.setImageURI(imageUri)
                if (index) {
                    containerLabel.setBackgroundColor(GREEN)
                    resultText.text = "Cancer"
                } else {
                    containerLabel.setBackgroundColor(RED)
                    resultText.text = "Non - Cancer"
                }

                save.setOnClickListener {
                    val sdf = SimpleDateFormat("'Date\n'dd-MM-yyyy '\n\nand\n\nTime\n'HH:mm:ss z")
                    val currentDateAndTime = sdf.format(Date())
                    historyResult = HistoryResult(
                        isCancer = index,
                        score = scoreResult,
                        createdAt = currentDateAndTime.toString()
                    )
                    historyViewModel.insert(historyResult as HistoryResult)
                    finish()
                }
            }
        }
    }

    private fun obtainViewModel(resultActivity: ResultActivity): HistoryViewModel {
        val factory = ViewModelFactory.getInstance(resultActivity.application)
        return ViewModelProvider(resultActivity, factory)[HistoryViewModel::class.java]
    }

    companion object {
        const val EXTRA_IMAGE_URI = "image_uri"
        const val EXTRA_RESULT_SCORE = "extra_result_score"
        const val EXTRA_RESULT_INDEX = "extra_result_index"
    }

}