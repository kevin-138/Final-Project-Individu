package com.kevin.netkick.presentation.view.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kevin.netkick.databinding.FragmentTrophiesBinding
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.view.trophies.activity.coach.CoachSearchActivity
import com.kevin.netkick.presentation.view.trophies.activity.players.TeamsSearchActivity
import com.kevin.netkick.presentation.view.trophies.activity.topscorer.LeagueTopScorerSearchActivity

class TrophiesFragment : Fragment() {
    private lateinit var binding: FragmentTrophiesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrophiesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkOnline(false)
        binding.apply {
            btnCoachTrophies.setOnClickListener {
                val intent = Intent(requireContext(), CoachSearchActivity::class.java)
                startActivity(intent)
            }
            btnPlayerTrophies.setOnClickListener {
                val intent = Intent(requireContext(), TeamsSearchActivity::class.java)
                startActivity(intent)
            }
            btnTopScorer.setOnClickListener {
                val intent = Intent(requireContext(), LeagueTopScorerSearchActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun checkOnline(current:Boolean = false) {
        val onlineCheck = activity?.let { PresentationUtils.isOnline(requireActivity()) }
        if (onlineCheck != true){
            if (current){
                PresentationUtils.networkDialog(requireActivity(),PresentationUtils.TROPHIES)
            }
        }
    }

}