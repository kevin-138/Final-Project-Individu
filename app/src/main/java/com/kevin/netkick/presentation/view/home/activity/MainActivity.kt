package com.kevin.netkick.presentation.view.home.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.gu.toolargetool.TooLargeTool
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityMainBinding
import com.kevin.netkick.presentation.view.home.fragments.ExploreFragment
import com.kevin.netkick.presentation.view.home.fragments.HomeFragment
import com.kevin.netkick.presentation.view.home.fragments.TrophiesFragment
import com.kevin.netkick.presentation.view.viewmodels.MainViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import javax.inject.Inject


class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var activeFragment:Fragment
    private lateinit var homeFragment: HomeFragment
    private lateinit var exploreFragment: ExploreFragment
    private lateinit var trophiesFragment: TrophiesFragment
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel:MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectInto(this)
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.isLoading.value
            }
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFragments()
        TooLargeTool.startLogging(application)
        setupNavigation()

    }

    private fun setupFragments(){
        homeFragment = HomeFragment(viewModel,application)
        exploreFragment = ExploreFragment()
        trophiesFragment = TrophiesFragment()
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fl_home_activity, homeFragment)
            add(R.id.fl_home_activity, exploreFragment)
            add(R.id.fl_home_activity, trophiesFragment)
            hide(exploreFragment)
            hide(trophiesFragment)
        }.commit()
    }

    private fun setupNavigation(){
        activeFragment = exploreFragment
        binding.bnvNavBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> {
                    setCurrentFragment(homeFragment)
                    activeFragment = homeFragment
                }
                R.id.nav_explore -> {
                    setCurrentFragment(exploreFragment)
                    activeFragment = exploreFragment
                }
                R.id.nav_trophies -> {

                    setCurrentFragment(trophiesFragment)
                    activeFragment = trophiesFragment
                }
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.hide(activeFragment).show(fragment)
        fragmentTransaction.commit()
    }

}