package com.example.flickscout

import android.app.Application
import com.example.flickscout.core.di.databaseModule
import com.example.flickscout.core.di.networkModule
import com.example.flickscout.core.di.repositoryModule
import com.example.flickscout.di.useCaseModule
import com.example.flickscout.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}