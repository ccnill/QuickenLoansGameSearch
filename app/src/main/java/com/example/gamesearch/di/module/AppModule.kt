package com.example.gamesearch.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Provides
    fun provideContext(context: Application): Context {
        return context.applicationContext
    }
}