package net.intari.AndroidToolboxCore;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by Dmitriy Kazimirov on 15.08.2018.
 */
public class CoreLifecycleHandler implements Application.ActivityLifecycleCallbacks {
    private static int resumed;
    private static int paused;
    private static int started;
    private static int stopped;

    private long actStarted=0L;
    private long actResumed=0L;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ++resumed;
        actResumed = System.currentTimeMillis();
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ++paused;
        long stopTime= System.currentTimeMillis();
        long timePassed=(stopTime-actResumed)/ Constants.MS_PER_SECOND;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ++started;
        actStarted =  System.currentTimeMillis();
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
        long stopTime= System.currentTimeMillis();
        long timePassed=(stopTime-actStarted)/ Constants.MS_PER_SECOND;

    }

    public static boolean isApplicationVisible() {
        return started > stopped;
    }

    public static boolean isApplicationInForeground() {
        return resumed > paused;
    }


}