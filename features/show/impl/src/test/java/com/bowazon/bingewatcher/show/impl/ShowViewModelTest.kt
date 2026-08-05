package com.bowazon.bingewatcher.show.impl

import com.bowazon.bingewatcher.core.entities.ActorSummary
import com.bowazon.bingewatcher.core.entities.Season
import com.bowazon.bingewatcher.core.entities.ShowSummary
import com.bowazon.bingewatcher.core.repository.ShowRepository
import com.bowazon.bingewatcher.core.repository.ShowTempModel
import com.bowazon.bingewatcher.core.ui.ScreenState
import com.bowazon.bingewatcher.features.show.impl.ShowViewModel
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import app.cash.turbine.test
import com.bowazon.bingewatcher.features.show.impl.ShowScreenEffect
import com.bowazon.bingewatcher.features.show.impl.ShowScreenEvent
import kotlin.Int
import kotlin.String

@OptIn(ExperimentalCoroutinesApi::class)
class ShowViewModelTest {

    private val repository = mockk<ShowRepository>()

    private lateinit var viewModel: ShowViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `state becomes Success when repository emits show`() = runTest {
        every {
            repository.observeShow(1)
        } returns flowOf(show)


        viewModel = ShowViewModel(
            showId = 1,
            repository = repository
        )


        testDispatcher.scheduler.advanceUntilIdle()


        val state = viewModel.state.value

        assertTrue(state is ScreenState.Success)

        assertEquals(
            show,
            (state as ScreenState.Success).data.show
        )
    }

    @Test
    fun `on imdb click emits OpenUrl effect`() = runTest {

        every {
            repository.observeShow(1)
        } returns flowOf(show)


        viewModel = ShowViewModel(
            1,
            repository
        )


        viewModel.effects.test {

            testDispatcher.scheduler.advanceUntilIdle()


            viewModel.onEvent(
                ShowScreenEvent.OnImdbClick
            )


            val effect = awaitItem()

            assertEquals(
                ShowScreenEffect.OpenUrl(
                    "https://imdb.com/test"
                ),
                effect
            )
        }
    }

    companion object {
        val show = ShowTempModel(
            id = 1,
            summary = ShowSummary(
                name = "jopa",
                premiered = "yesterfay",
                image = "www", // TODO maybe bitmap?
                genres = listOf("boyevik"),
                rating = 9.9
            ),
            ended = "", // empty if still active
            officialSite = "www/site",
            imdb = "https://imdb.com/test",
            largeImage = "www/li",
            description = "movie",
            actors = listOf(
                ActorSummary(
                    id = 2,
                    name = "John Smith",
                    image = "www/actorimage",
                    characterName = "killer"
                )
            ),
            seasons = listOf(
                Season(
                    id = 3,
                    showName = "benis",
                    seasonNumber = 1,
                    episodesAmount = 2,
                    image = "www/seasonimage",
                    premiereDate = "oof"
                )
            ),
            images = listOf("www/url"),
            followed = false
        )
    }
}