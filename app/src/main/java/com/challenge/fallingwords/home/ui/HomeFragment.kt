package com.challenge.fallingwords.home.ui

import android.os.Bundle
import android.view.View
import com.challenge.fallingwords.R
import com.challenge.fallingwords.game.ui.GameActivity
import com.challenge.fallingwords.home.presenter.HomePresenter
import com.challenge.fallingwords.infrastructure.base.BaseFragment
import com.challenge.fallingwords.infrastructure.di.components.FragmentComponent
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment: BaseFragment(), HomePresenter.View {

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment().apply {
                arguments = Bundle()
            }
        }
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_home

    override fun inject() {
        getComponent(FragmentComponent::class.java).inject(this)
    }

    @Inject lateinit var presenter: HomePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonListener()
        presenter.initialize(this)
    }

    private fun setButtonListener(){
        start_button.setOnClickListener { presenter.onStartClicked() }
    }

    override fun showGame() {
        if(context != null) startActivity(GameActivity.getLaunchIntent(context!!))
    }
}