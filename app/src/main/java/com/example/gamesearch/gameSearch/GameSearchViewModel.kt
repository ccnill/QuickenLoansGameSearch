package com.example.gamesearch.gameSearch

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.gamesearch.webservice.GameSearchRepository
import com.example.gamesearch.webservice.Games
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameSearchViewModel @Inject constructor(private val gameSearchRepository: GameSearchRepository) :
    ViewModel() {

    // updates as user types
    @ExperimentalCoroutinesApi
    @FlowPreview
    val queryChannel = ConflatedBroadcastChannel<String>()

    @FlowPreview
    @ExperimentalCoroutinesApi
    internal val internalSearchResult = queryChannel.asFlow()
        .flowOn(Dispatchers.IO)
        .map { text ->
            getAutocompleteGames(text)
        }
        .catch { crash -> d("nill", crash.message!!) }

    // store search results as LiveData to be observed in UI
    @ExperimentalCoroutinesApi
    @FlowPreview
    val searchResult: LiveData<List<Games>?> = internalSearchResult.filterNotNull().asLiveData()

    // calls api with updated query
    private suspend fun getAutocompleteGames(query: String): List<Games>? =
        withContext(Dispatchers.IO) {
            gameSearchRepository.getGames(query)?.results
        }
}