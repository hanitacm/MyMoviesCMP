package com.hanitacm.mymoviescmp

import android.app.Application
import com.hanitacm.mymoviescmp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MyMoviesCMPApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@MyMoviesCMPApp)
            androidLogger()
        }
    }
}
