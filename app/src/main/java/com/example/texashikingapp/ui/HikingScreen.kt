package com.example.texashikingapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.texashikingapp.ui.ParkApp
import com.example.texashikingapp.R
import com.example.texashikingapp.model.Trail
import com.example.texashikingapp.model.parks
import com.example.texashikingapp.model.trails

@Composable
fun TrailItem(
    hikingTrail: Trail,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(modifier = modifier)
    {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                TrailName(hikingTrail.name)
                Spacer(modifier = Modifier.weight(1f))
                TrailItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded}
                )
            }
            if (expanded) {
                TrailDifficulty(hikingTrail.difficultyRating,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
        }
    }
}

@Composable
fun TrailName(
    @StringRes trailName: Int,
    modifier: Modifier = Modifier
)
{
    Text(
        text = stringResource(trailName),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
    )

}

@Composable
fun TrailDifficulty(
    @StringRes trailDifficulty: Int,
    modifier: Modifier = Modifier
)
{
    Column (
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.expand_button_content_description2),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = stringResource(trailDifficulty),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun TrailItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}