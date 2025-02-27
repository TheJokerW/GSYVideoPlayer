/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2020, FrostWire(R). All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.joker.search.yify;

import com.joker.search.CrawlableSearchResult;
import com.joker.search.SearchMatcher;
import com.joker.search.torrent.TorrentRegexSearchPerformer;

/**
 * @author gubatron
 * @author aldenml
 */
public final class YifySearchPerformer extends TorrentRegexSearchPerformer<YifySearchResult> {
    public static final String SEARCH_RESULTS_REGEX = "(?is)<a class=\"movielink\" href=\"/movie/(?<itemId>[0-9]*)/(?<htmlFileName>.*?)\" itemprop=\"name\">";
    public static final String TORRENT_DETAILS_PAGE_REGEX = "(?is)<section id=\"(movie|torrent)\".*?" +
            "<h1( itemprop=\"name\")?>(?<displayName>.*?)</h1>.*?" +
            "<dt>Size:</dt>.*?<dd>(?<size>.*?)</dd>.*?" +
            "Date:</dt>.*?(?<creationDate>[0-9]+/[0-9]+/[0-9]+.*?).*?</dd>.*?" +
            "<dt>(Seeds|Seeders):</dt>.*?<dd>(?<seeds>[0-9]+).*?</dd>.*?" +
            "<a href=\"(?<magnet>.*?)\" id=\"dm\" class=\"button button-default\".*?>Download Magnet</a>.*?";
    private static final int MAX_RESULTS = 21;

    public YifySearchPerformer(String domainName, long token, String keywords, int timeout) {
        super(domainName, token, keywords, timeout, 1, 2 * MAX_RESULTS, MAX_RESULTS, SEARCH_RESULTS_REGEX, TORRENT_DETAILS_PAGE_REGEX);
    }

    @Override
    protected String getUrl(int page, String encodedKeywords) {
        return "https://" + getDomainName() + "/search/" + encodedKeywords + "/";
    }

    @Override
    public CrawlableSearchResult fromMatcher(SearchMatcher matcher) {
        String itemId = matcher.group("itemId");
        String htmlFileName = matcher.group("htmlFileName");
        return new YifyTempSearchResult(getDomainName(), itemId, htmlFileName);
    }

    @Override
    protected YifySearchResult fromHtmlMatcher(CrawlableSearchResult sr, SearchMatcher matcher) {
        return new YifySearchResult(sr.getDetailsUrl(), matcher);
    }

    @Override
    protected boolean isValidHtml(String html) {
        return html != null && !html.contains("Cloudflare");
    }

    @Override
    protected int htmlPrefixOffset(String html) {
        int offset = html.indexOf("<div id=\"content\"");
        return offset > 0 ? offset : 0;
    }

    @Override
    protected int htmlSuffixOffset(String html) {
        int offset = html.indexOf("<section id=\"movie_bottom\">");
        return offset > 0 ? offset : html.length();
    }
    // Tests? See YifiSearchPerformerTest in tests folder for non-ui search test
}
