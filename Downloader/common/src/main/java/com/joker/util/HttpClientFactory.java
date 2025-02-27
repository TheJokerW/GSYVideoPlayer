/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2019, FrostWire(R). All rights reserved.
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

import com.joker.util.http.HttpClient;
import com.joker.util.http.JdkHttpClient;
import com.joker.util.http.OkHttpClientWrapper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author gubatron
 * @author aldenml
 */
public class HttpClientFactory {
    private static Map<HttpContext, ThreadPool> okHttpClientPools = null;

    private static final Map<HttpContext, OkHttpClientWrapper> fwOKHTTPClients = new HashMap<>();
    private static final Object okHTTPClientLock = new Object();

    private HttpClientFactory() {
    }

    public static HttpClient newInstance() {
        return new JdkHttpClient();
    }

    public static HttpClient getInstance(HttpContext context) {
        if (isWindowsXP()) {
            return new JdkHttpClient();
        }
        if (okHttpClientPools == null) {
            okHttpClientPools = buildThreadPools();
        }
        synchronized (okHTTPClientLock) {
            if (!fwOKHTTPClients.containsKey(context)) {
                fwOKHTTPClients.put(context, new OkHttpClientWrapper(okHttpClientPools.get(context)));
            }
        }
        return fwOKHTTPClients.get(context);
    }

    private static Map<HttpContext, ThreadPool> buildThreadPools() {
        final HashMap<HttpContext, ThreadPool> map = new HashMap<>();
        map.put(HttpContext.SEARCH, new ThreadPool("OkHttpClient-searches", 4, 16, 60, new LinkedBlockingQueue<>(), true));
        map.put(HttpContext.DOWNLOAD, new ThreadPool("OkHttpClient-downloads", 4, 16, 30, new LinkedBlockingQueue<>(), true));
        map.put(HttpContext.MISC, new ThreadPool("OkHttpClient-misc", 4, 16, 30, new LinkedBlockingQueue<>(), true));
        return map;
    }

    private static boolean isWindowsXP() {
        String os = System.getProperty("os.name");
        os = os.toLowerCase(Locale.US);
        return os.contains("windows xp");
    }

    public enum HttpContext {
        SEARCH,
        DOWNLOAD,
        MISC
    }
}
