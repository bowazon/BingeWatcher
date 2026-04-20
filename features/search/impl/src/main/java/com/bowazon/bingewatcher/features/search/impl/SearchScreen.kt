package com.bowazon.bingewatcher.features.search.impl

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherMediumPersonCard
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherMediumShowCard
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.ui.utils.loadPainter
import com.bowazon.bingewatcher.core.designsystem.R

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onShowClicked: (Int) -> Unit,
    onPersonClicked: (Int) -> Unit,
) {
    val searchType by viewModel.searchType.collectAsState()
    val items by viewModel.searchState.collectAsState()

    //Scaffold(modifier.fillMaxSize()) { padding ->
    Column {
        SearchTextField(viewModel::search)

        SearchTabs(
            selected = searchType,
            onSelected = viewModel::setSearchType
        )

        LazyColumn {
            items(items) { item ->
                when (item) {
                    is SearchItem.ShowItem -> {
                        val painter = loadPainter(
                            item.data.summary.image,
                            painterResource(R.drawable.show_placeholder)
                        )

                        BingeWatcherMediumShowCard(
                            modifier = Modifier.fillMaxWidth(),
                            showName = item.data.summary.name,
                            picturePainter = painter,
                            date = item.data.summary.premiered,
                            genre = item.data.summary.genres.joinToString(separator = "/"),
                            rating = item.data.summary.rating,
                            onClick = { onShowClicked(item.data.id) }
                        )
                    }

                    is SearchItem.PersonItem -> {
                        val painter = loadPainter(
                            item.data.image,
                            painterResource(R.drawable.show_placeholder)
                        )
                        BingeWatcherMediumPersonCard(
                            //modifier = Modifier.fillMaxWidth(),
                            name = item.data.name,
                            picturePainter = painter,
                            role = "",
                            onClick = { onPersonClicked(item.data.id) }
                        )
                    }
                }
            }
        }
    }
    //}
}

@Composable
fun SearchTextField(
    searchCallback: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = {
            text = it
            searchCallback(text)
        },
        label = { Text("Enter text:") }
    )
}

@Composable
fun SearchTabs(
    selected: SearchType,
    onSelected: (SearchType) -> Unit
) {
    val tabs = listOf(SearchType.SHOWS, SearchType.PEOPLE)

    TabRow(selectedTabIndex = tabs.indexOf(selected)) {
        tabs.forEach { type ->
            Tab(
                selected = type == selected,
                onClick = { onSelected(type) },
                text = {
                    Text(
                        if (type == SearchType.SHOWS) "Shows" else "People"
                    )
                }
            )
        }
    }
}