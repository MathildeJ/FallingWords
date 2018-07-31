package com.challenge.fallingwords.infrastructure.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.challenge.fallingwords.R

abstract class SingleFragmentActivity : BaseActivity() {

    override fun getLayoutResource(): Int {
        return R.layout.activity_single_fragment
    }

    protected abstract val fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = fragment
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_container, fragment, fragment.javaClass.name)
        ft.commitAllowingStateLoss()
    }

}