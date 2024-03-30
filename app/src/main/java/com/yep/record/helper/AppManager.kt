package com.yep.record.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.os.Environment
import android.util.Log
import java.lang.ref.WeakReference
import java.util.Stack

object AppManager {
    private const val TAG = "AppManager"
    /** Application 对象 */
    private var mApplication: Application? = null

    /** 保存 Activity 对象的堆栈 */
    private val activityStack: Stack<WeakReference<Activity>> = Stack()

    /** 忽略列表 */
    private val ignoreActivitys = arrayListOf<Class<out Activity>>()

    /** 前台界面个数 */
    private var foregroundCount = 0

    /** App 前后台切换回调 */
    private var mAppForgrountStatusChangeCallback: ((Boolean) -> Unit)? = null

    /** Activity 生命周期回调接口*/
    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            onCreate(activity)
            Log.i(TAG, "onActivityCreated==" + activity.javaClass.name)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            Log.i(TAG, "onActivitySaveInstanceState==" + activity.javaClass.name)
        }

        override fun onActivityStarted(activity: Activity) {
            if (foregroundCount == 0) {
                // App 回到前台
                mAppForgrountStatusChangeCallback?.invoke(true)
            }
            foregroundCount++
            Log.i(TAG, "onActivityStarted==" + activity.javaClass.name)
        }

        override fun onActivityResumed(activity: Activity) {
            Log.i(TAG, "onActivityResumed==" + activity.javaClass.name)
        }

        override fun onActivityPaused(activity: Activity) {
            Log.i(TAG, "onActivityPaused==" + activity.javaClass.name)
        }

        override fun onActivityStopped(activity: Activity) {
            foregroundCount--
            if (foregroundCount == 0) {
                // App 退到后台
                mAppForgrountStatusChangeCallback?.invoke(false)
            }
            Log.i(TAG, "onActivityStopped==" + activity.javaClass.name)
        }

        override fun onActivityDestroyed(activity: Activity) {
            onDestroy(activity)
            Log.i(TAG, "onActivityDestroyed==" + activity.javaClass.name)
        }
    }

    /**
     * 获取前台界面数量
     *
     * @return 前台 Activity 数量
     */
    @JvmStatic
    fun getForegroundCount(): Int {
        return foregroundCount
    }

    /**
     * 应用是否在前台
     *
     * @return 应用是否在前台
     */
    @JvmStatic
    fun isForeground(): Boolean {
        val activityManager = getContext().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val packageName = getContext().packageName
        // 获取Android设备中所有正在运行的App
        val appProcesses = activityManager.runningAppProcesses ?: return false
        for (appProcess in appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName == packageName && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true
            }
        }
        return false
    }

    /**
     * 注册 Application
     *
     * @param application 应用 [Application] 对象
     */
    fun register(application: Application) {
        mApplication = application
        application.unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        application.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
    }

    /**
     * 设置 App 前后台状态切换监听
     *
     * @param onChange 切换回调
     * - true：回到前台 or false：退到后台
     */
    @JvmStatic
    fun setOnAppForgroundStatusChangeListener(onChange: (Boolean) -> Unit) {
        mAppForgrountStatusChangeCallback = onChange
    }

    /**
     * 获取可用 Application 对象
     */
    @JvmStatic
    fun getApplication(): Application {
        if (mApplication == null) {
            register(getApplicationByReflect())
            if (mApplication == null) {
                throw NullPointerException("Application must not be null! Please register AppManager in your Application start！")
            }
        }
        return mApplication!!
    }

    /**
     * 获取 [Context] 对象
     * - 优先获取栈顶的 [Activity] 对象，若没有，则返回 [Application] 对象
     *
     * @return [Context] 对象
     */
    @JvmStatic
    fun getContext(): Context {
        return peekActivity() ?: getApplication()
    }

    /**
     * 通过反射获取当前 Application 对象
     *
     * @return 当前 Application 对象
     */
    private fun getApplicationByReflect(): Application {
        try {
            @SuppressLint("PrivateApi")
            val activityThread = Class.forName("android.app.ActivityThread")
            val thread = activityThread.getMethod("currentActivityThread").invoke(null)
            val app = activityThread.getMethod("getApplication").invoke(thread)
                    ?: throw NullPointerException("u should init first")
            return app as Application
        } catch (e: Exception) {
            Log.e(TAG, "getApplicationByReflect", e.message?.let { Throwable(it) })
        }
        throw NullPointerException("u should init first")
    }

    /**
     * 将 Activity 类对象添加到忽略列表
     */
    @JvmStatic
    fun addToIgnore(vararg clazzs: Class<out Activity>) {
        ignoreActivitys.addAll(clazzs)
    }

    /**
     * 添加 Activity 到堆栈
     *
     * @param activity Activity 对象
     */
    @JvmStatic
    fun onCreate(activity: Activity?) {
        if (activity != null && ignoreActivitys.contains(activity.javaClass)) {
            // 不添加在忽略列表中的 Activity
            return
        }
        add(activity)
    }

    /**
     * 将 Activity 从堆栈移除
     *
     * @param activity Activity 对象
     */
    @JvmStatic
    fun onDestroy(activity: Activity?) {
        if (activity != null && ignoreActivitys.contains(activity.javaClass)) {
            // 不移除在忽略列表中的 Activity
            return
        }
        remove(activity)
    }

    /**
     * 判断当前堆栈中是否存在对应 Activity
     *
     * @param clazz Activity 类对象
     */
    @JvmStatic
    fun contains(clazz: Class<out Activity>): Boolean {
        return activityStack.count { it.get()?.javaClass == clazz } > 0
    }

    /**
     * 将 Activity 加入堆栈
     */
    @JvmStatic
    fun add(activity: Activity?) {
        if (activity == null) {
            return
        }
        activityStack.add(WeakReference(activity))
    }

    /**
     * 将 Activity 从堆栈移除
     *
     * @param activity Activity 对象
     */
    @JvmStatic
    fun remove(activity: Activity?) {
        if (activity == null) {
            return
        }
        var remove: WeakReference<Activity>? = null
        activityStack.forEach { weak ->
            if (weak.get() == activity) {
                remove = weak
            }
        }
        activityStack.remove(remove)
    }

    /**
     * 结束指定 Activity 之外的其他 Activity
     *
     * @param activity 指定不关闭的 Activity
     */
    @JvmStatic
    fun finishAllWithout(activity: Activity?) {
        if (activity == null) {
            return
        }
        remove(activity)
        finishAllActivity()
        add(activity)
    }

    /**
     * 结束指定 Activity 之外的其他 Activity
     *
     * @param clazz 指定不关闭的 Activity
     */
    @JvmStatic
    fun finishAllWithout(vararg clazz: Class<out Activity>) {
        if (clazz.isEmpty()) {
            return
        }
        val ls = arrayListOf<Activity>()
        for (clz in clazz) {
            val activity = getActivity(clz)
            if (null != activity) {
                ls.add(activity)
            }
        }
        if (ls.isEmpty()) {
            return
        }
        for (activity in ls) {
            remove(activity)
        }
        finishAllActivity()
        for (activity in ls) {
            add(activity)
        }
    }

    /**
     * 结束指定 Activity
     *
     * @param clazz Activity 类对象
     */
    @JvmStatic
    fun finishActivity(clazz: Class<out Activity>) {
        val del: Activity? = activityStack.lastOrNull { it.get()?.javaClass == clazz }?.get()
        del?.finish()
    }

    /**
     * 结束指定 Activity
     *
     * @param clazzs Activity 类对象
     */
    @JvmStatic
    fun finishActivities(vararg clazzs: Class<out Activity>) {
        for (clazz in clazzs) {
            finishActivity(clazz)
        }
    }

    /**
     * 获取栈顶的 Activity
     *
     * @return 栈顶的 Activity 对象
     */
    @JvmStatic
    fun peekActivity(): Activity? {
        return if (activityStack.isEmpty()) {
            null
        } else {
            activityStack.peek().get()
        }
    }

    /**
     * 根据类，获取 Activity 对象
     *
     * @param clazz  Activity 类
     * @param A      Activity 类型
     *
     * @return       Activity 对象
     */
    @JvmStatic
    fun <A : Activity> getActivity(clazz: Class<A>): A? {
        @Suppress("UNCHECKED_CAST")
        return activityStack.firstOrNull { it.get()?.javaClass == clazz }?.get() as A?
    }

    /**
     * 根据下标，获取 Activity 对象
     *
     * @param index  Activity 下标
     *
     * @return       Activity 对象
     */
    @JvmStatic
    fun getActivity(index: Int): Activity? {
        return activityStack[index]?.get()
    }

    /**
     * 获取堆栈中 Activity 数量
     *
     * @return Activity 数量
     */
    @JvmStatic
    fun getStackSize(): Int {
        return activityStack.size
    }

    /**
     * 结束所有 Activity
     */
    @JvmStatic
    private fun finishAllActivity() {
        for (weak in activityStack) {
            weak.get()?.finish()
        }
        activityStack.clear()
    }

    /**
     * 退出应用程序
     */
//    @JvmStatic
//    fun appExit() {
//        try {
//            val activityMgr = getContext().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//            activityMgr.killBackgroundProcesses(getContext().packageName)
//            finishAllActivity()
//            exitProcess(0)
//        } catch (e: Exception) {
//            Logger.e("AppManager_appExit=" + e.message)
//        }
//    }

    /** 图片存放全局路径 */
    private const val PIC_PATH: String = "/tiSports"
    fun getGlobalPicPath(): String {
        return getGlobalPath("/pic")
    }

    /** 获取全局路径 */
    fun getGlobalPath(pathName: String) : String {
        var sdPath = ""
        //判断手机已插入sd卡
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            //storage/emulated/0/--需提前申请sd卡权限
            val path = Environment.getExternalStorageDirectory().canonicalPath
            sdPath = path + PIC_PATH + pathName
        }
        return sdPath
    }

    fun getActivity(): Activity? {
        var context: Context? = getContext()
        while (context !is Activity && context is ContextWrapper) {
            context = context.baseContext
        }
        if (context is Activity) {
            return context
        }
        return null
    }
}