package com.moveis.new_fabw.view

import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.moveis.new_fabw.R
import com.moveis.new_fabw.databinding.FragmentAddContentBinding
import com.moveis.new_fabw.model.Org
import com.moveis.new_fabw.viewmodel.AddContentViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class AddContentFragment : Fragment() {
    private lateinit var binding: FragmentAddContentBinding
    private lateinit var navigation: NavController
    private lateinit var viewModel: AddContentViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var geocoder: Geocoder
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddContentBinding.inflate(inflater, container, false)
        navigation = findNavController()
        viewModel = ViewModelProvider(this)[AddContentViewModel::class.java]
        auth = FirebaseAuth.getInstance()
        geocoder = Geocoder(requireContext(), Locale.getDefault())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            latitude = bundle.getDouble("latitude", 0.0)
            longitude = bundle.getDouble("longitude", 0.0)
        }

        binding.btnSubmit.setOnClickListener {
            val name = binding.txtName.text.toString()
            val categories = binding.txtDonCategories.text.toString()
            val adress = ObterEnderecoPorCoordenadas()
            var org = Org("", name, categories, latitude, longitude, adress[0], adress[1], adress[2])

            viewModel.addOrg(org)
        }

        viewModel.addOrgContent
            .observe(viewLifecycleOwner) { response ->
                if(response.Sucesso)
                {
                    Snackbar.make(view, response.Mensagem, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.GREEN)
                        .show()
                    navigation.navigate(R.id.action_addContentFragment_to_navFragment)
                }
                else
                    Snackbar.make(view, response.Mensagem, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED)
                        .show()
            }
    }

    private fun ObterEnderecoPorCoordenadas() : List<String> {
        var enderecos = geocoder.getFromLocation(latitude, longitude, 1)
        var partesEndereco = mutableListOf<String>()

        partesEndereco.add(enderecos?.get(0)?.subAdminArea.toString())
        partesEndereco.add(enderecos?.get(0)?.adminArea.toString())
        partesEndereco.add(enderecos?.get(0)?.countryName.toString())

        return partesEndereco
    }
}