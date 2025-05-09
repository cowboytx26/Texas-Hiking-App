package com.example.texashikingapp.model

import androidx.annotation.StringRes
import com.example.texashikingapp.R

data class Park(
    @StringRes val name: Int,
    @StringRes val town: Int,
)

val parks = listOf(
    Park(R.string.palmetto, R.string.gonzales),
    Park(R.string.abilene, R.string.tuscola),
    Park(R.string.buescher, R.string.smithville),
    Park(R.string.bastropsp, R.string.bastrop),
    Park(R.string.bigbendranch, R.string.terlingua)
)