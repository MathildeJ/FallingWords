package com.challenge.fallingwords.infrastructure.di.components

import com.challenge.fallingwords.game.ui.GameFragment
import com.challenge.fallingwords.infrastructure.di.annotations.PerActivity
import com.challenge.fallingwords.infrastructure.di.modules.ActivityModule
import dagger.Component

@PerActivity
@Component(dependencies = [(AppComponent::class)], modules = [(ActivityModule::class)])
interface FragmentComponent : ActivityComponent{
    fun inject(fragment: GameFragment)

}