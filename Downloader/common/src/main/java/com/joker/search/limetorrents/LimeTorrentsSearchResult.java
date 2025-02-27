/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml), alejandroarturom
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

package com.joker.search.limetorrents;

import com.joker.search.torrent.AbstractTorrentSearchResult;
import com.joker.util.HtmlManipulator;
import com.joker.util.UrlUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by alejandroarturom on 08/26/2016
 * Modified by gubatron on 01/20/2020
 */
public final class LimeTorrentsSearchResult extends AbstractTorrentSearchResult {
    private final String filename;
    private final String displayName;
    private final String detailsUrl;
    private final String torrentUrl;
    private final String infoHash;
    private final double size;
    private final long creationTime;
    private final int seeds;

    LimeTorrentsSearchResult(String detailsUrl,
                             String infoHash,
                             String filename,
                             String fileSize,
                             String unit,
                             String age,
                             String seeds,
                             String title) {
        this.detailsUrl = detailsUrl;
        this.infoHash = (infoHash == null) ? null : infoHash.toLowerCase();
        this.filename = parseFileName(filename);
        this.size = parseSize(fileSize + " " + unit);
        this.creationTime = parseAgeString(age);
        this.seeds = parseSeeds(seeds);
        this.torrentUrl = UrlUtils.buildMagnetUrl(infoHash, filename, UrlUtils.USUAL_TORRENT_TRACKERS_MAGNET_URL_PARAMETERS);
        this.displayName = title;
    }

    @Override
    public double getSize() {
        return size;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String getSource() {
        return "LimeTorrents";
    }

    @Override
    public String getHash() {
        return infoHash;
    }

    @Override
    public int getSeeds() {
        return seeds;
    }

    @Override
    public String getDetailsUrl() {
        return detailsUrl;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public String getTorrentUrl() {
        return torrentUrl;
    }

    private String parseFileName(String decodedFileName) {
        return HtmlManipulator.replaceHtmlEntities(decodedFileName.trim()) + ".torrent";
    }

    private int parseSeeds(String group) {
        try {
            return Integer.parseInt(group);
        } catch (Exception e) {
            return 0;
        }
    }

    private long parseAgeString(String dateString) {
        long now = System.currentTimeMillis();
        try {
            if (dateString.contains("1 Year+")) {
                return now - 365L * 24L * 60L * 60L * 1000L; // a year in milliseconds
            }
            if (dateString.contains("Last Month")) {
                return now - 31L * 24L * 60L * 60L * 1000L; // a month in milliseconds
            }
            if (dateString.contains("months ago")) {
                int months = Integer.parseInt(dateString.substring(0, dateString.indexOf(' ')));
                long monthInMillis = 31L * 24L * 60L * 60L * 1000L;
                return now - months * monthInMillis;
            }
            if (dateString.contains("days ago")) {
                int days = Integer.parseInt(dateString.substring(0, dateString.indexOf(' ')));
                long dayInMillis = 24L * 60L * 60L * 1000L;
                return now - days * dayInMillis;
            }
            if (dateString.contains("Yesterday")) {
                return now - 24L * 60L * 60L * 1000L; // one day in milliseconds
            }
            // this format seems to be not used anymore
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            now = myFormat.parse(dateString.trim()).getTime();
        } catch (Throwable t) {
        }
        return now;
    }
}
