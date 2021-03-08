package com.example.gamesearch.di.module

import com.example.gamesearch.di.scope.ActivityScope
import com.example.gamesearch.gameSearch.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [GameSearchModule::class])
    abstract fun contributeMainActivity(): MainActivity?
}