package com.challenge.fallingwords.infrastructure.di.modules

import android.content.Context
import com.challenge.fallingwords.FallingWordsApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModule(private val app: FallingWordsApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return app
    }
}