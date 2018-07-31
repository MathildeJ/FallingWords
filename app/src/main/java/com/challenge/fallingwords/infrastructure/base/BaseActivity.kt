package com.challenge.fallingwords.infrastructure.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.challenge.fallingwords.FallingWordsApplication
import com.challenge.fallingwords.infrastructure.di.components.AppComponent
import com.challenge.fallingwords.infrastructure.di.components.DaggerFragmentComponent
import com.challenge.fallingwords.infrastructure.di.components.FragmentComponent
import com.challenge.fallingwords.infrastructure.di.components.HasComponent
import com.challenge.fallingwords.infrastructure.di.modules.ActivityModule

abstract class BaseActivity: AppCompatActivity(), HasComponent<FragmentComponent> {

    private lateinit var fragmentComponent: FragmentComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeInjector()
        injectDependencies()
        setContentView(getLayoutResource())
        injectViews()
    }

    protected abstract fun getLayoutResource(): Int

    private fun injectDependencies() {
        getApplicationComponent().inject(this)
    }

    protected fun getApplicationComponent(): AppComponent {
        return (application as FallingWordsApplication).getAppComponent()
    }

    private fun injectViews() {
        //ButterKnife.bind(this)
    }

    protected fun getActivityModule(): ActivityModule {
        return ActivityModule(this)
    }

    private fun initializeInjector() {
        fragmentComponent = DaggerFragmentComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build()
    }

    override val component: FragmentComponent
        get() = fragmentComponent

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}