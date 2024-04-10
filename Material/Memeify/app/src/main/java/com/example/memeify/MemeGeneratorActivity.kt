package com.example.memeify

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.memeify.databinding.ActivityMemeGeneratorBinding
import com.example.memeify.utils.BITMAP_HEIGHT
import com.example.memeify.utils.BITMAP_WIDTH
import com.example.memeify.utils.BitmapResizer
import com.example.memeify.utils.FILE_SUFFIX_JPG
import com.example.memeify.utils.HELVETICA_FONT
import com.example.memeify.utils.IMAGE_URI_KEY
import com.example.memeify.utils.Toaster
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MemeGeneratorActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMemeGeneratorBinding
    private lateinit var selectedPictureImageview: ImageView
    private var viewBitmap: Bitmap? = null
    private var pictureUri: Uri? = null
    private var originalImage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemeGeneratorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.writeTextToImageButton.setOnClickListener(this)
        binding.saveImageButton.setOnClickListener(this)

        pictureUri = intent.getParcelableExtra(IMAGE_URI_KEY)
        val bitmapWidth = intent.getIntExtra(BITMAP_WIDTH, 100)
        val bitmapHeight = intent.getIntExtra(BITMAP_HEIGHT, 100)

        selectedPictureImageview = binding.selectedPictureImageview
        pictureUri?.let {
            val selectedImageBitmap =
                BitmapResizer.shrinkBitmap(this, it, bitmapWidth, bitmapHeight)
            selectedPictureImageview.setImageBitmap(selectedImageBitmap)
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.writeTextToImageButton -> createMeme()
            R.id.saveImageButton -> askForPermissions()
            else -> println("No case satisfied")

        }
    }

    private fun createMeme() {
        val topText = binding.topTextEdittext.text.toString()
        val bottomText = binding.bottomTextEdittext.text.toString()

        if (!originalImage) {
            pictureUri?.let {
                val bm = BitmapResizer.shrinkBitmap(
                    this, it, selectedPictureImageview.width, selectedPictureImageview.height
                )
                selectedPictureImageview.setImageBitmap(bm)
            }
        }

        val imageDrawable = selectedPictureImageview.drawable as BitmapDrawable

        viewBitmap = imageDrawable.bitmap
        viewBitmap = viewBitmap!!.copy(viewBitmap!!.config, true)
        viewBitmap?.let {
            addTextToBitmap(it, topText, bottomText)
        }

        // set your imageview to show your newly edited bitmap to
        selectedPictureImageview.setImageBitmap(viewBitmap)
        originalImage = true
    }

    private fun addTextToBitmap(viewBitmap: Bitmap, topText: String, bottomText: String) {
        // get dimensions of image
        val bitmapWidth = viewBitmap.width
        val bitmapHeight = viewBitmap.height

        // create a canvas that uses the bitmap as its base
        val pictureCanvas = Canvas(viewBitmap)

        // create paint object with font parameters
        val tf = Typeface.create(HELVETICA_FONT, Typeface.BOLD)

        val textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 18f, resources.displayMetrics
        ).toInt()

        val textPaint = Paint()
        textPaint.textSize = textSize.toFloat()
        textPaint.color = Color.WHITE
        textPaint.typeface = tf
        textPaint.textAlign = Paint.Align.CENTER

        val textPaintOutline = Paint()
        textPaintOutline.isAntiAlias = true
        textPaintOutline.textSize = textSize.toFloat()
        textPaintOutline.color = Color.BLACK
        textPaintOutline.typeface = tf
        textPaintOutline.style = Paint.Style.STROKE
        textPaintOutline.textAlign = Paint.Align.CENTER
        textPaintOutline.strokeWidth = 8f

        val xPos = (bitmapWidth / 2).toFloat()
        var yPos = (bitmapHeight / 7).toFloat()

        pictureCanvas.drawText(topText, xPos, yPos, textPaintOutline)
        pictureCanvas.drawText(topText, xPos, yPos, textPaint)

        yPos = (bitmapHeight - bitmapHeight / 14).toFloat()

        pictureCanvas.drawText(bottomText, xPos, yPos, textPaintOutline)
        pictureCanvas.drawText(bottomText, xPos, yPos, textPaint)
    }

    private fun askForPermissions() {

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            val permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            } else {
                saveImageToGallery(viewBitmap)
            }
        } else {
            saveImageToGallery(viewBitmap)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                saveImageToGallery(viewBitmap)
            } else {
                Toaster.show(this, R.string.permissions_please)
            }
        }


    private fun saveImageToGallery(memeBitmap: Bitmap?) {
        if (originalImage) {
            // save bitmap to file
            memeBitmap?.let { bitmap ->

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                    val filename = "${System.currentTimeMillis()}.jpg"
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }
                    //
                    val imageUri: Uri? = contentResolver?.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
                    )

                    imageUri?.let { uri ->
                        contentResolver?.openOutputStream(uri)?.use { outputStream ->
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        }
                        // Notify the system that the file has been added
                        MediaScannerConnection.scanFile(this, arrayOf(uri.path), null, null)
                        Toaster.show(this, R.string.save_image_succeeded)
                    }
                } else {
                    val imageFile = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        bitmap.toString() + FILE_SUFFIX_JPG
                    )

                    try {
                        // create output stream, compress image and write to file, flush and close outputstream
                        val fos = FileOutputStream(imageFile)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)
                        fos.flush()
                        fos.close()
                    } catch (e: IOException) {
                        Toaster.show(this, R.string.save_image_failed)
                    }

                    val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                    mediaScanIntent.data = Uri.fromFile(imageFile)
                    sendBroadcast(mediaScanIntent)
                    Toaster.show(this, R.string.save_image_succeeded)

                }
            }
        } else {
            Toaster.show(this, R.string.add_meme_message)
        }
    }

}