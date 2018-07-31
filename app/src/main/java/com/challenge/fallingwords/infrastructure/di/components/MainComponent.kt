package com.challenge.fallingwords.infrastructure.di.components

import com.challenge.fallingwords.FallingWordsApplication
import com.challenge.fallingwords.game.presenter.GamePresenter
import com.challenge.fallingwords.infrastructure.base.BaseActivity

interface MainComponent{
    fun inject(`object`: FallingWordsApplication)
    fun inject(activity: BaseActivity)

    fun getGamePresenter(): GamePresenter
}