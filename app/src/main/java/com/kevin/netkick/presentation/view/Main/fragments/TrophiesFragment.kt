package com.kevin.netkick.presentation.view.Main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kevin.netkick.R
import com.kevin.netkick.presentation.PresentationUtils

class TrophiesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trophies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun checkOnline(current:Boolean = false) {
        val onlineCheck = activity?.let { PresentationUtils.isOnline(requireActivity()) }
        if (onlineCheck == true){
//            getLiveData()
        }else{
            if (current){
                PresentationUtils.networkDialog(requireActivity(),PresentationUtils.TROPHIES)
            }
        }
    }

}