package com.example.gamesearch.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gamesearch.di.GamesViewModelFactory
import com.example.gamesearch.di.ViewModelKey
import com.example.gamesearch.gameSearch.GameSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(GameSearchViewModel::class)
    abstract fun bindMainViewModel(gameSearchViewModel: GameSearchViewModel?): ViewModel?

    @Binds
    abstract fun bindViewModelFactory(factory: GamesViewModelFactory?): ViewModelProvider.Factory?
}