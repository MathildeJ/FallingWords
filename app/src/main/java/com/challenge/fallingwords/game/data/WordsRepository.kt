package com.challenge.fallingwords.game.data

import com.challenge.fallingwords.game.domain.model.WordEngSpa
import javax.inject.Inject

class WordsRepository @Inject
constructor(private val wordsDataSource: WordsDataSource){

    fun getWords(): Array<WordEngSpa>?{
        return wordsDataSource.wordsList()
    }
}