package com.challenge.fallingwords.game.ui

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.challenge.fallingwords.infrastructure.base.SingleFragmentActivity

class GameActivity: SingleFragmentActivity(){

    companion object {
        fun getLaunchIntent(context: Context): Intent {
            return Intent(context, GameActivity::class.java)
        }
    }

    override val fragment: Fragment
        get() = GameFragment.newInstance()

}