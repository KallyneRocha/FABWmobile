package com.moveis.new_fabw

import androidx.navigation.fragment.NavHostFragment
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainInstrumentedTest {
    @Test
    fun testNavigationController() {
        // inicia o main
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        // pega NavController
        scenario.onActivity { activity ->
            val navHostFragment =
                activity.supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navHostFragment.navController

            // confere se o NavController não é null
            assertNotNull(navController)
        }
    }
}