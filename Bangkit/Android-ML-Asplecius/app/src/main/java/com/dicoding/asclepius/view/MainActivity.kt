package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.datastore.SettingPreferences
import com.dicoding.asclepius.data.local.datastore.datastore
import com.dicoding.asclepius.data.local.room.ResultHistory
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.utils.FIRST_CATEGORY_LABEL_EXTRA
import com.dicoding.asclepius.utils.FIRST_CATEGORY_SCORE_EXTRA
import com.dicoding.asclepius.utils.IMAGE_URI_EXTRA
import com.dicoding.asclepius.utils.SECOND_CATEGORY_LABEL_EXTRA
import com.dicoding.asclepius.utils.SECOND_CATEGORY_SCORE_EXTRA
import com.dicoding.asclepius.utils.SettingPrefViewModelFactory
import com.dicoding.asclepius.utils.Snackbars
import com.dicoding.asclepius.utils.Toaster
import com.dicoding.asclepius.utils.ViewModelFactory
import com.dicoding.asclepius.viewmodel.ResultHistoryViewModel
import com.dicoding.asclepius.viewmodel.SettingViewModel
import com.yalantis.ucrop.UCrop
import org.tensorflow.lite.support.label.Category
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var viewModel : ResultHistoryViewModel
    private lateinit var settingViewModel : SettingViewModel

    private var currentImageUri: Uri? = null
    private var keepOnSplashScreen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * Set the splash screen
         */
         installSplashScreen()
             .setKeepOnScreenCondition{keepOnSplashScreen}
        Handler(Looper.getMainLooper()).postDelayed(({ keepOnSplashScreen = false}), 3000)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this.applicationContext)
        viewModel = ViewModelProvider(this, factory)[ResultHistoryViewModel::class.java]

        val preferences = SettingPreferences.getInstance(application.datastore)
        val settingsFactory = SettingPrefViewModelFactory(preferences)
        settingViewModel = ViewModelProvider(this, settingsFactory)[SettingViewModel::class.java]

        setupTheme()

        with(binding) {
            galleryButton.setOnClickListener { startGallery() }
            analyzeButton.setOnClickListener {
                currentImageUri?.let {
                    analyzeImage()
                } ?: Toaster.show(this@MainActivity, getString(R.string.no_media))
            }

            topAppBar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.history_result -> {
                        val intent = Intent(this@MainActivity, HistoryResultActivity::class.java)
                        startActivity(intent)

                        true
                    }
                    R.id.settings -> {
                        val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
        }

    }

    private fun setupTheme() {
        settingViewModel.getThemeSettings().observe(this) {isDarkTheme ->
            if (isDarkTheme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri : Uri? ->
        if (uri != null) {
            Snackbars.show(binding.galleryButton, R.string.success_pick_media)
            cropImage(uri)
        } else {
            Snackbars.show(binding.galleryButton, R.string.no_media)
        }
    }


    private fun cropImage(uri: Uri) {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFile = File(filesDir, "Photos/$timestamp.jpg")
        if (imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdir()

        val imageUri = Uri.fromFile(imageFile)

        val uCrop = UCrop.of(uri, imageUri)
            .withAspectRatio(1f, 1f)

        uCrop.getIntent(this).apply {
            launcherUCrop.launch(this)
        }
    }

    private val launcherUCrop = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            currentImageUri = UCrop.getOutput(result.data!!)
            showImage()
        }

        if (result.resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(result.data!!)
            Toaster.show(this, cropError?.localizedMessage!!)
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {
        imageClassifierHelper = ImageClassifierHelper(context = this, classifierListener = object : ImageClassifierHelper.ClassifierListener {
            override fun onError(error: String) {
                Toaster.show(this@MainActivity, error)
            }

            override fun onResult(results: List<Classifications>?, inferenceTime: Long) {
                results?.let {classifications ->
                    if (classifications.isNotEmpty() and classifications[0].categories.isNotEmpty()) {
                        val sortedCategories = classifications[0].categories.sortedByDescending { it.score }

                        if (sortedCategories[0].label.isNotEmpty() and sortedCategories[1].label.isNotEmpty()) {
                            val resultHistory = ResultHistory(
                                imageUri = currentImageUri.toString(),
                                label = sortedCategories[0].label,
                                score = percentFormatter(sortedCategories[0].score)
                            )

                            viewModel.insertResultHistory(resultHistory)

                            moveToResult(sortedCategories)

                        } else {
                            Toaster.show(
                                this@MainActivity,
                                getString(R.string.something_wrong_try_again)
                            )
                        }

                    }
                }
            }

        })
        imageClassifierHelper.classifyStaticImage(currentImageUri!!)
    }

    private fun moveToResult(category: List<Category>) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra(IMAGE_URI_EXTRA, currentImageUri.toString())
            putExtra(FIRST_CATEGORY_LABEL_EXTRA, category[0].label)
            putExtra(FIRST_CATEGORY_SCORE_EXTRA, percentFormatter(category[0].score))
            putExtra(SECOND_CATEGORY_LABEL_EXTRA, category[1].label)
            putExtra(SECOND_CATEGORY_SCORE_EXTRA, percentFormatter(category[1].score))
        }
        startActivity(intent)
    }

    private fun percentFormatter(score: Float) : String =
        NumberFormat.getPercentInstance().format(score)

}