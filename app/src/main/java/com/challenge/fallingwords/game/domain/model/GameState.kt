package com.challenge.fallingwords.game.domain.model

data class GameState(val words: WordEngSpa, val wordCount: Int, val isCorrectTranslationOfWord: Boolean)