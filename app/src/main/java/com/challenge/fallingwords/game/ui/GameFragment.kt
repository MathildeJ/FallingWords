package com.challenge.fallingwords.game.ui

import android.os.Bundle
import com.challenge.fallingwords.R
import com.challenge.fallingwords.infrastructure.base.BaseFragment
import com.challenge.fallingwords.infrastructure.di.components.FragmentComponent

class GameFragment: BaseFragment() {

    override val fragmentLayout: Int
        get() = R.layout.fragment_game

    override fun inject() {
        getComponent(FragmentComponent::class.java).inject(this)
    }

    companion object {
        fun newInstance(): GameFragment {
            return GameFragment().apply {
                arguments = Bundle()
            }
        }
    }
}