package com.challenge.fallingwords.infrastructure.di.modules

import com.challenge.fallingwords.game.domain.GetWords
import com.challenge.fallingwords.game.domain.GetWordsInteractor
import dagger.Module
import dagger.Provides

@Module
class LogicModule{

    @Provides
    fun provideGetWords(interactor: GetWordsInteractor): GetWords {
        return interactor
    }
}