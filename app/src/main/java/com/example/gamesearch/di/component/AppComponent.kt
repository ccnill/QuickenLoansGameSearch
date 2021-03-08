package com.example.gamesearch.di.component

import android.app.Application
import com.example.gamesearch.GameApplication
import com.example.gamesearch.di.module.ActivityBindingModule
import com.example.gamesearch.di.module.ApiModule
import com.example.gamesearch.di.module.AppModule
import com.example.gamesearch.di.scope.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@ApplicationScope
@Component(modules = [AndroidInjectionModule::class, ActivityBindingModule::class, ApiModule::class, AppModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?
        fun build(): AppComponent?
    }

    fun inject(application: GameApplication?)
}