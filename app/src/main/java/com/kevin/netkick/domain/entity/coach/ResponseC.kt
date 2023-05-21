package com.kevin.netkick.domain.entity.coach

import android.os.Parcelable
import com.kevin.netkick.domain.entity.player.subplayer.Birth
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseC(
    val name: String,
    val age: Int,
    val birth: Birth,
    val nationality: String,
    val photo: String,
    val team: TeamC,
    val career: List<Career>
) : Parcelable
