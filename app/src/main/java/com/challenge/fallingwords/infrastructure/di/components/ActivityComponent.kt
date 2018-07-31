package com.challenge.fallingwords.infrastructure.di.components

import android.content.Context
import com.challenge.fallingwords.infrastructure.di.annotations.PerActivity
import com.challenge.fallingwords.infrastructure.di.modules.ActivityModule
import dagger.Component

@PerActivity
@Component(dependencies = [(AppComponent::class)], modules = [(ActivityModule::class)])
interface ActivityComponent {
    fun activity(): Context
}