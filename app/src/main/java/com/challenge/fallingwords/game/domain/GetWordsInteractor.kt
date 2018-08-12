package com.challenge.fallingwords.game.domain

import com.challenge.fallingwords.game.data.WordsRepository
import com.challenge.fallingwords.game.domain.model.WordEngSpa
import com.challenge.fallingwords.infrastructure.base.Constants
import com.challenge.fallingwords.infrastructure.util.getRandomElement
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetWordsInteractor @Inject
constructor(private val wordsRepository: WordsRepository) : GetWords{

    private var wordCount = 0


    override fun execute(): Observable<Triple<Pair<String, String>, Int, Boolean>> {
        val words = wordsRepository.getWords()
        return Observable.create<Triple<Pair<String, String>, Int, Boolean>> { subscriber ->
            val disposable = Observable.interval(0, Constants.WORDS_UPDATE_INTERVAL, TimeUnit.MILLISECONDS)
                    .subscribe {
                        if(wordCount < Constants.NB_OF_WORDS_IN_GAME && words != null) {
                            val firstWord = words.getRandomElement()
                            val isCorrectTranslationOfWord = Math.random() < 0.5
                            subscriber.onNext(Triple(
                                    Pair(firstWord.text_eng, (if(isCorrectTranslationOfWord) firstWord else words.getRandomElement()).text_spa),
                                    wordCount,
                                    isCorrectTranslationOfWord
                            ))
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