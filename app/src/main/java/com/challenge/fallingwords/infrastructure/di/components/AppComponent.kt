package com.challenge.fallingwords.infrastructure.di.components

import com.challenge.fallingwords.infrastructure.di.modules.LogicModule
import com.challenge.fallingwords.infrastructure.di.modules.MainModule
import com.challenge.fallingwords.infrastructure.di.modules.PresenterModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(MainModule::class), (LogicModule::class), (PresenterModule::class)])
interface AppComponent : MainComponent {
}