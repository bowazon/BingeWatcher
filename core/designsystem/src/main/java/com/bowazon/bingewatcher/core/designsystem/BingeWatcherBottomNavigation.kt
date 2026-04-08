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
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.core.navigation.Navigator


@Composable
fun BingeWatcherBottomNavigation(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    discoverKey: NavKey,
    searchKey: NavKey,
    myLibraryKey: NavKey,
    content: @Composable () -> Unit
) {
    NavigationSuiteScaffold(modifier = modifier,
        navigationSuiteItems = {
            item(
                selected = true,
                onClick =  { navigator.navigate(discoverKey) } ,
                icon = {
                    BingeWatcherDiscoverIcon()
                },
                label = { Text(stringResource(R.string.discover)) }
            )
            item(
                selected = true,
                onClick =  { navigator.navigate(searchKey) } ,
                icon = {
                    BingeWatcherSearchIcon()
                },
                label = { Text(stringResource(R.string.search)) }
            )
            item(
                selected = true,
                onClick =  { navigator.navigate(myLibraryKey) } ,
                icon = {
                    BingeWatcherMyLibraryIcon()
                },
                label = { Text(stringResource(R.string.my_library)) }
            )
        }
    ) {
        content()
    }
}

