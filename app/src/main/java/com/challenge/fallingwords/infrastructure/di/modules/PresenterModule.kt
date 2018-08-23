package com.challenge.fallingwords.infrastructure.di.modules

import com.challenge.fallingwords.game.domain.GetWords
import com.challenge.fallingwords.game.presenter.GamePresenter
import com.challenge.fallingwords.home.presenter.HomePresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule{

    @Provides
    fun provideGamePresenter(getWords: GetWords): GamePresenter{
        return GamePresenter(getWords)
    }

    @Provides
    fun provideHomePresenter(): HomePresenter{
        return HomePresenter()
    }
}