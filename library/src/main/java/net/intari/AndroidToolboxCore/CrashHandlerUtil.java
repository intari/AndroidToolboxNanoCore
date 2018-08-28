package net.intari.AndroidToolboxCore;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import net.intari.CustomLogger.CustomLog;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Dmitriy Kazimirov on 17.08.2018.
 * Custom Crash Handler
 * For use as part of reporter
 * Can restart on crash
 * Reports crashes to analytics
 * What it does NOT do:
 * - it does not store crashes on disk to report on next startup (whih is rather GOOD idea...what if it's OOM?)
 * - store crashes and try to upload them via network when it becomes present (on it's own, Analytics services could use offline storage)
 */

public class CrashHandlerUtil implements  java.lang.Thread.UncaughtExceptionHandler {
    public static final String TAG = CrashHandlerUtil.class.getSimpleName();
    private static CrashHandlerUtil instance;
    private Thread.UncaughtExceptionHandler oldHandler;
    private Context context;
    private Intent intentForRestart;
    private static final String APP_CRASH_EVENT_NAME="ApplicationCrashed";
    private static final String APP_CRASH_STACK_TRACE="StackTrace";
    private static final String APP_CRASH_MESSAGE="Message";
    private static final String APP_CRASH_THREAD="Thread";

    private static boolean logToCustomLog=false;
    private static boolean restartOnCrash=false;

    private static final int INITTIAL_CAPACITY=37;
    private static Map<String,Object> eventInfo=new HashMap<>(INITTIAL_CAPACITY);

    /**
     * Should we restart on crash?
     * @return
     */
    public static boolean isRestartOnCrash() {
        return restartOnCrash;
    }

    /**
     * Enables/disables restart on crash (intentForRestart must be set TOO)
     * @param restartOnCrash
     */
    public static void setRestartOnCrash(boolean restartOnCrash) {
        CrashHandlerUtil.restartOnCrash = restartOnCrash;
    }

    /**
     * Returns intent used for restart on crash
     * @return
     */
    public Intent getIntentForRestart() {
        return intentForRestart;
    }

    /**
     * Sets intent used for restart
     * @param intentForRestart
     */
    public void setIntentForRestart(Intent intentForRestart) {
        this.intentForRestart = intentForRestart;
    }

    /**
     * Is sending logs to CustomLog ok?
     * @return
     */
    public static boolean isLoggingEnabled() {
        return logToCustomLog;
    }

    /**
     * Enables/disables logging to CustomLog
     * @param isLog
     */
    public static void setLoggingEnabled(boolean isLog) {
        logToCustomLog=isLog;
    }

    @Deprecated
    public static void setIsLogToCustomLog(boolean isLog) {
        setLoggingEnabled(isLog);
    }


    /**
     * Constructs this handler
     * @param context
     */
    private CrashHandlerUtil(Context context) {
        this.oldHandler=Thread.getDefaultUncaughtExceptionHandler();
        this.context=context;
        if (isLoggingEnabled()) {
            CustomLog.d(TAG,"Creating new handler. Old handler "+this.oldHandler);
        }
    }

    /**
     * Returns instance of this class
     * @return
     */
    public static CrashHandlerUtil getInstance() {
        return instance;
    }

    /**
     * Installs exception handler.
     * Main entry point
     * @param context - app context to use
     */
    public static void installExceptionHandler(Context context) {
        synchronized (TAG) {
            if (instance==null) {
                instance=new CrashHandlerUtil(context);
            }
            Thread.setDefaultUncaughtExceptionHandler(instance);
        }
    }

    /**
     * Installs handler and sets intent to restart in one operation (also enables restarting)
     * @param context
     * @param providedIntentForRestart
     */
    public static void installExceptionHandler(Context context,Intent providedIntentForRestart) {
        synchronized (TAG) {
            if (instance==null) {
                instance=new CrashHandlerUtil(context);
                instance.setIntentForRestart(providedIntentForRestart);
            }
            restartOnCrash=true;
            Thread.setDefaultUncaughtExceptionHandler(instance);
        }
    }

    /**
     * Reports crash
     * Made public because of (planned) other uses)
     * It's VERY good idea for caller   of this functions to catch any and all exceptions from it
     * even if it's 'regular custome' will ignore exceptions  anyway
     * @param t - Thread
     * @param e - Throwable
     */
    public static void reportCrash(Thread t, Throwable e) {
        StringWriter sw2=new StringWriter();
        joinStackTrace(e,sw2);
        if (isLoggingEnabled()) {
            StringWriter sw=new StringWriter();
            PrintWriter pw=new PrintWriter(sw);
            e.printStackTrace(pw);//does it report 'caused by'?
            StringBuilder sb=new StringBuilder();
            sb.append("Thread:");
            sb.append(t.toString());
            sb.append("\nError:\n");
            sb.append(e.toString());
            sb.append("\nAt:");
            sb.append(sw.toString());
            sb.append("\nAt(2):");
            sb.append(sw2);
            CustomLog.e(TAG,sb.toString());
        }

        eventInfo.put(APP_CRASH_THREAD,t.toString());
        eventInfo.put(APP_CRASH_MESSAGE,e.getMessage());
        eventInfo.put(APP_CRASH_STACK_TRACE,sw2);
        CoreUtils.reportAnalyticsEventSync(APP_CRASH_EVENT_NAME,eventInfo);

    }
    /**
     * Method invoked when the given thread terminates due to the
     * given uncaught exception.
     * <p>Any exception thrown by this method will be ignored by the
     * Java Virtual Machine.
     *
     * @param t the thread
     * @param e the exception
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            reportCrash(t,e);
        } catch (Throwable inner) {
            Log.e(TAG,"Crash in crash handler!!!");
            if (isLoggingEnabled()) {
                CustomLog.e(TAG,"Crash in crash handler!!!:"+inner.toString());
            }
        }
        if (intentForRestart!=null) {
            if (restartOnCrash) {
                if (isLoggingEnabled()) {
                    CustomLog.d(TAG,"IntentForRestart is not null - restarting");
                }
                context.startActivity(intentForRestart);
                //System.exit(1);
            } else {
                if (isLoggingEnabled()) {
                    CustomLog.d(TAG,"IntentForRestart is not null but restartOnCrash is false - not restarting");
                }

            }
        }
        if (oldHandler!=null) {
            if (isLoggingEnabled()) {
                CustomLog.d(TAG,"Calling old handler "+oldHandler);
            }
            oldHandler.uncaughtException(t, e);
        }
    }


    //code base on https://stackoverflow.com/questions/1292858/getting-full-string-stack-trace-including-inner-exception
    public static String joinStackTrace(Throwable e) {
        StringWriter writer = null;
        try {
            writer = new StringWriter();
            joinStackTrace(e, writer);
            return writer.toString();
        }
        finally {
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException e1) {
                    // ignore
                }
        }
    }

    public static void joinStackTrace(Throwable e, StringWriter writer) {
        PrintWriter printer = null;
        try {
            printer = new PrintWriter(writer);

            while (e != null) {

                printer.println(e);
                StackTraceElement[] trace = e.getStackTrace();
                for (int i = 0; i < trace.length; i++)
                    printer.println("\tat " + trace[i]);

                e = e.getCause();
                if (e != null)
                    printer.println("Caused by:\r\n");
            }
        }
        finally {
            if (printer != null)
                printer.close();
        }
    }
}
