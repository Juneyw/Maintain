package com.yantang.juney.maintain.utils;


import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Log工具类，debug环境在输出所有级别l的og，release环境下只输出warn,error,assert级别的log
 * <p>
 * 参考 {@link  LoggerUtil#LOG_DEBUG_MODE}
 * </p>
 * <p>
 * Created by zengfansheng on 2016/3/24
 * </p>
 */
  public final class LoggerUtil {

    /**
     * debug环境在输出所有级别l的og
     * <br/>
     * release环境下只输出warn,error,assert级别的log
     * <p/>
     * you should config in your build.gradle
     * <pre>
     * buildTypes {
     *    release {
     *      buildConfigField "boolean", "LOG_DEBUG", "false"
     *    }
     * }
     * </pre>
     */
    private static final boolean LOG_DEBUG_MODE = true;

    /**
     * Master switch.To catch debug level info you need set this value below {@link Log#VERBOSE}<br/>
     */
    private static final int LOG_DEBUG_LEVEL = 0;
    /**
     * Master switch.To catch error level info you need set this value below {@link Log#WARN}<br/>
     */
    private static final int LOG_RELEASE_LEVEL = Log.INFO;

    /**
     * debug和release 环境log level switch
     */
    private static final int LOG_LEVEL = LOG_DEBUG_MODE ? LOG_DEBUG_LEVEL : LOG_RELEASE_LEVEL;

    /**
     * Default log tag
     */
    private static final String LOG_TAG = "maintain_info";

    /**
     * init logger
     */
    static {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // 是否显示线程信息 默认显示 上图Thread Infrom的位置
                .methodCount(3)         // 展示方法的行数 默认是2  上图Method的行数
                .methodOffset(1)        // 内部方法调用向上偏移的行数 默认是0
//                .logStrategy(customLog) // 改变log打印的策略一种是写本地，一种是logcat显示 默认是后者（当然也可以自己定义）
                .tag(LOG_TAG)   // 自定义全局tag 默认：PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

    }

    /**
     * Don't let anyone instantiate this class.
     */
    private LoggerUtil() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * Send a {@link Log#VERBOSE} log message.
     *
     * @param obj
     */
    public static void v(Object obj) {
        if (Log.VERBOSE > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(LOG_TAG, msg, Log.VERBOSE, null);
        }
    }

    /**
     * Send a {@link Log#VERBOSE} log message.
     */
    public static void v(String tag, Object obj) {
        if (Log.VERBOSE > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(tag, msg, Log.VERBOSE, null);
        }
    }

    /**
     * Send a {@link #LOG_LEVEL} log message.
     *
     * @param obj
     */
    public static void d(Object obj) {
        if (Log.DEBUG > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(LOG_TAG, msg, Log.DEBUG, null);
        }
    }

    /**
     * Send a {@link Log#DEBUG} log message.
     */
    public static void d(String tag, Object obj) {
        if (Log.DEBUG > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(tag, msg, Log.DEBUG, null);
        }
    }

    /**
     * Send an {@link Log#INFO} log message.
     *
     * @param obj
     */
    public static void i(Object obj) {
        if (Log.INFO > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(LOG_TAG, msg, Log.INFO, null);
        }
    }

    /**
     * Send an {@link Log#INFO} log message.
     */
    public static void i(String tag, Object obj) {
        if (Log.INFO > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(tag, msg, Log.INFO, null);
        }
    }

    /**
     * Send a {@link Log#WARN} log message.
     *
     * @param obj
     */
    public static void w(Object obj) {
        if (Log.WARN > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(LOG_TAG, msg, Log.WARN, null);
        }
    }

    /**
     * Send a {@link Log#WARN} log message.
     */
    public static void w(String tag, Object obj) {
        if (Log.WARN > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(tag, msg, Log.WARN, null);
        }
    }

    /**
     * Send an {@link Log#ERROR} log message.
     *
     * @param obj
     */
    public static void e(Object obj) {
        if (Log.ERROR > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(LOG_TAG, msg, Log.ERROR, null);
        }
    }

    /**
     * Send an {@link Log#ERROR} log message.
     */
    public static void e(String tag, Object obj) {
        if (Log.ERROR > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(tag, msg, Log.ERROR, null);
        }
    }

    /**
     * 避免和{@link LoggerUtil#e(String, Object)}混淆，不对外暴露
     * <p>
     * Send an {@link Log#ERROR} log message.
     * </p>
     */
    private static void e(Throwable throwable, Object obj) {
        if (Log.ERROR > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(LOG_TAG, msg, Log.ERROR, throwable);
        }
    }

    /**
     * Send an {@link Log#ERROR} log message.
     */
    public static void e(String tag, Throwable throwable, Object obj) {
        if (Log.ERROR > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(tag, msg, Log.ERROR, throwable);
        }
    }

    public static void wtf(Object obj) {
        if (Log.ASSERT > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(LOG_TAG, msg, Log.ASSERT, null);
        }
    }

    /**
     * What a Terrible Failure: Report a condition that should never happen. The
     * error will always be logged at level ASSERT with the call stack.
     * Depending on system configuration, a report may be added to the
     * {@link android.os.DropBoxManager} and/or the process may be terminated
     * immediately with an error dialog.
     *
     * @param tag Used to identify the source of a log message.
     * @param obj The message you would like logged.
     */
    public static void wtf(String tag, Object obj) {
        if (Log.ASSERT > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(tag, msg, Log.ASSERT, null);
        }
    }

    /**
     * pretty json
     *
     * @param json
     */
    public static void json(String json) {
        if (Log.DEBUG > LOG_LEVEL) {
            loggerJson(LOG_TAG, json);
        }
    }

    public static void json(String tag, String json) {
        if (Log.DEBUG > LOG_LEVEL) {
            loggerJson(tag, json);
        }
    }

    private static void loggerJson(String tag, String json) {
        Logger.t(tag).json(json);
    }

    /**
     * pretty xml
     *
     * @param xml
     */
    public static void xml(String xml) {
        if (Log.DEBUG > LOG_LEVEL) {
            loggerXml(LOG_TAG, xml);
        }
    }

    public static void xml(String tag, String xml) {
        if (Log.DEBUG > LOG_LEVEL) {
            loggerXml(tag, xml);
        }
    }

    private static void loggerXml(String tag, String xml) {
        Logger.t(tag).xml(xml);
    }

    /**
     * Exception信息输出
     *
     * @param e 输出异常
     */
    public static void printStackTrace(Throwable e) {
        if (Log.ERROR > LOG_LEVEL) {
            e.printStackTrace();
        }
    }

    /**
     * Logger
     *
     * @param tag
     * @param msg
     * @param logLevel
     * @param throwable
     */
    private static void logger(String tag, String msg, int logLevel, Throwable throwable) {
        switch (logLevel) {
            case Log.VERBOSE:
                Logger.t(tag).v(msg);
                break;
            case Log.DEBUG:
                Logger.t(tag).d(msg);
                break;
            case Log.INFO:
                Logger.t(tag).i(msg);
                break;
            case Log.WARN:
                Logger.t(tag).w(msg);
                break;
            case Log.ERROR:
                Logger.t(tag).e(throwable, msg);
                break;
            case Log.ASSERT:
                Logger.t(tag).wtf(msg);
                break;
            default:
                break;
        }
    }

}