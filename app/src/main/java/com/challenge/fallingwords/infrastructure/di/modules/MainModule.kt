package com.challenge.fallingwords.infrastructure.di.modules

import android.content.Context
import com.challenge.fallingwords.FallingWordsApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//need 'open' modifier for instrumentation test
@Module
open class MainModule(private val app: FallingWordsApplication) {

    @Provides
    @Singleton
    open fun provideApplicationContext(): Context {
        return app
    }
}