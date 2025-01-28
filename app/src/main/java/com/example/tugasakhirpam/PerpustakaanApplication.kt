package com.example.tugasakhirpam

import android.app.Application
import com.example.tugasakhirpam.network.AppContainer
import com.example.tugasakhirpam.network.AppContainerImpl

class PerpustakaanApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl()
    }
}
