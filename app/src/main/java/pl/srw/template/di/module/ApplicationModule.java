package pl.srw.template.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Global dependencies module
 */
@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
