package com.challenge.fallingwords.game.domain

import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetWordsInteractor @Inject
constructor() : GetWords{

    companion object {
        private const val WORDS_UPDATE_INTERVAL = 3500L
        private const val NB_OF_WORDS_IN_GAME = 25
    }

    private var wordCount = 0

    override fun execute(): Observable<Pair<String, String>> {
        return Observable.create<Pair<String, String>> { subscriber ->
            val disposable = Observable.interval(WORDS_UPDATE_INTERVAL, TimeUnit.MILLISECONDS)
                    .subscribe {
                        if(wordCount < NB_OF_WORDS_IN_GAME) {
                            subscriber.onNext(Pair("cat", "chat"))
                            wordCount++
                        } else {
                            subscriber.onComplete()
                        }
                    }

            subscriber.setCancellable {
                disposable.dispose()
            }
        }
    }

}