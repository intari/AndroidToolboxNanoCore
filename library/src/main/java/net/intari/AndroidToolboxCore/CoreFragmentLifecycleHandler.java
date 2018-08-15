package net.intari.AndroidToolboxCore;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

/**
 * Created by Dmitriy Kazimirov on 15.08.2018.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class CoreFragmentLifecycleHandler extends FragmentManager.FragmentLifecycleCallbacks {
    public static final String TAG = CoreFragmentLifecycleHandler.class.getSimpleName();

    /**
     * Called right before the fragment's {@link Fragment#onAttach(Context)} method is called.
     * This is a good time to inject any required dependencies for the fragment before any of
     * the fragment's lifecycle methods are invoked.
     *
     * @param fm      Host FragmentManager
     * @param f       Fragment changing state
     * @param context Context that the Fragment is being attached to
     */
    @Override
    public void onFragmentPreAttached(FragmentManager fm, Fragment f, Context context) {
        super.onFragmentPreAttached(fm, f, context);
    }

    /**
     * Called after the fragment has been attached to its host. Its host will have had
     * <code>onAttachFragment</code> called before this call happens.
     *
     * @param fm      Host FragmentManager
     * @param f       Fragment changing state
     * @param context Context that the Fragment was attached to
     */
    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        super.onFragmentAttached(fm, f, context);
    }

    /**
     * Called right before the fragment's {@link Fragment#onCreate(Bundle)} method is called.
     * This is a good time to inject any required dependencies or perform other configuration
     * for the fragment.
     *
     * @param fm                 Host FragmentManager
     * @param f                  Fragment changing state
     * @param savedInstanceState Saved instance bundle from a previous instance
     */
    @Override
    public void onFragmentPreCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentPreCreated(fm, f, savedInstanceState);
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onCreate(Bundle)}. This will only happen once for any given
     * fragment instance, though the fragment may be attached and detached multiple times.
     *
     * @param fm                 Host FragmentManager
     * @param f                  Fragment changing state
     * @param savedInstanceState Saved instance bundle from a previous instance
     */
    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onActivityCreated(Bundle)}. This will only happen once for any given
     * fragment instance, though the fragment may be attached and detached multiple times.
     *
     * @param fm                 Host FragmentManager
     * @param f                  Fragment changing state
     * @param savedInstanceState Saved instance bundle from a previous instance
     */
    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState);
    }

    /**
     * Called after the fragment has returned a non-null view from the FragmentManager's
     * request to {@link Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     *
     * @param fm                 Host FragmentManager
     * @param f                  Fragment that created and owns the view
     * @param v                  View returned by the fragment
     * @param savedInstanceState Saved instance bundle from a previous instance
     */
    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState);
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onStart()}.
     *
     * @param fm Host FragmentManager
     * @param f  Fragment changing state
     */
    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        super.onFragmentStarted(fm, f);
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onResume()}.
     *
     * @param fm Host FragmentManager
     * @param f  Fragment changing state
     */
    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        super.onFragmentResumed(fm, f);
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onPause()}.
     *
     * @param fm Host FragmentManager
     * @param f  Fragment changing state
     */
    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        super.onFragmentPaused(fm, f);
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onStop()}.
     *
     * @param fm Host FragmentManager
     * @param f  Fragment changing state
     */
    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        super.onFragmentStopped(fm, f);
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onSaveInstanceState(Bundle)}.
     *
     * @param fm       Host FragmentManager
     * @param f        Fragment changing state
     * @param outState Saved state bundle for the fragment
     */
    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        super.onFragmentSaveInstanceState(fm, f, outState);
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onDestroyView()}.
     *
     * @param fm Host FragmentManager
     * @param f  Fragment changing state
     */
    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentViewDestroyed(fm, f);
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onDestroy()}.
     *
     * @param fm Host FragmentManager
     * @param f  Fragment changing state
     */
    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentDestroyed(fm, f);
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onDetach()}.
     *
     * @param fm Host FragmentManager
     * @param f  Fragment changing state
     */
    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
        super.onFragmentDetached(fm, f);
    }
}
