package com.joka.http.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created on 2020/4/13 8:45 下午.
 *
 * @author zhaozengjie
 * Description :
 */
public class Platform {
    private static final Platform PLATFORM = findPlatform();

    public static Platform get() {
        return PLATFORM;
    }

    private static Platform findPlatform() {
        return new Platform();
    }

    public Executor defaultCallbackExecutor() {
        return Executors.newCachedThreadPool();
    }

    public void execute(Runnable runnable) {
        defaultCallbackExecutor().execute(runnable);
    }


}
