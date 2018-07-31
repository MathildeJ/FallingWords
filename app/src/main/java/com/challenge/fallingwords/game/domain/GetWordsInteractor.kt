package com.challenge.fallingwords.game.domain

import com.challenge.fallingwords.infrastructure.util.getRandomElement
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

    override fun execute(words: Array<WordEngSpa>?): Observable<Triple<Pair<String, String>, Int, Boolean>> {
        return Observable.create<Triple<Pair<String, String>, Int, Boolean>> { subscriber ->
            val disposable = Observable.interval(WORDS_UPDATE_INTERVAL, TimeUnit.MILLISECONDS)
                    .subscribe {
                        if(wordCount < NB_OF_WORDS_IN_GAME && words != null) {
                            wordCount++
                            val firstWord = words.getRandomElement()
                            val isCorrectTranslationOfWord = Math.random() < 0.5
                            subscriber.onNext(Triple(
                                    Pair(firstWord.text_eng, (if(isCorrectTranslationOfWord) firstWord else words.getRandomElement()).text_spa),
                                    wordCount,
                                    isCorrectTranslationOfWord
                            ))
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