package com.example.storyapp.ui.upload

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.pm.PackageManager
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.example.storyapp.R
import com.example.storyapp.data.base.ApiResponse
import com.example.storyapp.data.stories.UploadStory
import com.example.storyapp.databinding.FragmentUploadStoryBinding
import com.example.storyapp.utils.Toaster
import com.example.storyapp.utils.getImageUri
import com.example.storyapp.utils.getImageUriForPreQ
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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

    private lateinit var binding: FragmentUploadStoryBinding

    private val viewModel: UploadStoryViewModel by viewModels()
    private lateinit var fusedLocationProvider: FusedLocationProviderClient

    private var currentImageUri: Uri? = null
    private var lat: Double = 0.0
    private var lon: Double = 0.0

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            cropImage(currentImageUri!!)
        }

    }

    private val launcherPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toaster.show(requireContext(), getString(R.string.failed_permission))
        }

    }

    private val launcherPickMedia = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            Toaster.show(requireContext(), getString(R.string.success_pick_media))
            cropImage(uri)
        } else {
            Toaster.show(requireContext(), getString(R.string.error_pick_media))
        }
    }

    private val launchUCrop = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            currentImageUri = UCrop.getOutput(result.data!!)
            showImage()
        } else if (result.resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(result.data!!)
            Toaster.show(requireContext(), cropError?.localizedMessage!!)
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
                        Toaster.show(requireContext(), uploadStatus.data.message)
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
        launcherPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(requireActivity())

        with(binding) {
            btnCamera.setOnClickListener {
                currentImageUri = getImageUri(requireContext())
                launcherIntentCamera.launch(currentImageUri)
            }

            btnGallery.setOnClickListener {
                launcherPickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            switchLocation.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) ||
                        checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    ) {
                        fusedLocationProvider.lastLocation.addOnSuccessListener { location ->
                            if (location != null) {
                                lon = location.longitude
                                lat = location.latitude
                            }
                        }
                    }
                }
            }

            btnUpload.setOnClickListener {
                val description = etDescription.text.toString()

                if (description.isNotEmpty() && currentImageUri != null) {
                    viewModel.uploadStory(
                        UploadStory(
                            description = binding.etDescription.text.toString(),
                            uriFile = currentImageUri!!,
                            lat = lat,
                            lon = lon
                        )
                    )

                    ivStory.setImageResource(R.drawable.ic_place_holder)
                    etDescription.text.clear()
                } else {
                    Toaster.show(requireContext(), getString(R.string.error_upload))
                }
            }

        }
    }

    private fun showImage() {
        binding.ivStory.setImageURI(currentImageUri)
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

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}