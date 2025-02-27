/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2020, FrostWire(R). All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.joker.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is not final, but it's not meant to be inherited but
 * only in very specific situations.
 *
 * @author gubatron
 * @author aldenml
 */
public class ThreadPool extends ThreadPoolExecutor {
    private static final long THREAD_STACK_SIZE = 1024*4;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String name;


    public ThreadPool(String name, int maximumPoolSize, BlockingQueue<Runnable> workQueue, boolean daemon) {
        super(maximumPoolSize, maximumPoolSize, 1L, TimeUnit.SECONDS, workQueue, new PoolThreadFactory(daemon));
        this.name = name;
    }

    public ThreadPool(String name, int corePoolSize, int maximumPoolSize, long keepAliveTimeInSeconds, BlockingQueue<Runnable> workQueue, boolean daemon) {
        super(corePoolSize, maximumPoolSize, keepAliveTimeInSeconds, TimeUnit.SECONDS, workQueue, new PoolThreadFactory(daemon));
        this.name = name;
    }

    public static ExecutorService newThreadPool(String name, int maxThreads, boolean daemon) {
        ThreadPool pool = new ThreadPool(name, maxThreads, new LinkedBlockingQueue<>(), daemon);
        return Executors.unconfigurableExecutorService(pool);
    }

    public static ExecutorService newThreadPool(String name, int maxThreads) {
        return newThreadPool(name, maxThreads, false);
    }

    public static ExecutorService newThreadPool(String name, boolean daemon) {
        ThreadPool pool = new ThreadPool(name, Integer.MAX_VALUE, new LinkedBlockingQueue<>(), daemon);
        return Executors.unconfigurableExecutorService(pool);
    }

    public static ExecutorService newThreadPool(String name) {
        return newThreadPool(name, false);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        String threadName = null;
        if (r instanceof Thread) {
            Thread thread = (Thread) r;
            threadName = thread.getName();
        }
        t.setName(name + "-thread-" + threadNumber.getAndIncrement() + "-" + (threadName != null ? threadName : "@" + r.hashCode()));
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Thread.currentThread().setName(name + "-thread-idle");
    }

    private static final class PoolThreadFactory implements ThreadFactory {
        private final boolean daemon;
        private final ThreadGroup threadGroup = new ThreadGroup("PoolThreadFactoryGroup");

        PoolThreadFactory(boolean daemon) {
            this.daemon = daemon;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(threadGroup, r,"", THREAD_STACK_SIZE);
            t.setDaemon(daemon);
            return t;
        }
    }
}
