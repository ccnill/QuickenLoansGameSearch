package com.example.gamesearch.gameSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gamesearch.AppData
import com.example.gamesearch.databinding.FragmentGameDetailsBinding
import com.example.gamesearch.di.Injectable
import com.squareup.picasso.Picasso

class GameDetailsFragment : Fragment(), Injectable {

    private val selectedGame = AppData.game
    private lateinit var binding: FragmentGameDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameDetailsBinding.inflate(inflater, container, false)
        setUpView()
        return binding.root
    }

    private fun setUpView() {
        binding.gameName.text = selectedGame?.name
        Picasso.get().load(selectedGame?.image?.super_url).into(binding.gameImage)
        binding.gameDescription.text = selectedGame?.deck ?: "No Game Description"
    }
}