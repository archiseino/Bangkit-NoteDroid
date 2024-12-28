package com.example.storyapp.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.storyapp.R
import com.example.storyapp.data.base.ApiResponse
import com.example.storyapp.databinding.FragmentMapsBinding
import com.example.storyapp.utils.Toaster
import com.google.android.gms.location.FusedLocationProviderClient

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var googleMap : GoogleMap

    private val viewModel: MapsViewModel by viewModels()

    private val boundsBuilder = LatLngBounds.Builder()


    private val launcherRequestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) getMyLocation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStoriesWithLocation()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled = true

        getMyLocation()
        setMapStyle()

        viewModel.mapsStatus.observe(viewLifecycleOwner) { mapsStatus ->
            when (mapsStatus) {
                is ApiResponse.Success -> {
                    mapsStatus.data.listStory.forEach { story ->
                        val latLng = LatLng(story.lat, story.lon)
                        googleMap.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(story.name)
                                .snippet(story.description)
                        )
                        boundsBuilder.include(latLng)
                    }
                    val bounds: LatLngBounds = boundsBuilder.build()
                    googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            bounds,
                            resources.displayMetrics.widthPixels,
                            resources.displayMetrics.heightPixels,
                            300
                        )
                    )

                    binding.loadingIndicator.hide()
                }

                is ApiResponse.Error -> {
                    Toaster.show(requireContext(), getString(R.string.failed_load_map))
                    binding.loadingIndicator.hide()
                }

                is ApiResponse.Loading -> binding.loadingIndicator.show()
                else -> binding.loadingIndicator.hide()
            }
        }


    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED )
        {
            googleMap.uiSettings.isMyLocationButtonEnabled = true
        } else {
            launcherRequestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setMapStyle() {
        try {
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(),
                    R.raw.custom_maps
                )
            )
            if (!success) {
                Toaster.show(requireContext(), getString(R.string.failed_load_map))
            }
        } catch (e: Resources.NotFoundException) {
            Toaster.show(requireContext(), getString(R.string.failed_load_map))
        }
    }
}