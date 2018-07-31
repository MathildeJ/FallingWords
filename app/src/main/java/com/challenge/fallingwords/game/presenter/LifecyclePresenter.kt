package com.challenge.fallingwords.game.presenter

import com.challenge.fallingwords.infrastructure.base.BasePresenter
import com.challenge.fallingwords.infrastructure.base.BaseView

abstract class LifecyclePresenter<T: BaseView> : BasePresenter<T>(), Lifecycle {

    internal var state: LifecycleState? = null

    init {
        this.state = LifecycleState.INITIAL
    }

    override fun start() {
        if (state == LifecycleState.INITIAL) {
            state = LifecycleState.STARTED
            onStart()
        } else if (state == LifecycleState.PAUSED) {
            state = LifecycleState.STARTED
            onResume()
        }
    }

    override fun pause() {
        if (state == LifecycleState.STARTED) {
            state = LifecycleState.PAUSED
            onPause()
        }
    }

    override fun resume() {
        if (state == LifecycleState.PAUSED) {
            state = LifecycleState.STARTED
            onResume()
        }
    }

    override fun finish() {
        if (state == LifecycleState.PAUSED) {
            state = LifecycleState.FINISHED
            onFinish()
        }
    }

    protected abstract fun onStart()
    protected abstract fun onPause()
    protected abstract fun onResume()
    protected abstract fun onFinish()

}
