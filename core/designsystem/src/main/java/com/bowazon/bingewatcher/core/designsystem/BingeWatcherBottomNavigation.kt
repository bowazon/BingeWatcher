package com.bowazon.bingewatcher.core.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocalLibrary
import androidx.compose.material.icons.rounded.Search
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
    homeKey: NavKey,
    searchKey: NavKey,
    myLibraryKey: NavKey,
    content: @Composable () -> Unit
) {
    NavigationSuiteScaffold(modifier = modifier,
        navigationSuiteItems = {
            item(
                selected = true,
                onClick =  { navigator.navigate(homeKey) } ,
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Home,
                        contentDescription = null,
                    )
                },
                label = { Text("Discover") }
            )
            item(
                selected = true,
                onClick =  { navigator.navigate(searchKey) } ,
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null,
                    )
                },
                label = { Text("Search") }
            )
            item(
                selected = true,
                onClick =  { navigator.navigate(myLibraryKey) } ,
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.LocalLibrary,
                        contentDescription = null,
                    )
                },
                label = { Text("My Library") }
            )
        }
    ) {
        content()
    }
}

