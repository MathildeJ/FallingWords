package com.challenge.fallingwords.infrastructure.di.modules

import com.challenge.fallingwords.game.domain.GetWords
import com.challenge.fallingwords.game.domain.GetWordsInteractor
import dagger.Module
import dagger.Provides

//need 'open' modifier for instrumentation test
@Module
open class LogicModule{

    @Provides
    open fun provideGetWords(interactor: GetWordsInteractor): GetWords {
        return interactor
    }
}