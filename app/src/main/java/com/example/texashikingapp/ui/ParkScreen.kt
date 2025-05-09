package com.example.texashikingapp.ui

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.texashikingapp.R
import com.example.texashikingapp.model.Park
import com.example.texashikingapp.model.parks
import com.example.texashikingapp.ui.theme.TexasHikingAppTheme
import com.example.texashikingapp.ui.theme.Typography

enum class ParkScreens {
    ParkScreen,
    HikingScreen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkApp(
    texasHikingAppViewModel: TexasHikingAppViewModel = viewModel(
        factory = TexasHikingAppViewModel.Factory
    )
) {
    val navController = rememberNavController()

    var showHikingHelpDialog by rememberSaveable { mutableStateOf(false) }
    var selectHikingPreference = texasHikingAppViewModel::selectHikingPreference
    var uiState = texasHikingAppViewModel.uiState.collectAsState().value
    var prefEasyHike = uiState.prefEasyHike
    var prefEasyHikeTxt = uiState.toggleContentDescription
    val context = LocalContext.current
    val mainScreenTitle = stringResource(R.string.top_bar_name)
    var topAppBarTitle by remember { mutableStateOf(mainScreenTitle) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(topAppBarTitle)},
            )
        },
        bottomBar = {
            BottomAppBar (
                actions = {
                    IconButton(onClick = { showHikingHelpDialog = true}) {
                        Icon(Icons.AutoMirrored.Filled.Help, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { shareApplication(context, "Love this app", "This is the best hiking application ever!")  }) {
                        Icon(Icons.Filled.Share, contentDescription = "Localized description")
                    }
                }
            )
        }

    )
    { innerPadding ->
        NavHost (
            navController = navController,
            startDestination = ParkScreens.ParkScreen.name
        ) {
            composable(ParkScreens.ParkScreen.name) {
                it -> LazyColumn(contentPadding = innerPadding) {
                    items(parks) {
                        ParkItem(
                            park = it,
                            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                        )
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                texasHikingAppViewModel.selectHikingTrails(context,it.name, prefEasyHike)
                                topAppBarTitle = "Trails in " + getString(context, it.name)
                                navController.navigate(ParkScreens.HikingScreen.name)
                            }
                        )
                        {
                            Text(text = "See hikes in ${stringResource(it.name)}")
                        }
                    }
                item {
                    PrefEasyHikes(
                        prefEasyHike = prefEasyHike,
                        onPrefChanged = selectHikingPreference,
                        prefEasyHikeText = prefEasyHikeTxt,
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                    )
                }
                }
            }
            composable(ParkScreens.HikingScreen.name){
                it -> LazyColumn(contentPadding = innerPadding) {
                    items(texasHikingAppViewModel.currTrails) {
                        TrailItem(hikingTrail = it,
                            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                        )
                    }
                    item {
                        Button(onClick = {
                            topAppBarTitle = mainScreenTitle
                            navController.navigate(ParkScreens.ParkScreen.name)
                        })
                        {
                            Text(text = "Go back")
                        }
                    }
                }
            }
        }
        if (showHikingHelpDialog) {
            MinimalDialog ( onDismissRequest = {showHikingHelpDialog = false} )
        }
    }
}

@Composable
fun PrefEasyHikes(
    prefEasyHike: Boolean,
    prefEasyHikeText: Int,
    onPrefChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .fillMaxWidth()
        .size(48.dp)
        .padding(dimensionResource(R.dimen.padding_small))
    ) {
        Text(
            text = stringResource(prefEasyHikeText),
            fontSize = 24.sp,
        )
        Switch(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = prefEasyHike,
            onCheckedChange = onPrefChanged
        )
    }

}

@Composable
fun ParkItem(
    park: Park,
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
                ParkName(park.name)
                Spacer(modifier = Modifier.weight(1f))
                ParkItemButton(
                    parkName = park.name,
                    expanded = expanded,
                    onClick = { expanded = !expanded}
                )
            }
            if (expanded) {
                ParkTown(park.town,
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
fun ParkName(
    @StringRes parkName: Int,
    modifier: Modifier = Modifier
)
{
    Text(
        text = stringResource(parkName),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
    )

}

@Composable
fun ParkTown(
    @StringRes parkTown: Int,
    modifier: Modifier = Modifier
)
{
    Column (
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.expand_button_content_description),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = stringResource(parkTown),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun ParkItemButton(
    @StringRes parkName: Int,
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

@Preview
@Composable
fun TexasHikingPreview() {
    TexasHikingAppTheme(darkTheme = false) {
        ParkApp()
    }
}

@Preview
@Composable
fun TexasHikingDarkThemePreview() {
    TexasHikingAppTheme(darkTheme = true) {
        ParkApp()
    }
}

private fun shareApplication(context: Context, subject: String, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.share_app)
        )
    )
}

@Composable
fun MinimalDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .clickable { onDismissRequest() }
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = "Help Screen",
                style = Typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "This application shows the user state parks in Texas and related hiking trails",
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
            )
            Text(
                text = "The user can click on a park to see the trails in that park",
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
            )
            Text(
                text = "The user can set a preference to see only easy hikes.  When this is done, only easy hikes for the selected park are shown",
                style = Typography.bodySmall,
                textAlign = TextAlign.Left
            )
        }
    }
}