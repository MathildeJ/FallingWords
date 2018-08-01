package com.challenge.fallingwords.home.presenter

import com.challenge.fallingwords.infrastructure.base.BasePresenter
import com.challenge.fallingwords.infrastructure.base.BaseView

class HomePresenter: BasePresenter<HomePresenter.View>(){

    fun initialize(view: View){
        initializeView(view)
    }

    fun onStartClicked(){
        view?.showGame()
    }

    interface View: BaseView{
        fun showGame()
    }
}