package net.intari.AndroidToolboxCore.observers;

/**
 * Created by Dmitriy Kazimirov on 15.08.2018.
 */
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import net.intari.AndroidToolboxCore.Constants;
import net.intari.AndroidToolboxCore.CoreUtils;
import net.intari.CustomLogger.CustomLog;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class ActivityLifecycleObserver implements Application.ActivityLifecycleCallbacks {
    public static final String TAG = ActivityLifecycleObserver.class.getSimpleName();

    private static int resumed;
    private static int paused;
    private static int started;
    private static int stopped;

    private long actStarted=0L;
    private long actResumed=0L;


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,activity.getClass().getSimpleName()+" was created");
        }

        if (activity instanceof AppCompatActivity) {
            if (CoreUtils.isReportLifecycleEventsForDebug()) {
                CustomLog.d(TAG,activity.getClass().getSimpleName()+" is AppCompat activity so we should have fragment manager");
            }
            FragmentManager fragmentManager=((AppCompatActivity) activity).getSupportFragmentManager();
            fragmentManager.registerFragmentLifecycleCallbacks(new FragmentLifecycleObserver(),true);
        } else {
            if (CoreUtils.isReportLifecycleEventsForDebug()) {
                CustomLog.d(TAG,activity.getClass().getSimpleName()+" is NOT AppCompat activity so no supportFragmentManager support");
            }
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,activity.getClass().getSimpleName()+" was destroyed");
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ++resumed;
        actResumed = System.currentTimeMillis();
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,activity.getClass().getSimpleName()+" was resumed");
        }
        if (CoreUtils.isReportLifecycleEventsForAnalytics()) {
            TreeMap<String, Object> eventAttributes=new TreeMap<String, Object>();
            eventAttributes.put("activity",activity.getClass().getSimpleName());
            eventAttributes.put("activityFullName",activity.getClass().getCanonicalName());
            switch (CoreUtils.getReportLifecycleForAnalyticsAs()) {
                case JUST_EVENT:
                    CoreUtils.reportAnalyticsEvent("activityResumed", eventAttributes);
                    break;
                case PREFIXED_FULL_CLASS_NAME:
                    CoreUtils.reportAnalyticsEvent(activity.getClass().getCanonicalName() + " resumed", eventAttributes);
                    break;
                case PREFIXED_SHORT_CLASS_NAME:
                    CoreUtils.reportAnalyticsEvent(activity.getClass().getSimpleName() + " resumed", eventAttributes);
                    break;
            }

        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ++paused;
        long stopTime= System.currentTimeMillis();
        long timePassed=(stopTime-actResumed)/ Constants.MS_PER_SECOND;
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,activity.getClass().getSimpleName()+" was paused after "+timePassed+" seconds");
        }
        if (CoreUtils.isReportLifecycleEventsForAnalytics_OnlyStart()) {
            return;
        }
        if (CoreUtils.isReportLifecycleEventsForAnalytics()) {
            TreeMap<String, Object> eventAttributes=new TreeMap<String, Object>();
            eventAttributes.put("activity",activity.getClass().getSimpleName());
            eventAttributes.put("activityFullName",activity.getClass().getCanonicalName());
            eventAttributes.put("timePassedInSeconds",timePassed);

            switch (CoreUtils.getReportLifecycleForAnalyticsAs()) {
                case JUST_EVENT:
                    CoreUtils.reportAnalyticsEvent("activityPaused", eventAttributes);
                    break;
                case PREFIXED_FULL_CLASS_NAME:
                    CoreUtils.reportAnalyticsEvent(activity.getClass().getCanonicalName() + " paused", eventAttributes);
                    break;
                case PREFIXED_SHORT_CLASS_NAME:
                    CoreUtils.reportAnalyticsEvent(activity.getClass().getSimpleName() + " paused", eventAttributes);
                    break;
            }

        }

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,activity.getClass().getSimpleName()+" was saving instance state,state:"+outState);
        }

    }

    @Override
    public void onActivityStarted(Activity activity) {
        ++started;
        actStarted =  System.currentTimeMillis();
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,activity.getClass().getSimpleName()+" was started");
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
        long stopTime= System.currentTimeMillis();
        long timePassed=(stopTime-actStarted)/ Constants.MS_PER_SECOND;
        if (CoreUtils.isReportLifecycleEventsForDebug()) {
            CustomLog.d(TAG,activity.getClass().getSimpleName()+" was stopped after "+timePassed+" seconds");
        }
    }

    public static boolean isApplicationVisible() {
        return started > stopped;
    }

    public static boolean isApplicationInForeground() {
        return resumed > paused;
    }


}
