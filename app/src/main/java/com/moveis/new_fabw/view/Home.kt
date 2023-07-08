package com.moveis.new_fabw.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.moveis.new_fabw.R
import com.moveis.new_fabw.adapter.OrgAdapter
import com.moveis.new_fabw.databinding.FragmentHomeBinding
import com.moveis.new_fabw.model.Org
import com.moveis.new_fabw.viewmodel.HomeViewModel

class Home : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var navigation: NavController
    lateinit var viewModel: HomeViewModel
    lateinit var adapter: OrgAdapter
    private lateinit var orgs: List<Org>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        navigation = findNavController()

        binding.orgList.layoutManager = LinearLayoutManager(requireContext())
        viewModel.GetOrgs()
        ListOrgs()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddOrg.setOnClickListener {
            navigation.navigate(R.id.action_home_to_mapFragment)
        }
    }

    fun ListOrgs() {
        viewModel.home
            .observe(viewLifecycleOwner) { response ->
                if(response.Sucesso) {
                    orgs = response.Documentos as List<Org>
                    adapter = OrgAdapter(orgs)
                    binding.orgList.adapter = adapter
                }
            }
    }
}