package com.challenge.fallingwords.game.data

import com.challenge.fallingwords.game.domain.model.WordEngSpa

interface WordsDataSource {

    fun wordsList(): Array<WordEngSpa>?
}