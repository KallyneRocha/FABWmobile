package com.moveis.new_fabw.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moveis.new_fabw.R
import com.moveis.new_fabw.databinding.FragmentNavBinding
import com.moveis.new_fabw.view.Home


class NavFragment : Fragment() {
    private lateinit var binding: FragmentNavBinding
    private lateinit var navigation: NavController
    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadFragment(Home())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavBinding.inflate(inflater, container, false)
        bottomNav = binding.root.findViewById<BottomNavigationView>(R.id.nav_bottom)
        navigation = findNavController()

        bottomNav.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.nav_home -> {
                    loadFragment(Home())
                    true
                }
                R.id.nav_map_solo -> {
                    loadFragment(SoloMapFragment())
                    true
                }
                R.id.nav_add-> {
                    navigation.navigate(R.id.action_navFragment_to_mapFragment)
                    true
                }
                else -> false
            }

        }

        return binding.root
    }


    private fun loadFragment(fragment: Fragment) {
        // load fragment
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_nav, fragment)
            .commit()
    }
}