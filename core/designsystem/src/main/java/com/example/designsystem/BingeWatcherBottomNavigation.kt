package com.example.designsystem

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation3.runtime.NavKey
import com.example.navigation.Navigator


@Composable
fun BingeWatcherBottomNavigation(
    navigator: Navigator,
    first: NavKey,
    second: NavKey,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            item(
                selected = true,
                onClick =  { navigator.navigate(first) } ,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                    )
                },
                label = { Text("Discover") }
            )
            item(
                selected = true,
                onClick =  { navigator.navigate(second) } ,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                    )
                },
                label = { Text("Search") }
            )
        }
    ) {
        content()
    }
}

