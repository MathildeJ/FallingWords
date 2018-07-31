package com.challenge.fallingwords

import android.app.Application
import com.challenge.fallingwords.infrastructure.di.components.AppComponent
import com.challenge.fallingwords.infrastructure.di.modules.LogicModule
import com.challenge.fallingwords.infrastructure.di.modules.MainModule
import com.challenge.fallingwords.infrastructure.di.modules.PresenterModule
import com.challenge.fallingwords.infrastructure.di.components.DaggerAppComponent


class FallingWordsApplication: Application(){

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        setupComponent()
    }

    private fun setupComponent() {
        component = DaggerAppComponent.builder()
                .mainModule(MainModule(this))
                .logicModule(LogicModule())
                .presenterModule(PresenterModule())
                .build()

        component.inject(this)
    }

    fun getAppComponent(): AppComponent {
        return component
    }

    fun setComponent(appComponent: AppComponent) {
        this.component = appComponent
        this.component.inject(this)
    }
}