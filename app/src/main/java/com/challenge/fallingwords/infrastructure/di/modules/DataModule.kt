package com.challenge.fallingwords.infrastructure.di.modules

import com.challenge.fallingwords.game.data.WordsDataSource
import com.challenge.fallingwords.game.data.WordsLocalDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class DataModule{

    @Provides
    @Singleton
    open fun provideWordsDataSource(dataSource: WordsLocalDataSource): WordsDataSource {
        return dataSource
    }
}