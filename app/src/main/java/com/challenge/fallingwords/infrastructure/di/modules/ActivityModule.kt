package com.challenge.fallingwords.infrastructure.di.modules

import android.content.Context
import com.challenge.fallingwords.infrastructure.base.BaseActivity
import com.challenge.fallingwords.infrastructure.di.annotations.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity) {

    @Provides
    @PerActivity
    internal fun provideActivityContext(): Context {
        return activity
    }
}
