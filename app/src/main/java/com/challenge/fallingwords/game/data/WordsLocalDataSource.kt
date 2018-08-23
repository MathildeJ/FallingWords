package com.challenge.fallingwords.game.data

import android.content.Context
import com.beust.klaxon.Klaxon
import com.challenge.fallingwords.game.domain.model.WordEngSpa
import javax.inject.Inject

class WordsLocalDataSource @Inject
constructor(private val context: Context): WordsDataSource {

    override fun wordsList(): Array<WordEngSpa>? {
        val json = context.assets?.open("words_v2.json")
        return if(json != null) {
            Klaxon().parseArray<WordEngSpa>(json)
        } else {
            emptyList()
        }?.toTypedArray()
    }

}