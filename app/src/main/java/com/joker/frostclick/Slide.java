/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml), Marcelina Knitter
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

package com.joker.frostclick;

/**
 * @author gubatron
 * @author aldenml
 */
public class Slide {

    /**
     * Open the URL if available, don't download
     */
    public static final int DOWNLOAD_METHOD_OPEN_URL = 0;

    /**
     * Download the torrent file
     */
    public static final int DOWNLOAD_METHOD_TORRENT = 1;

    /**
     * Download the file via HTTP
     */
    public static final int DOWNLOAD_METHOD_HTTP = 2;

    // FLAGS
    public static final int POST_DOWNLOAD_UNZIP = 1;
    public static final int POST_DOWNLOAD_DELETE_ZIP_FILE = 1 << 1;
    public static final int POST_DOWNLOAD_EXECUTE = 1 << 2;
    public static final int PREVIEW_AUDIO_USING_FWPLAYER = 1 << 3;
    public static final int PREVIEW_AUDIO_USING_BROWSER = 1 << 4;
    public static final int PREVIEW_VIDEO_USING_FWPLAYER = 1 << 5;
    public static final int PREVIEW_VIDEO_USING_BROWSER = 1 << 6;
    public static final int SHOW_AUDIO_PREVIEW_BUTTON = 1 << 7;
    public static final int SHOW_VIDEO_PREVIEW_BUTTON = 1 << 8;
    public static final int OPEN_CLICK_URL_ON_DOWNLOAD = 1 << 9;
    public static final int SHOW_PREVIEW_BUTTONS_ON_THE_LEFT = 1 << 10;
    public static final int IS_ADVERTISEMENT = 1 << 11;

    /**
     * url of image that will be displayed on this slide
     */
    public String imageSrc;

    /**
     * http address where to go if user clicks on this slide
     */
    public String clickURL;

    /**
     * length of time this slide will be shown
     */
    public long duration;

    /**
     * url of torrent file that should be opened if user clicks on this slide
     */
    public String torrent;

    public String httpDownloadURL;

    /**
     * language (optional filter) = Can be given in the forms of:
     * *
     * en
     * en_US
     */
    public String language;

    /**
     * os (optional filter) = Can be given in the forms of:
     * windows
     * mac
     * linux
     */
    public String os;

    /**
     * Title of the promotion
     */
    public String title;

    public String author;

    /**
     * Download method
     * 0 - Torrent
     * 1 - HTTP
     */
    public int method;

    public String md5;

    public String saveFileAs;

    public String executeParameters;

    public String includedVersions;

    public String audioURL;

    public String videoURL;

    public String facebook;

    public String twitter;

    public String gplus;

    public String youtube;

    public String instagram;

    public int flags;

    public String uri;

    public boolean uncompress;


    /**
     * Total size in bytes
     */
    public long size;
}
