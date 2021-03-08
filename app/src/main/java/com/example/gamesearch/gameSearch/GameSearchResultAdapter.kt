package com.example.gamesearch.gameSearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamesearch.databinding.ListItemGameSearchBinding
import com.example.gamesearch.util.AutoUpdatableAdapter
import com.example.gamesearch.webservice.Games
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates

class GameSearchResultAdapter(private val gameSelectCallback: OnGameSelectCallback) :
    RecyclerView.Adapter<GameSearchViewHolder>(), AutoUpdatableAdapter {

    private val list = mutableListOf<Games>()


    fun updateList(gamesList: List<Games>) {
        list.clear()
        val items: List<Games> by Delegates.observable(gamesList) { _, old, new ->
            autoNotify(old, new) { o, n ->
                o == n
            }
        }
        list.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameSearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGameSearchBinding.inflate(inflater, parent, false)
        with(binding) {
            gameSelectCallback = this@GameSearchResultAdapter.gameSelectCallback
        }
        return GameSearchViewHolder(binding)
    }


    override fun onBindViewHolder(holder: GameSearchViewHolder, position: Int) {
        holder.updateView(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class GameSearchViewHolder(
    private val binding: ListItemGameSearchBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun updateView(games: Games) {
        binding.games = games
        binding.gameName.text = games.name
        Picasso.get().load(games.image.icon_url).into(binding.gameImage)
    }
}

interface OnGameSelectCallback {
    fun onGameSelect(game: Games)
}