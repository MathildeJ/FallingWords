package com.challenge.fallingwords.game.domain.model

import com.beust.klaxon.Json

data class WordEngSpa(@Json(name = "text_eng") val textEng: String,
                      @Json(name = "text_spa") val textSpa: String)
