package com.moveis.new_fabw

import android.os.Bundle
import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.moveis.new_fabw.adapter.OrgAdapter
import com.moveis.new_fabw.databinding.FragmentHomeBinding
import com.moveis.new_fabw.dto.GenericResponseDTO
import com.moveis.new_fabw.model.Org
import com.moveis.new_fabw.view.Home
import com.moveis.new_fabw.viewmodel.HomeViewModel
import junit.framework.TestCase.assertNotNull
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class HomeTest {

    @Mock
    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var navController: NavController

    @Mock
    private lateinit var adapter: OrgAdapter

    private lateinit var fragment: Home

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fragment = Home()
        fragment.viewModel = viewModel
        fragment.navigation = navController
        fragment.adapter = adapter
    }

    @Test
    fun onCreateView_setsUpViewModelAndListensToOrgs() {
        val scenario = FragmentScenario.launchInContainer(Home::class.java)
        scenario.onFragment { fragment ->
            assertEquals(fragment.viewModel, viewModel)
            verify(viewModel).GetOrgs()
            assertNotNull(fragment.binding.orgList.layoutManager)
            verify(fragment.viewModel.home).observe(fragment.viewLifecycleOwner, any())
        }
    }

    @Test
    fun onViewCreated_navigatesToMapFragment() {
        val scenario = FragmentScenario.launchInContainer(Home::class.java)
        scenario.onFragment { fragment ->
            fragment.onViewCreated(mock(View::class.java), mock(Bundle::class.java))
            fragment.binding.btnAddOrg.performClick()
            verify(fragment.navigation).navigate(R.id.action_home_to_mapFragment)
        }
    }

    @Test
    fun listOrgs_setsAdapterWhenResponseIsSuccessful() {
        val orgs = listOf(
            Org(
                Id = "1",
                Name = "Org 1",
                Categories = "Category 1",
                Lat = 123.456,
                Lng = 789.012,
                City = "City 1",
                State = "State 1",
                Country = "Country 1"
            ),
            Org(
                Id = "2",
                Name = "Org 2",
                Categories = "Category 2",
                Lat = 456.789,
                Lng = 012.345,
                City = "City 2",
                State = "State 2",
                Country = "Country 2"
            ),
        )

        val response = MutableLiveData<GenericResponseDTO<Org>>()
        response.value = GenericResponseDTO(true, orgs, null)

        val recyclerView = mock(RecyclerView::class.java)

        fragment.binding = mock(FragmentHomeBinding::class.java)
        `when`(fragment.binding.orgList).thenReturn(recyclerView)

        fragment.ListOrgs()

        verify(fragment.viewModel.home).observe(fragment.viewLifecycleOwner, any())
        verify(fragment.binding.orgList).adapter = adapter
        verify(adapter).setOrgs(orgs)
    }

}
