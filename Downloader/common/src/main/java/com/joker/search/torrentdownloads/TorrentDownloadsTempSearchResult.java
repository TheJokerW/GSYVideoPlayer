/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2016, FrostWire(R). All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.joker.search.torrentdownloads;

import com.joker.search.AbstractSearchResult;
import com.joker.search.CrawlableSearchResult;

/**
 * @author alejandroarturom
 */
public final class TorrentDownloadsTempSearchResult extends AbstractSearchResult implements CrawlableSearchResult {
    private final String itemId;
    private final String detailsUrl;

    TorrentDownloadsTempSearchResult(String domainName, String itemId) {
        this.itemId = itemId;
        this.detailsUrl = "https://" + domainName + "/torrent/" + itemId;
    }

    public String getItemId() {
        return itemId;
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
