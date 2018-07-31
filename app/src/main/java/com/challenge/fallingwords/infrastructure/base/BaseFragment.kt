package com.challenge.fallingwords.infrastructure.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.challenge.fallingwords.infrastructure.di.components.HasComponent

abstract class BaseFragment : Fragment() {

    protected abstract val fragmentLayout: Int

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        inject()
        return inflater.inflate(fragmentLayout, container, false)
    }

    protected fun <C> getComponent(componentType: Class<C>): C {
        return componentType.cast((activity as HasComponent<C>).component)
    }

    protected abstract fun inject()
}