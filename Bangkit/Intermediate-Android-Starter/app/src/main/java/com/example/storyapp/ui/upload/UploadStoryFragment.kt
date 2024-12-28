package com.example.storyapp.ui.upload

import android.app.Activity.RESULT_OK
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.example.storyapp.R
import com.example.storyapp.data.base.ApiResponse
import com.example.storyapp.data.stories.UploadStory
import com.example.storyapp.databinding.FragmentUploadStoryBinding
import com.example.storyapp.utils.Toaster
import com.example.storyapp.utils.getImageUri
import com.example.storyapp.utils.getImageUriForPreQ
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class UploadStoryFragment : Fragment() {

    private lateinit var binding : FragmentUploadStoryBinding

    private val viewModel: UploadStoryViewModel by viewModels()

    private var currentImageUri : Uri? = null

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) {isSuccess ->
        if (isSuccess) {
            cropImage(currentImageUri!!)
        }

    }

    private fun showImage() {
        binding.ivStory.setImageURI(currentImageUri)

    }

    private val launcherPickMedia = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri : Uri? ->
        if (uri != null) {
            Toaster.show(requireContext(), getString(R.string.success_pick_media))
            cropImage(uri)
        } else {
            Toaster.show(requireContext(), getString(R.string.error_pick_media))
        }
    }

    private val launchUCrop = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result ->
        if (result.resultCode == RESULT_OK) {
            currentImageUri = UCrop.getOutput(result.data!!)
            showImage()
        } else if (result.resultCode == UCrop.RESULT_ERROR){
            val cropError = UCrop.getError(result.data!!)
            Toaster.show(requireContext(), cropError?.localizedMessage!!)
        }

    }

    private fun cropImage(sourceUri: Uri) {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val filesDir = requireContext().filesDir
        val imageFile = File(filesDir, "/StoryApp/${timeStamp}.jpg")
        if (imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdir()

        val imageUri = Uri.fromFile(imageFile)

        val uCrop = UCrop.of(sourceUri, imageUri)
            .withAspectRatio(1f, 1f)

        uCrop.getIntent(requireContext()).apply {
            launchUCrop.launch(this)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadStoryBinding.inflate(inflater, container, false)

        setupUi()
        setupObserver()

        return binding.root
    }

    private fun setupObserver() {
        viewModel.uploadStatus.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled().let { uploadStatus ->
                when (uploadStatus) {
                    is ApiResponse.Success -> {
                        Toaster.show(requireContext(),uploadStatus.data.message)
                        binding.loadingIndicator.hide()
                        findNavController().navigate(R.id.home)
                    }
                    is ApiResponse.Error -> {
                        Toaster.show(requireContext(), uploadStatus.error)
                        binding.loadingIndicator.hide()
                    }
                    is ApiResponse.Loading -> binding.loadingIndicator.show()
                    else -> binding.loadingIndicator.hide()
                }
            }
        }
    }

    private fun setupUi() {
        with(binding) {
            btnCamera.setOnClickListener {
                currentImageUri = getImageUri(requireContext())
                launcherIntentCamera.launch(currentImageUri)
            }

            btnGallery.setOnClickListener {
                launcherPickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            btnUpload.setOnClickListener {
                val description = etDescription.text.toString()

                if (description.isNotEmpty() && currentImageUri != null) {
                    viewModel.uploadStory(UploadStory(
                        description = binding.etDescription.text.toString(),
                        uriFile = currentImageUri!!
                    ))

                    ivStory.setImageResource(R.drawable.ic_place_holder)
                    etDescription.text.clear()
                } else {
                    Toaster.show(requireContext(), getString(R.string.error_upload))
                }
            }

        }
    }
}