package pl.srw.mfvp;

import java.util.HashMap;

import pl.srw.mfvp.di.component.MvpActivityScopeComponent;
import pl.srw.mfvp.di.component.MvpFragmentScopeComponent;
import pl.srw.mfvp.view.fragment.MvpFragmentScopedFragment;
import timber.log.Timber;

/**
 * Dependency components holder
 */
final class DependencyComponentManager {

    private final static DependencyComponentManager INSTANCE = new DependencyComponentManager();

    private final HashMap<Class<? extends MvpActivity>, MvpActivityScopeComponent> activityComponentsMap;
    private final HashMap<Class<? extends MvpFragmentScopedFragment>, MvpFragmentScopeComponent> fragmentComponentMap;

    static DependencyComponentManager getInstance() {
        return INSTANCE;
    }

    private DependencyComponentManager() {
        this.activityComponentsMap = new HashMap<>(1);
        this.fragmentComponentMap = new HashMap<>(0);
    }

    public <C extends MvpActivityScopeComponent> C getComponentFor(MvpActivity<C> activity) {
        final Class<? extends MvpActivity> activityClass = activity.getClass();
        if (!activityComponentsMap.containsKey(activityClass)) {
            Timber.v("preparing component for %s", activityClass.getSimpleName());
            activityComponentsMap.put(activityClass, activity.prepareComponent());
        }
        return (C) activityComponentsMap.get(activityClass);
    }

    public void releaseComponentFor(MvpActivity activity) {
        final Class<? extends MvpActivity> activityClass = activity.getClass();
        Timber.v("releasing component for %s", activityClass.getSimpleName());
        activityComponentsMap.remove(activityClass);
    }

    public <C extends MvpFragmentScopeComponent, AC extends MvpActivityScopeComponent>
        C getComponentFor(MvpFragmentScopedFragment<C, AC> fragment, AC activityComponent) {

        final Class<? extends MvpFragmentScopedFragment> fragmentClass = fragment.getClass();
        if (!fragmentComponentMap.containsKey(fragmentClass)) {
            Timber.v("preparing component for %s", fragmentClass.getSimpleName());
            fragmentComponentMap.put(fragmentClass, fragment.getFragmentComponent(activityComponent));
        }
        return (C) fragmentComponentMap.get(fragmentClass);
    }

    public void releaseComponentFor(MvpFragmentScopedFragment fragment) {
        final Class<? extends MvpFragmentScopedFragment> fragmentClass = fragment.getClass();
        Timber.v("releasing component for %s", fragmentClass.getSimpleName());
        fragmentComponentMap.remove(fragmentClass);
    }
}
