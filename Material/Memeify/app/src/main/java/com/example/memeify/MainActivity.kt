package com.example.memeify

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.memeify.databinding.ActivityMainBinding
import com.example.memeify.utils.BITMAP_HEIGHT
import com.example.memeify.utils.BITMAP_WIDTH
import com.example.memeify.utils.BitmapResizer
import com.example.memeify.utils.IMAGE_URI_KEY
import com.example.memeify.utils.Toaster
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityMainBinding
    private lateinit var pictureImageview : ImageView
    private var selectedPhotoPath : Uri? = null
    private var pictureTaken : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            selectedPhotoPath = savedInstanceState.getParcelable("URI")
            binding.pictureImageview.setImageURI(selectedPhotoPath)
            setImageViewWithImage()
        }

        binding.pictureImageview.setOnClickListener(this)
        binding.enterTextButton.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.pictureImageview -> takePictureWithCamera()
            R.id.enterTextButton -> moveToNextScreen()
            else -> println("No case satisfied")
        }
    }

    private fun takePictureWithCamera() {
        val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
//        val imageFile = File(filesDir, "images/$filename")
//        if (imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdirs()
        val filesDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val tempFile = File.createTempFile(
            "JPEG_${timeStamp}",
            ".jpg",
            filesDir
        )
        selectedPhotoPath = FileProvider.getUriForFile(
            this@MainActivity,
            "com.example.memeify.fileprovider",
            tempFile
        )

        cameraLauncher.launch(selectedPhotoPath)

    }

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) {result ->
        if (result) {
            setImageViewWithImage()
        }
    }

    private fun setImageViewWithImage() {
        pictureImageview = binding.pictureImageview
        val photoPath : Uri = selectedPhotoPath ?: return
        pictureImageview.post {
            val pictureBitmap = BitmapResizer.shrinkBitmap(
                this@MainActivity,
                photoPath,
                pictureImageview.width,
                pictureImageview.height
            )
            pictureImageview.setImageBitmap(pictureBitmap)
        }
        binding.lookingGoodTextView.visibility = View.VISIBLE

        pictureTaken = true
    }

    private fun moveToNextScreen() {
        if (pictureTaken) {
            val nextScreenIntent = Intent(this, MemeGeneratorActivity::class.java).apply {
                putExtra(IMAGE_URI_KEY, selectedPhotoPath)
                putExtra(BITMAP_WIDTH, pictureImageview.width)
                putExtra(BITMAP_HEIGHT, pictureImageview.height)
            }

            startActivity(nextScreenIntent)
        } else {
            Toaster.show(this, R.string.select_a_picture)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("URI", selectedPhotoPath)
    }

}