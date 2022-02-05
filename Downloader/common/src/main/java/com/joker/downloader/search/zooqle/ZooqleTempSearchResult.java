/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2017, FrostWire(R). All rights reserved.
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

package com.joker.downloader.search.zooqle;

import com.joker.downloader.search.AbstractSearchResult;
import com.joker.downloader.search.CrawlableSearchResult;
import com.joker.downloader.util.UrlUtils;

/**
 * @author aldenml
 * @author gubatron
 */
final class ZooqleTempSearchResult extends AbstractSearchResult implements CrawlableSearchResult {
    private final String detailUrl;

    ZooqleTempSearchResult(String domainName, String detailPath) {
        this.detailUrl = "https://" + domainName + "/" + UrlUtils.encode(detailPath);
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public String getDetailsUrl() {
        return detailUrl;
    }

    @Override
    public String getSource() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return false;
    }
}
