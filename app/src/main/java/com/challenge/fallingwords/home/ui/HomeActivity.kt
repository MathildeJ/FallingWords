package com.challenge.fallingwords.home.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import com.challenge.fallingwords.infrastructure.base.SingleFragmentActivity

class HomeActivity: SingleFragmentActivity(){

    override val fragment: Fragment
        get() = HomeFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }
}