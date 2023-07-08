package com.moveis.new_fabw.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.moveis.new_fabw.R
import com.moveis.new_fabw.databinding.FragmentSoloMapBinding
import com.moveis.new_fabw.viewmodel.SoloMapViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class SoloMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentSoloMapBinding
    private lateinit var viewModel: SoloMapViewModel
    private var supportMapFragment: SupportMapFragment? = null
    private var gMap: GoogleMap? = null
    private var marker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSoloMapBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SoloMapViewModel::class.java]
        supportMapFragment = childFragmentManager.findFragmentById(R.id.solo_map_fragment) as? SupportMapFragment
        supportMapFragment?.getMapAsync(this)

        viewModel.ObterDenuncias()

        return binding.root
    }

    override fun onMapReady(p0: GoogleMap?) {
        gMap = p0

        adicionarMarcadores()
    }

    private fun adicionarMarcadores() {
        viewModel.mapa
            .observe(viewLifecycleOwner) { response ->
                if (response.Sucesso) {
                    response.Documentos?.forEach { org ->
                        gMap?.addMarker(
                            MarkerOptions()
                                .title(org.Name)
                                .position(LatLng(org.Lat, org.Lng))
                        )
                    }
                }
            }
    }
}