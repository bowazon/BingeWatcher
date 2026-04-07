package com.bowazon.bingewatcher.core.designsystem

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.core.navigation.Navigator


@Composable
fun BingeWatcherBottomNavigation(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    first: NavKey,
    second: NavKey,
    content: @Composable () -> Unit
) {
    NavigationSuiteScaffold(modifier = modifier,
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

