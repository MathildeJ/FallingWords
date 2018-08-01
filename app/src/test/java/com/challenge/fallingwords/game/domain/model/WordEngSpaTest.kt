package com.challenge.fallingwords.game.domain.model

import org.junit.Assert.*
import org.junit.Test

class WordEngSpaTest{

    companion object {
        private const val textEng = "cat"
        private const val textSpa = "gato"
    }

    @Test
    fun testWordEngSpaIsCreatedCorrectly() {
        val wordEngSpa = WordEngSpa(textEng, textSpa)

        assertEquals(wordEngSpa.text_eng, textEng)
        assertEquals(wordEngSpa.text_spa, textSpa)
    }
}