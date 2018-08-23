package net.intari.AndroidToolboxCore.observers;

/**
 * Created by Dmitriy Kazimirov on 15.08.2018.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks;
import android.view.View;

import net.intari.AndroidToolboxCore.Constants;
import net.intari.AndroidToolboxCore.CoreUtils;
import net.intari.CustomLogger.CustomLog;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Dmitriy Kazimirov on 15.08.2018.
 * TODO:it IS possible to have mor than one fragment on screen. resume/paused couters don't make sense (until they are really per-fragment)
 */
public class FragmentLifecycleObserver extends FragmentLifecycleCallbacks {
    public static final String TAG = FragmentLifecycleObserver.class.getSimpleName();

    private static int resumed;
    private static int paused;
    private static int started;
    private static int stopped;

    private Map<Fragment,Long> fragmentStartedMap=new HashMap<>();
    private Map<Fragment,Long> fragmentResumedMap=new HashMap<>();

    /**
     * Called right before the fragment's {@link Fragment#onAttach(Context)} method is called.
     * This is a good time to inject any required dependencies or perform other configuration
     * for the fragment before any of the fragment's lifecycle methods are invoked.
     *
     * @param fm      Host FragmentManager
     * @param f       Fragment changing state
     * @param context Context that the Fragment is being attached to
     */
    @Override
    public void onFragmentPreAttached(FragmentManager fm, Fragment f, Context context) {
        super.onFragmentPreAttached(fm, f, context);
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,f.getClass().getSimpleName()+" was pre-attached to "+context);
        }
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
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,f.getClass().getSimpleName()+" was attached to "+context);
        }
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
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,f.getClass().getSimpleName()+" was pre-created,savedInstanceState was:"+savedInstanceState);
        }
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
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,f.getClass().getSimpleName()+" was created,savedInstanceState was:"+savedInstanceState);
        }
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
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,f.getClass().getSimpleName()+",onActivityCreated done, savedInstanceState was:"+savedInstanceState);
        }

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
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,f.getClass().getSimpleName()+",onCreateView done, savedInstanceState was:"+savedInstanceState);
        }

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
        //++started;
        fragmentStartedMap.put(f,System.currentTimeMillis());

        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,f.getClass().getSimpleName()+" started");
        }

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
        //++resumed;

        fragmentResumedMap.put(f,System.currentTimeMillis());

        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,f.getClass().getSimpleName()+" resumed");
        }
        if (CoreUtils.isReportLifecycleEventsForAnalytics()) {
            Map<String, Object> eventAttributes=new TreeMap<String, Object>();
            eventAttributes.put("fragment",f.getClass().getSimpleName());
            eventAttributes.put("fragmentFullName",f.getClass().getCanonicalName());
            switch (CoreUtils.getReportLifecycleForAnalyticsAs()) {
                case JUST_EVENT:
                    CoreUtils.reportAnalyticsEvent("fragmentResumed",eventAttributes);
                    break;
                case PREFIXED_FULL_CLASS_NAME:
                    CoreUtils.reportAnalyticsEvent(f.getClass().getCanonicalName()+" resumed",eventAttributes);
                    break;
                case PREFIXED_SHORT_CLASS_NAME:
                    CoreUtils.reportAnalyticsEvent(f.getClass().getSimpleName()+" resumed",eventAttributes);
                    break;
            }
        }
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
        ++paused;
        long stopTime= System.currentTimeMillis();
        Long startTime=fragmentResumedMap.get(f);
        if (startTime==null) {
            startTime=0L;
        }
        long timePassed=(stopTime-startTime)/ Constants.MS_PER_SECOND;
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,f.getClass().getSimpleName()+" paused after "+timePassed+" seconds");
        }
        if (CoreUtils.isReportLifecycleEventsForAnalytics_OnlyStart()) {
            return;
        }
        if (CoreUtils.isReportLifecycleEventsForAnalytics()) {
            Map<String, Object> eventAttributes=new TreeMap<String, Object>();
            eventAttributes.put("fragment",f.getClass().getSimpleName());
            eventAttributes.put("fragmentFullName",f.getClass().getCanonicalName());
            eventAttributes.put("timePassedInSeconds",timePassed);
            switch (CoreUtils.getReportLifecycleForAnalyticsAs()) {
                case JUST_EVENT:
                    CoreUtils.reportAnalyticsEvent("fragmentPaused", eventAttributes);
                    break;
                case PREFIXED_FULL_CLASS_NAME:
                    CoreUtils.reportAnalyticsEvent(f.getClass().getCanonicalName() + " paused", eventAttributes);
                    break;
                case PREFIXED_SHORT_CLASS_NAME:
                    CoreUtils.reportAnalyticsEvent(f.getClass().getSimpleName() + " paused", eventAttributes);
                    break;
            }
        }


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
        ++stopped;
        long stopTime= System.currentTimeMillis();
        Long startTime=fragmentStartedMap.get(f);
        if (startTime==null) {
            startTime=0L;
        }

        long timePassed=(stopTime-startTime)/ Constants.MS_PER_SECOND;

        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,f.getClass().getSimpleName()+" stopped after "+timePassed+" seconds");
        }

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
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,f.getClass().getSimpleName()+",onSaveInstanceState");
        }
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
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,f.getClass().getSimpleName()+",onDestroyView");
        }

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
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,f.getClass().getSimpleName()+",onDestroy(ed)");
        }

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
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,f.getClass().getSimpleName()+",onDetach(ed)");
        }
    }
}
