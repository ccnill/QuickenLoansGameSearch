package com.example.gamesearch.di.module

import com.example.gamesearch.di.scope.FragmentScope
import com.example.gamesearch.gameSearch.GameDetailsFragment
import com.example.gamesearch.gameSearch.GameSearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GameSearchModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): GameSearchFragment?

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeGameDetailsFragment(): GameDetailsFragment?
}