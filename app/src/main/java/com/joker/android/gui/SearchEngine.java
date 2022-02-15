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

package com.joker.android.gui;

import android.os.Build;

import com.joker.android.core.ConfigurationManager;
import com.joker.android.core.Constants;
import com.joker.search.SearchPerformer;
import com.joker.search.archiveorg.ArchiveorgSearchPerformer;
import com.joker.search.eztv.EztvSearchPerformer;
import com.joker.search.frostclick.FrostClickSearchPerformer;
import com.joker.search.frostclick.UserAgent;
import com.joker.search.glotorrents.GloTorrentsSearchPerformer;
import com.joker.search.idope.IdopeSearchPerformer;
import com.joker.search.limetorrents.LimeTorrentsSearchPerformer;
import com.joker.search.magnetdl.MagnetDLSearchPerformer;
import com.joker.search.nyaa.NyaaSearchPerformer;
import com.joker.search.one337x.One337xSearchPerformer;
import com.joker.search.soundcloud.SoundcloudSearchPerformer;
import com.joker.search.torlock.TorLockSearchPerformer;
import com.joker.search.torrentdownloads.TorrentDownloadsSearchPerformer;
import com.joker.search.torrentparadise.TorrentParadiseSearchPerformer;
import com.joker.search.torrentz2.Torrentz2SearchPerformer;
import com.joker.search.tpb.TPBSearchPerformer;
import com.joker.search.yify.YifySearchPerformer;
import com.joker.search.zooqle.ZooqleSearchPerformer;
import com.joker.util.HttpClientFactory;
import com.joker.util.UrlUtils;
import com.joker.util.http.HttpClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author gubatron
 * @author aldenml
 */
public abstract class SearchEngine {

    private static final UserAgent FROSTWIRE_ANDROID_USER_AGENT = new UserAgent(getOSVersionString(), Constants.joker_VERSION_STRING, Constants.joker_BUILD);
    private static final int DEFAULT_TIMEOUT = 5000;

    private final String name;
    private final String preferenceKey;

    private boolean active;

    private SearchEngine(String name, String preferenceKey) {
        this.name = name;
        this.preferenceKey = preferenceKey;
        this.active = true;
        postInitWork();
    }

    protected boolean isReady() {
        return true;
    }

    protected void postInitWork() {
    }

    public String getName() {
        return name;
    }

    public abstract SearchPerformer getPerformer(long token, String keywords);

    public String getPreferenceKey() {
        return preferenceKey;
    }

    public boolean isEnabled() {
        return isActive() && ConfigurationManager.instance().getBoolean(preferenceKey);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return name;
    }

    public static List<SearchEngine> getEngines(boolean excludeNonReady) {
        ArrayList<SearchEngine> candidates = new ArrayList<>();

        for (SearchEngine se : ALL_ENGINES) {
            if (excludeNonReady && se.isReady()) {
                candidates.add(se);
            } else {
                candidates.add(se);
            }
        }

        // ensure that at leas one is enable
        boolean oneEnabled = false;
        for (SearchEngine se : candidates) {
            if (se.isEnabled()) {
                oneEnabled = true;
            }
        }
        if (!oneEnabled) {
            SearchEngine engineToEnable;
            engineToEnable = ARCHIVE;
            String prefKey = engineToEnable.getPreferenceKey();
            ConfigurationManager.instance().setBoolean(prefKey, true);
        }
        return candidates;
    }

    public static SearchEngine forName(String name) {
        for (SearchEngine engine : getEngines(false)) {
            if (engine.getName().equalsIgnoreCase(name)) {
                return engine;
            }
        }
        return null;
    }

    static String getOSVersionString() {
        return Build.VERSION.CODENAME + "_" + Build.VERSION.INCREMENTAL + "_" + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT;
    }

    public static final SearchEngine ZOOQLE = new SearchEngine("Zooqle", Constants.PREF_KEY_SEARCH_USE_ZOOQLE) {
        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            return new ZooqleSearchPerformer("zooqle.com", token, keywords, DEFAULT_TIMEOUT);
        }
    };

    public static final SearchEngine SOUNCLOUD = new SearchEngine("Soundcloud", Constants.PREF_KEY_SEARCH_USE_SOUNDCLOUD) {
        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            return new SoundcloudSearchPerformer("api-v2.sndcdn.com", token, keywords, DEFAULT_TIMEOUT);
        }
    };

    public static final SearchEngine ARCHIVE = new SearchEngine("Archive.org", Constants.PREF_KEY_SEARCH_USE_ARCHIVEORG) {
        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            return new ArchiveorgSearchPerformer("archive.org", token, keywords, DEFAULT_TIMEOUT);
        }
    };

    public static final SearchEngine FROSTCLICK = new SearchEngine("FrostClick", Constants.PREF_KEY_SEARCH_USE_FROSTCLICK) {
        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            return new FrostClickSearchPerformer("api.frostclick.com", token, keywords, DEFAULT_TIMEOUT, FROSTWIRE_ANDROID_USER_AGENT);
        }
    };

    public static final SearchEngine TORLOCK = new SearchEngine("TorLock", Constants.PREF_KEY_SEARCH_USE_TORLOCK) {
        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            return new TorLockSearchPerformer("www.torlock.com", token, keywords, DEFAULT_TIMEOUT);
        }
    };

    public static final SearchEngine TORRENTDOWNLOADS = new SearchEngine("TorrentDownloads", Constants.PREF_KEY_SEARCH_USE_TORRENTDOWNLOADS) {
        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            return new TorrentDownloadsSearchPerformer("www.torrentdownloads.me", token, keywords, DEFAULT_TIMEOUT);
        }
    };

    public static final SearchEngine LIMETORRENTS = new SearchEngine("LimeTorrents", Constants.PREF_KEY_SEARCH_USE_LIMETORRENTS) {
        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            return new LimeTorrentsSearchPerformer("www.limetorrents.info", token, keywords, DEFAULT_TIMEOUT);
        }
    };

    public static final SearchEngine NYAA = new SearchEngine("Nyaa", Constants.PREF_KEY_SEARCH_USE_NYAA) {
        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            return new NyaaSearchPerformer("nyaa.si", token, keywords, DEFAULT_TIMEOUT);
        }
    };

    public static final SearchEngine EZTV = new SearchEngine("Eztv", Constants.PREF_KEY_SEARCH_USE_EZTV) {
        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            return new EztvSearchPerformer("eztv.re", token, keywords, DEFAULT_TIMEOUT);
        }
    };

    public static final SearchEngine TPB = new SearchEngine("TPB", Constants.PREF_KEY_SEARCH_USE_TPB) {
        private String domainName = null;

        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            if (domainName == null) {
                throw new RuntimeException("check your logic, this search performer has no domain name ready");
            }
            return new TPBSearchPerformer(domainName, token, keywords, DEFAULT_TIMEOUT);
        }

        protected void postInitWork() {
            // while this is happening TPB.isReady() should be false, as it's initialized with a null domain name.
            new Thread(() -> {
                HttpClient httpClient = HttpClientFactory.getInstance(HttpClientFactory.HttpContext.SEARCH);
                String[] mirrors = {
                        "thepiratebay.org",
                        "www.pirate-bay.net",
                        "pirate-bays.net",
                        "pirate-bay.info",
                        "thepiratebay-unblocked.org",
                        "piratebay.live",
                        "thepiratebay.zone",
                        "thepiratebay.monster",
                        "thepiratebay0.org",
                        "thepiratebay.vip",
                };
                domainName = UrlUtils.getFastestMirrorDomain(httpClient, mirrors, 6000);
            }
            ).start();
        }

        @Override
        protected boolean isReady() {
            return domainName != null;
        }
    };

    public static final SearchEngine YIFY = new SearchEngine("Yify", Constants.PREF_KEY_SEARCH_USE_YIFY) {
        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            return new YifySearchPerformer("yify-torrent.cc", token, keywords, DEFAULT_TIMEOUT);
        }
    };

    public static final SearchEngine ONE337X = new SearchEngine("1337x", Constants.PREF_KEY_SEARCH_USE_ONE337X) {
        private String domainName = null;

        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            if (domainName == null) {
                throw new RuntimeException("check your logic, this search performer has no domain name ready");
            }
            return new One337xSearchPerformer(domainName, token, keywords, DEFAULT_TIMEOUT);
        }

        protected void postInitWork() {
            new Thread(() -> {
                HttpClient httpClient = HttpClientFactory.getInstance(HttpClientFactory.HttpContext.SEARCH);
                String[] mirrors = {
                        "www.1377x.to"
                };
                domainName = UrlUtils.getFastestMirrorDomain(httpClient, mirrors, 7000);
            }
            ).start();
        }

        @Override
        protected boolean isReady() {
            return domainName != null;
        }
    };

    public static final SearchEngine IDOPE = new SearchEngine("Idope", Constants.PREF_KEY_SEARCH_USE_IDOPE) {

        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            return new IdopeSearchPerformer(token, keywords, DEFAULT_TIMEOUT);
        }
    };

    public static final SearchEngine TORRENTZ2 = new SearchEngine("Torrentz2", Constants.PREF_KEY_SEARCH_USE_TORRENTZ2) {

        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            return new Torrentz2SearchPerformer(token, keywords, DEFAULT_TIMEOUT / 2);
        }
    };

    public static final SearchEngine MAGNETDL = new SearchEngine("MagnetDL", Constants.PREF_KEY_SEARCH_USE_MAGNETDL) {

        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            return new MagnetDLSearchPerformer(token, keywords, DEFAULT_TIMEOUT);
        }
    };


    public static final SearchEngine TORRENT_PARADISE = new SearchEngine("TorrentParadise", Constants.PREF_KEY_SEARCH_USE_TORRENT_PARADISE) {
        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            return new TorrentParadiseSearchPerformer(token, keywords, DEFAULT_TIMEOUT);
        }
    };

    public static final SearchEngine GLOTORRENTS = new SearchEngine("GloTorrents", Constants.PREF_KEY_SEARCH_USE_GLOTORRENTS) {
        @Override
        public SearchPerformer getPerformer(long token, String keywords) {
            return new GloTorrentsSearchPerformer(token, keywords, DEFAULT_TIMEOUT);
        }
    };

    private static final List<SearchEngine> ALL_ENGINES = Arrays.asList(
            TORRENT_PARADISE,
            MAGNETDL,
            TORRENTZ2,
            YIFY,
            ONE337X,
            IDOPE,
            FROSTCLICK,
            ZOOQLE,
            TPB,
            SOUNCLOUD,
            ARCHIVE,
            TORLOCK,
            TORRENTDOWNLOADS,
            LIMETORRENTS,
            NYAA,
            EZTV,
            GLOTORRENTS);
}
