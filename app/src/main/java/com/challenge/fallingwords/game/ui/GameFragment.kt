package com.challenge.fallingwords.game.ui

import android.os.Bundle
import android.view.View
import com.beust.klaxon.Klaxon
import com.challenge.fallingwords.R
import com.challenge.fallingwords.game.domain.WordEngSpa
import com.challenge.fallingwords.game.presenter.GamePresenter
import com.challenge.fallingwords.infrastructure.base.BaseFragment
import com.challenge.fallingwords.infrastructure.di.components.FragmentComponent
import kotlinx.android.synthetic.main.fragment_game.*
import javax.inject.Inject


class GameFragment: BaseFragment(), GamePresenter.View {

    companion object {
        fun newInstance(): GameFragment {
            return GameFragment().apply {
                arguments = Bundle()
            }
        }
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_game

    override fun inject() {
        getComponent(FragmentComponent::class.java).inject(this)
    }

    @Inject lateinit var presenter: GamePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val json = context?.assets?.open("words_v2.json")
        val words = if(json != null) {
            Klaxon().parseArray<WordEngSpa>(json)
        } else {
            emptyList()
        }?.toTypedArray()
        presenter.initialize(this, words)
        setButtonsClickListener()
        presenter.start()
    }

    private fun setButtonsClickListener(){
        yes_button.setOnClickListener { presenter.onYesClicked() }
        no_button.setOnClickListener { presenter.onNoClicked() }
    }

    override fun showNewWords(words: Pair<String, String>) {
        fixed_text_view.text = words.first
        falling_text_view.text = words.second
    }

    override fun showScore(correctCount: Int, wordCount: Int) {
        score_text.text = getString(R.string.score_over, correctCount, wordCount)
    }

    override fun clearScore() {
        score_text.text = ""
    }

    override fun showButtons() {
        yes_button.visibility = View.VISIBLE
        no_button.visibility = View.VISIBLE
    }

    override fun hideButtons() {
        yes_button.visibility = View.INVISIBLE
        no_button.visibility = View.INVISIBLE
    }

    override fun showEndOfGame() {
        presenter.finish()
        activity?.finish()
    }
}