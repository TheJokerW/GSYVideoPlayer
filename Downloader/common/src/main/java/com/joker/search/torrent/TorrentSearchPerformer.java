/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2014,, FrostWire(R). All rights reserved.

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

package com.joker.search.torrent;

import com.joker.search.CrawlPagedWebSearchPerformer;
import com.joker.search.PerformersHelper;
import com.joker.search.SearchResult;

import java.util.List;

/**
 * Extend this search performer if you can obtain all you need directly from a search results
 * page, otherwise extend TorrentRegexSearchPerformer
 * @author gubatron
 * @author aldenml
 */
public abstract class TorrentSearchPerformer extends CrawlPagedWebSearchPerformer<TorrentCrawlableSearchResult> {

    protected TorrentSearchPerformer(String domainName, long token, String keywords, int timeout, int pages, int numCrawls) {
        super(domainName, token, keywords, timeout, pages, numCrawls);
    }

    @Override
    protected String getCrawlUrl(TorrentCrawlableSearchResult sr) {
        return sr.getTorrentUrl();
    }

    @Override
    protected List<? extends SearchResult> crawlResult(TorrentCrawlableSearchResult sr, byte[] data) {
        return PerformersHelper.crawlTorrent(this, sr, data);
    }
}
