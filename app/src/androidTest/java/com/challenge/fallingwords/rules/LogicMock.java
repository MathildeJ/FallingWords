package com.challenge.fallingwords.rules;

import android.support.test.InstrumentationRegistry;

import com.challenge.fallingwords.FallingWordsApplication;
import com.challenge.fallingwords.infrastructure.di.components.AppComponent;
import com.challenge.fallingwords.infrastructure.di.modules.LogicModule;
import com.challenge.fallingwords.infrastructure.di.modules.MainModule;

import it.cosenonjaviste.daggermock.DaggerMockRule;

public class LogicMock extends DaggerMockRule<AppComponent> {

    public static LogicMock create(){
        return new LogicMock(AppComponent.class, new LogicModule(), new MainModule(getApp()));
    }

    public LogicMock(Class<AppComponent> componentClass, Object... modules) {
        super(componentClass, modules);
        set(new ComponentSetter<AppComponent>() {
            @Override
            public void setComponent(AppComponent appComponent) {
                getApp().setComponent(appComponent);
            }
        });
    }

    private static FallingWordsApplication getApp() {
        return (FallingWordsApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    }
}
