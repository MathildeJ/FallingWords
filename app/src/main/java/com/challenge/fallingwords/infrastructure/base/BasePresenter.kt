package com.challenge.fallingwords.infrastructure.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T: BaseView>{

    protected val compositeDisposable = CompositeDisposable()
    protected var view: T? = null

    fun initializeView(view: T){
        this.view = view
    }

    fun cleanUp(){
        compositeDisposable.clear()
        this.view = null
    }
}