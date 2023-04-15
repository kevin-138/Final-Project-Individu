package com.kevin.netkick.presentation.view.home.activity

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityMainBinding
import com.kevin.netkick.domain.DomainUseCase
import com.kevin.netkick.presentation.view.home.fragments.ExploreFragment
import com.kevin.netkick.presentation.view.home.fragments.HomeFragment
import com.kevin.netkick.presentation.view.home.fragments.TrophiesFragment
import com.kevin.netkick.presentation.view.home.vm
import javax.inject.Inject


class MainActivity @Inject constructor(private val useCase: DomainUseCase): AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val homeFragment = HomeFragment()
    private val exploreFragment = ExploreFragment()
    private val trophiesFragment = TrophiesFragment()

    @Inject
    lateinit var viewModel: vm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as NetkickApplication).appComponent.injectInto(this)
        setupNavigation()
    }

    private fun setupNavigation(){
        setCurrentFragment(homeFragment)
        binding.bnvNavBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> setCurrentFragment(homeFragment)
                R.id.nav_explore -> setCurrentFragment(exploreFragment)
                R.id.nav_trophies -> setCurrentFragment(trophiesFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.flHomeActivity.id,fragment)
        fragmentTransaction.commit()
    }

}