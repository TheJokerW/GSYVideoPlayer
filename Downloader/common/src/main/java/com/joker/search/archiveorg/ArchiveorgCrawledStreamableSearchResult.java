/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2014, FrostWire(R). All rights reserved.
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

package com.joker.search.archiveorg;

import com.joker.search.StreamableSearchResult;

/**
 * @author gubatron
 * @author aldenml
 */
public class ArchiveorgCrawledStreamableSearchResult extends ArchiveorgCrawledSearchResult implements StreamableSearchResult {
    public ArchiveorgCrawledStreamableSearchResult(ArchiveorgSearchResult sr, ArchiveorgFile file) {
        super(sr, file);
    }

    @Override
    public String getStreamUrl() {
        return getDownloadUrl();
    }
}
