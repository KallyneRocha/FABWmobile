package com.moveis.new_fabw.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.moveis.new_fabw.R
import com.moveis.new_fabw.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    private lateinit var navigation: NavController
    private var supportMapFragment: SupportMapFragment? = null
    private var gMap: GoogleMap? = null
    private lateinit var latLng: LatLng
    private var marker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        navigation = findNavController()
        supportMapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        supportMapFragment?.getMapAsync(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddOrg.setOnClickListener {
            var bundle = Bundle()
            bundle.putDouble("latitude", latLng.latitude)
            bundle.putDouble("longitude", latLng.longitude)
            navigation.navigate(R.id.action_mapFragment_to_addContentFragment, bundle)
        }
    }

    private fun adicionarMarcador(latLng: LatLng) {
        marker = gMap?.addMarker(
            MarkerOptions()
                .title("Marcador")
                .position(latLng)
        )
    }

    override fun onMapReady(p0: GoogleMap?) {
        gMap = p0
        var latLngLocal = LatLng(-8.050000,-34.900002)
        gMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngLocal, 7f))

        gMap?.setOnMapClickListener {
            if(marker != null)
                marker?.remove()

            latLng = it
            adicionarMarcador(latLng)
            binding.btnAddOrg.show()
        }
    }
}