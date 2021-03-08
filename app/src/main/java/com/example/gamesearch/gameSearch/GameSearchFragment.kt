package com.example.gamesearch.gameSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamesearch.AppData
import com.example.gamesearch.databinding.FragmentSearchBinding
import com.example.gamesearch.di.Injectable
import com.example.gamesearch.webservice.Games
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

class GameSearchFragment : Fragment(), Injectable, OnGameSelectCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: GameSearchViewModel by lazy {
        ViewModelProvider(viewModelStore, viewModelFactory).get(GameSearchViewModel::class.java)
    }
    private var userInput = ""

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        // sets the listener for the query in the view
        binding.inputSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false // do nothing
            }

            // when char is entered, send new text in the query channel
            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launchWhenCreated {
                    // sets visibility of results
                    if (binding.autocompleteSearchList.visibility == View.GONE) {
                        binding.autocompleteSearchList.visibility = View.VISIBLE
                    }
                    if (!newText.isNullOrEmpty()) {
                        userInput = newText.toString()
                        viewModel.queryChannel.send(newText.toString())
                    }
                }
                return false
            }
        })

        observeViewModel()
        setupRecyclerView()
        return binding.root
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun observeViewModel() {
        observeViewModelForAutoCompleteName()
    }

    private fun setupRecyclerView() {
        binding.autocompleteSearchList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun observeViewModelForAutoCompleteName() {
        viewModel.searchResult.observe(
            viewLifecycleOwner,
            Observer(::setupGameLookupAdapter)
        )
    }

    // setup recycler view adapter
    private fun setupGameLookupAdapter(results: List<Games>?) {
        results?.let {
            val adapter = GameSearchResultAdapter(this)
            adapter.updateList(it)
            binding.autocompleteSearchList.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    // game select callback
    override fun onGameSelect(game: Games) {
        // set game data for detail screen to access
        AppData.game = game
        // calls function in MainActivity to navigate to detail screen
        (activity as MainActivity).showGameDetailsScreen()
    }
}