package com.kevin.netkick.presentation

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.kevin.netkick.R
import com.kevin.netkick.presentation.view.main.fragments.ExploreFragment
import com.kevin.netkick.presentation.view.main.fragments.HomeFragment
import com.kevin.netkick.presentation.view.main.fragments.TrophiesFragment
import java.util.*
import kotlin.concurrent.schedule


object PresentationUtils {

    const val LIVE_PARAMS = "all"

    const val HOME = "001"
    const val EXPLORE = "002"
    const val TROPHIES = "003"

    const val NEWS_URL = "NEWS_URL"
    const val COUNTRY_CODE = "COUNTRY_CODE"

    const val TEAM_ID = "TEAM_ID"
    const val TEAM_FULL_DATA = "TEAM_FULL_DATA"
    const val TEAM_SEARCH_DATA = "TEAM_SEARCH_DATA"
    const val TEAM_SEASON = "TEAM_SEASON"

    const val POPULAR_SEASON = 2019

    const val LEAGUE_FULL_DATA = "LEAGUE_FULL_DATA"

    const val LEAGUE_SEASON = "LEAGUE_SEASON"

    const val TYPE_ITEM = 0
    const val TYPE_HEADER = 1

    const val WINNER = "Winner"
    const val SECOND = "2nd Place"

    const val COACH_FULL_DATA = "COACH_FULL_DATA"
    const val PLAYER_FULL_DATA = "PLAYER_FULL_DATA"


    const val FIXTURE_FULL_DATA = "FIXTURE_FULL_DATA"
    const val FIXTURE_REQUIREMENT= "FIXTURE_REQUIREMENT"

    fun isOnline(activity: FragmentActivity): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false}

    fun networkDialog(activity: FragmentActivity, currentFrag: String){
        val dialogBuilder = AlertDialog.Builder(activity, R.style.NetworkAlertDialogTheme)
        dialogBuilder.setMessage("No Network Connection detected, Please make sure you have a stable connection to the internet, then press retry to refresh the app and try again.")
        dialogBuilder.setCancelable(false)
        dialogBuilder.setIcon(R.drawable.no_internet_logo)
        dialogBuilder.setTitle("No Network Connection")
        dialogBuilder.setPositiveButton("RETRY") { _, _ ->
            var homeFragment = false
            var expFragment = false
            var tropFragment = false

            when(currentFrag){
                HOME -> homeFragment = true

                EXPLORE ->  expFragment = true

                TROPHIES -> tropFragment = true
            }
            Timer().schedule(1000L) {
                activity.runOnUiThread {
                    (activity.supportFragmentManager.fragments[0] as HomeFragment).checkOnline(homeFragment)
                    (activity.supportFragmentManager.fragments[1] as ExploreFragment).checkOnline(expFragment)
                    (activity.supportFragmentManager.fragments[2] as TrophiesFragment).checkOnline(tropFragment)
                }
            }
        }
        dialogBuilder.setNegativeButton("CLOSE APP") { _, _ ->
            activity.finish()
        }
        val connectionAlertDialog = dialogBuilder.create()
        connectionAlertDialog.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)
        connectionAlertDialog.show()
    }

    fun networkDialog(activity: Activity){
        val dialogBuilder = AlertDialog.Builder(activity, R.style.NetworkAlertDialogTheme)
        dialogBuilder.setMessage("No Network Connection detected, Please make sure you have a stable connection to the internet")
        dialogBuilder.setCancelable(false)
        dialogBuilder.setIcon(R.drawable.no_internet_logo)
        dialogBuilder.setTitle("No Network Connection")
        dialogBuilder.setPositiveButton("Back") { _, _ ->
            Timer().schedule(500L) {
                activity.runOnUiThread {
                    activity.finish()
                }
            }
        }
        val connectionAlertDialog = dialogBuilder.create()
        connectionAlertDialog.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)
        connectionAlertDialog.show()
    }

    fun newsDateFormatter(date:String):String{
        return date.split("T")[0]
    }

    fun errorToast(context: Context,error:String){
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    fun loadingDrawableBar(context: Context):CircularProgressDrawable{
        val loadingDrawable = CircularProgressDrawable(context)
        loadingDrawable.strokeWidth = 5f
        loadingDrawable.centerRadius = 30f
        loadingDrawable.setColorSchemeColors(Color.WHITE)
        loadingDrawable.start()
        return loadingDrawable
    }


}