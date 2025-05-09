package com.example.texashikingapp.model

import androidx.annotation.StringRes
import com.example.texashikingapp.R

data class Trail(
    @StringRes val name: Int,
    @StringRes val parkName: Int,
    @StringRes val difficultyRating: Int,
)

val trails = listOf(
    Trail(R.string.buffalo_wallow, R.string.abilene, R.string.easy),
    Trail(R.string.palmetto_interpretive_trail, R.string.palmetto, R.string.easy),
    Trail(R.string.cowboycircle, R.string.abilene, R.string.moderate),
    Trail(R.string.lostpines, R.string.bastropsp, R.string.easy),
    Trail(R.string.heronhide, R.string.bastropsp, R.string.hard),
    Trail(R.string.treearmy, R.string.bastropsp, R.string.easy),
    Trail(R.string.encinoloop, R.string.bigbendranch, R.string.easy),
    Trail(R.string.sauceda, R.string.bigbendranch, R.string.easy),
    Trail(R.string.closedcanyon, R.string.bigbendranch, R.string.hard),
    Trail(R.string.pine_gulch, R.string.buescher, R.string.easy),
    Trail(R.string.flower_view_crossing, R.string.buescher, R.string.moderate),
    Trail(R.string.roosevelts_cutoff, R.string.buescher, R.string.hard),
)