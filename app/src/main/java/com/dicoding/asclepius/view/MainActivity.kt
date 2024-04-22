package com.dicoding.asclepius.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.yalantis.ucrop.UCrop
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentImageUri: Uri? = null
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            galleryButton.setOnClickListener {
                startGallery()
            }
            analyzeButton.setOnClickListener {
                currentImageUri?.let {
                    analyzeImage(it)
                }
            }

            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.item_1 -> {
                        startActivity(Intent(this@MainActivity, ExploreActivity::class.java))
                        true
                    }

                    R.id.item_2 -> {
                        startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
                        true
                    }

                    else -> false
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showImage()
        if (currentImageUri != null) {
            binding.analyzeButton.visibility = View.VISIBLE
        }
    }

    override fun onStop() {
        super.onStop()
        currentImageUri = null
        binding.previewImageView.setImageResource(R.drawable.ic_place_holder)
        binding.analyzeButton.visibility = View.INVISIBLE
    }


    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }


    private fun showImage() {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
        currentImageUri.let {
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage(uri: Uri) {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    runOnUiThread {
                        showToast(error)
                    }
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    runOnUiThread {
                        results?.let {
                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                Log.d(TAG, "results: $it")
                                moveToResult(it)
                            }
                        }
                    }
                }

            }
        )
        imageClassifierHelper.classifyStaticImage(uri)
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            startUCropActivity(uri)
        } else {
            showToast("No media selected")
            Log.d("Photo Picker", "No media selected")
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UCrop.REQUEST_CROP && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val resultUri = UCrop.getOutput(data)
                resultUri?.let {
                    currentImageUri = it
                }
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            cropError?.let {
                showToast("Crop error: ${it.localizedMessage}")
                Log.e("UCrop", "Crop error: ${it.localizedMessage}")
            }
        }
    }

    private fun startUCropActivity(uri: Uri) {
        val destinationUri = Uri.fromFile(File(cacheDir, "cropped_image"))
        UCrop.of(uri, destinationUri)
            .withAspectRatio(1f, 1f)
            .start(this)
    }

    private fun moveToResult(results: List<Classifications>) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, currentImageUri.toString())
        val index = results[0].categories[0].index.toString() == "1"
        val score = results[0].categories[0].score.toString()
        intent.putExtra(ResultActivity.EXTRA_RESULT_INDEX, index)
        intent.putExtra(ResultActivity.EXTRA_RESULT_SCORE, score)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}