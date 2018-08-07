package com.example.sample.base;

import android.app.Activity;

import java.util.Stack;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public class ActivityManager {
    private static Stack<Activity> activityStack;

    private ActivityManager() {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
    }

    public static ActivityManager getInstance() {
        return SingletonLoader.INSTANCE;
    }

    /**
     * 获取Activity个数
     *
     * @return
     */
    public int getActivityCount() {
        return activityStack.size();
    }

    /**
     * 移除某个Activity
     *
     * @param activity 要移除的Activity
     */
    public void pop(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 移除指定类名的Activity
     */
    public void pop(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                pop(activity);
                break;
            }
        }
    }

    /**
     * 通过class获取某个Activity
     */
    public Activity getActivityByClass(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }

        return null;
    }

    /**
     * 获得当前栈顶Activity
     *
     * @return 栈顶的Activity
     */
    public Activity currentActivity() {
        int len = activityStack.size();
        if (len == 0) {
            return null;
        }
        return activityStack.lastElement();
    }

    /**
     * 把Activity推入栈中
     *
     * @param activity 要放入栈的activity
     */
    public void push(Activity activity) {
        activityStack.push(activity);
    }

    /**
     * 除了当前Activity,退出栈中其它所有Activity
     */
    public void popAllActivityExceptThis() {
        int len = activityStack.size();
        if (len == 0) {
            return;
        }

        Activity current = currentActivity();
        for (int i = len - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (activity == null || (activity == current)) {
                continue;
            }
            pop(activity);
        }
    }

    /**
     * 移除所有Activity
     */
    public void popAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
//                MyLog.v("JVGuestDataMoveActivity", "activity's name:" +
// activity
//                        .getClass().getSimpleName());
                activity.finish();
            }
        }
        activityStack.clear();
    }

    // -----------------------------------------------

    /**
     * 当前类(ActivityManager.java)的单例
     */
    private static class SingletonLoader {
        private static final ActivityManager INSTANCE = new ActivityManager();
    }
}
