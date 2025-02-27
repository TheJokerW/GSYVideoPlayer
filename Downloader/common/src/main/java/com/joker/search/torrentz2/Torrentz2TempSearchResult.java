/*
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

package com.joker.search.torrentz2;

import com.joker.search.AbstractSearchResult;
import com.joker.search.CrawlableSearchResult;
import com.joker.util.UrlUtils;

final class Torrentz2TempSearchResult extends AbstractSearchResult implements CrawlableSearchResult {
    private final String detailsUrl;

    Torrentz2TempSearchResult(String domainName, String itemId) {
        // sometimes the itemId needs to be url encoded
        itemId = UrlUtils.encode(itemId);
        this.detailsUrl = "https://" + domainName + "/" + itemId;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public String getDetailsUrl() {
        return detailsUrl;
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
