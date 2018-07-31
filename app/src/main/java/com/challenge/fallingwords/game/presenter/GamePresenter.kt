package com.challenge.fallingwords.game.presenter

import com.challenge.fallingwords.game.domain.GetWords
import com.challenge.fallingwords.infrastructure.base.BaseView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GamePresenter @Inject
constructor(private val getWords: GetWords): LifecyclePresenter<GamePresenter.View>(){

    fun initialize(view: View){
        initializeView(view)
        showInitialState()
    }

    private fun showInitialState(){
        view?.run {
            showNewWords(Pair("", ""))
            clearScore()
            hideButtons()
        }
    }

    override fun onStart() {
        subscribeToGetWords()
    }

    override fun onPause() {
        stopReceivingUpdates()
    }

    override fun onResume() {
        subscribeToGetWords()
    }

    override fun onFinish() {
        cleanUp()
    }

    private fun subscribeToGetWords(){
        if(compositeDisposable.size() == 0) {
            compositeDisposable.add(
                    getWords()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnComplete {
                                stopReceivingUpdates()
                                view?.showEndOfGame()
                            }
                            .subscribe({ onNewWordsReady(it) }, {})
            )
        }
    }

    private fun onNewWordsReady(words: Pair<String, String>){
        view?.run {
            showNewWords(words)
            showScore(0, 1)
            showButtons()
        }
    }

    private fun stopReceivingUpdates(){
        compositeDisposable.clear()
    }

    fun onYesClicked(){
        view?.hideButtons()
    }

    fun onNoClicked(){
        view?.hideButtons()
    }

    interface View: BaseView{
        fun showNewWords(words: Pair<String, String>)
        fun showScore(correctCount: Int, wordCount: Int)
        fun clearScore()
        fun showButtons()
        fun hideButtons()
        fun showEndOfGame()
    }
}