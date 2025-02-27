/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml),
 *            Marcelina Knitter (@marcelinkaaa)
 * Copyright (c) 2011-2022, FrostWire(R). All rights reserved.
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

package com.joker.android.core.providers;

import static android.provider.MediaStore.Audio.AudioColumns.ALBUM;
import static android.provider.MediaStore.Audio.AudioColumns.ALBUM_ID;
import static android.provider.MediaStore.Audio.AudioColumns.ARTIST;
import static android.provider.MediaStore.Audio.AudioColumns.DATA;
import static android.provider.MediaStore.Audio.AudioColumns.DATE_ADDED;
import static android.provider.MediaStore.Audio.AudioColumns.DATE_MODIFIED;
import static android.provider.MediaStore.Audio.AudioColumns.MIME_TYPE;
import static android.provider.MediaStore.Audio.AudioColumns.SIZE;
import static android.provider.MediaStore.Audio.AudioColumns.TITLE;
import static android.provider.MediaStore.Audio.AudioColumns.YEAR;
import static android.provider.MediaStore.Audio.AudioColumns._ID;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.MediaStore.Files.FileColumns;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Video.VideoColumns;

import androidx.annotation.RequiresApi;

import com.joker.android.core.Constants;
import com.joker.android.core.FWFileDescriptor;
import com.joker.android.core.MediaType;
import com.joker.android.util.SystemUtils;
import com.joker.util.Logger;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Help yourself with TableFetchers.
 * <p/>
 * Note: if you need to fetch files by file path(s) see Librarian.instance().getFiles(filepath,exactMatch)
 *
 * @author gubatron
 * @author aldenml
 */
public final class TableFetchers {

    private static final Logger LOG = Logger.getLogger(TableFetchers.class);

    private static final TableFetcher AUDIO_TABLE_FETCHER = new AudioTableFetcher();
    private static final TableFetcher PICTURES_TABLE_FETCHER = new PicturesTableFetcher();
    private static final TableFetcher VIDEOS_TABLE_FETCHER = new VideosTableFetcher();
    private static final TableFetcher DOCUMENTS_TABLE_FETCHER = new DocumentsTableFetcher();
    private static final TableFetcher RINGTONES_TABLE_FETCHER = new RingtonesTableFetcher();
    private static final TableFetcher TORRENTS_TABLE_FETCHER = new TorrentsTableFetcher();
    public static final TableFetcher UNKNOWN_TABLE_FETCHER = new UnknownTableFetcher();


    static void getRecursiveFiles(File parent, byte fileType, List<File> results) {
        File[] files = parent.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isFile()) {
                MediaType mediaTypeForExtension = MediaType.getMediaTypeForExtension(FilenameUtils.getExtension(f.getAbsolutePath()));
                if (mediaTypeForExtension == MediaType.TYPE_UNKNOWN) {
                    continue;
                }
                if (mediaTypeForExtension.getId() == fileType) {
                    results.add(f);
                }
            } else if (f.isDirectory()) {
                getRecursiveFiles(f, fileType, results);
            }
        }
    }

    public static abstract class AbstractTableFetcher implements TableFetcher {

        @Override
        public String where() {
            return null;
        }

        @Override
        public String[] whereArgs() {
            return new String[0];
        }
    }

    /**
     * Default Table Fetcher for Audio Files.
     */
    public static class AudioTableFetcher extends AbstractTableFetcher {

        protected int idCol;
        protected int pathCol;
        protected int mimeCol;
        protected int artistCol;
        protected int titleCol;
        protected int albumCol;
        protected int yearCol;
        protected int sizeCol;
        protected int dateAddedCol;
        protected int dateModifiedCol;
        protected int albumIdCol;

        public String[] getColumns() {
            return new String[]{_ID, ARTIST, TITLE, ALBUM, DATA, YEAR, MIME_TYPE, SIZE, DATE_ADDED, DATE_MODIFIED, ALBUM_ID};
        }

        public String getSortByExpression() {
            return DATE_ADDED + " DESC";
        }

        public Uri getExternalContentUri() {
            if (SystemUtils.hasAndroid10OrNewer()) {
                return MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            }
            return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }


        public void prepareColumnIds(Cursor cur) {
            idCol = cur.getColumnIndex(_ID);
            pathCol = cur.getColumnIndex(DATA);
            mimeCol = cur.getColumnIndex(MIME_TYPE);
            artistCol = cur.getColumnIndex(ARTIST);
            titleCol = cur.getColumnIndex(TITLE);
            albumCol = cur.getColumnIndex(ALBUM);
            yearCol = cur.getColumnIndex(YEAR);
            sizeCol = cur.getColumnIndex(SIZE);
            dateAddedCol = cur.getColumnIndex(DATE_ADDED);
            dateModifiedCol = cur.getColumnIndex(DATE_MODIFIED);
            albumIdCol = cur.getColumnIndex(ALBUM_ID);
        }

        public FWFileDescriptor fetchFWFileDescriptor(Cursor cur) {
            int id = cur.getInt(idCol);
            String path = cur.getString(pathCol);
            String mime = cur.getString(mimeCol);
            String artist = cur.getString(artistCol);
            String title = cur.getString(titleCol);
            String album = cur.getString(albumCol);
            String year = cur.getString(yearCol);
            int size = cur.getInt(sizeCol);
            long dateAdded = cur.getLong(dateAddedCol);
            long dateModified = cur.getLong(dateModifiedCol);
            long albumId = cur.getLong(albumIdCol);

            FWFileDescriptor fd = new FWFileDescriptor(id, artist, title, album, year, path,
                    Constants.FILE_TYPE_AUDIO, mime, size, dateAdded, dateModified,
                    !SystemUtils.hasAndroid11OrNewer());
            fd.albumId = albumId;

            return fd;
        }

        public byte getFileType() {
            return Constants.FILE_TYPE_AUDIO;
        }

        public int getType() {
            return Constants.FILE_TYPE_AUDIO;
        }
    }

    public static class PicturesTableFetcher extends AbstractTableFetcher {

        private int idCol;
        private int titleCol;
        private int pathCol;
        private int mimeCol;
        private int sizeCol;
        private int dateAddedCol;
        private int dateModifiedCol;

        public FWFileDescriptor fetchFWFileDescriptor(Cursor cur) {
            int id = cur.getInt(idCol);
            String path = cur.getString(pathCol);
            String mime = cur.getString(mimeCol);
            String title = cur.getString(titleCol);
            int size = cur.getInt(sizeCol);
            long dateAdded = cur.getLong(dateAddedCol);
            long dateModified = cur.getLong(dateModifiedCol);

            return new FWFileDescriptor(id, null, title, null, null, path, Constants.FILE_TYPE_PICTURES, mime, size, dateAdded, dateModified, !SystemUtils.hasAndroid11OrNewer());
        }

        public String[] getColumns() {
            return new String[]{ImageColumns._ID, ImageColumns.TITLE, ImageColumns.DATA, ImageColumns.MIME_TYPE, ImageColumns.MINI_THUMB_MAGIC, ImageColumns.SIZE, ImageColumns.DATE_ADDED, ImageColumns.DATE_MODIFIED};
        }

        public Uri getExternalContentUri() {
            if (SystemUtils.hasAndroid10OrNewer()) {
                return MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            }
            return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        public byte getFileType() {
            return Constants.FILE_TYPE_PICTURES;
        }

        public String getSortByExpression() {
            return ImageColumns.DATE_ADDED + " DESC";
        }

        public void prepareColumnIds(Cursor cur) {
            idCol = cur.getColumnIndex(ImageColumns._ID);
            titleCol = cur.getColumnIndex(ImageColumns.TITLE);
            pathCol = cur.getColumnIndex(ImageColumns.DATA);
            mimeCol = cur.getColumnIndex(ImageColumns.MIME_TYPE);
            sizeCol = cur.getColumnIndex(ImageColumns.SIZE);
            dateAddedCol = cur.getColumnIndex(ImageColumns.DATE_ADDED);
            dateModifiedCol = cur.getColumnIndex(ImageColumns.DATE_MODIFIED);
        }

        public int getType() {
            return Constants.FILE_TYPE_PICTURES;
        }
    }

    public static final class VideosTableFetcher extends AbstractTableFetcher {

        private int idCol;
        private int pathCol;
        private int mimeCol;
        private int artistCol;
        private int titleCol;
        private int albumCol;
        private int sizeCol;
        private int dateAddedCol;
        private int dateModifiedCol;

        public FWFileDescriptor fetchFWFileDescriptor(Cursor cur) {
            int id = cur.getInt(idCol);
            String path = cur.getString(pathCol);
            String mime = cur.getString(mimeCol);
            String artist = cur.getString(artistCol);
            String title = cur.getString(titleCol);
            String album = cur.getString(albumCol);
            int size = cur.getInt(sizeCol);
            long dateAdded = cur.getLong(dateAddedCol);
            long dateModified = cur.getLong(dateModifiedCol);

            return new FWFileDescriptor(id, artist, title, album, null, path, Constants.FILE_TYPE_VIDEOS, mime, size, dateAdded, dateModified, !SystemUtils.hasAndroid11OrNewer());
        }

        public String[] getColumns() {
            return new String[]{VideoColumns._ID, VideoColumns.ARTIST, VideoColumns.TITLE, VideoColumns.ALBUM, VideoColumns.DATA, VideoColumns.MIME_TYPE, VideoColumns.MINI_THUMB_MAGIC, VideoColumns.SIZE, VideoColumns.DATE_ADDED, VideoColumns.DATE_MODIFIED};
        }

        public Uri getExternalContentUri() {
            if (SystemUtils.hasAndroid10OrNewer()) {
                return MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            }
            return MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }

        public byte getFileType() {
            return Constants.FILE_TYPE_VIDEOS;
        }

        public String getSortByExpression() {
            return VideoColumns.DATE_ADDED + " DESC";
        }

        public void prepareColumnIds(Cursor cur) {
            idCol = cur.getColumnIndex(VideoColumns._ID);
            pathCol = cur.getColumnIndex(VideoColumns.DATA);
            mimeCol = cur.getColumnIndex(VideoColumns.MIME_TYPE);
            artistCol = cur.getColumnIndex(VideoColumns.ARTIST);
            titleCol = cur.getColumnIndex(VideoColumns.TITLE);
            albumCol = cur.getColumnIndex(VideoColumns.ALBUM);
            sizeCol = cur.getColumnIndex(VideoColumns.SIZE);
            dateAddedCol = cur.getColumnIndex(VideoColumns.DATE_ADDED);
            dateModifiedCol = cur.getColumnIndex(VideoColumns.DATE_MODIFIED);
        }

        public int getType() {
            return Constants.FILE_TYPE_VIDEOS;
        }
    }

    public static abstract class AbstractFilesTableFetcher extends AbstractTableFetcher {
        private int idCol;
        private int pathCol;
        private int mimeCol;
        private int titleCol;
        private int sizeCol;
        private int dateAddedCol;
        private int dateModifiedCol;

        public FWFileDescriptor fetchFWFileDescriptor(Cursor cur) {
            int id = cur.getInt(idCol);
            String path = cur.getString(pathCol);
            String mime = cur.getString(mimeCol);
            String title = cur.getString(titleCol);
            int size = cur.getInt(sizeCol);
            long dateAdded = cur.getLong(dateAddedCol);
            long dateModified = cur.getLong(dateModifiedCol);
            return new FWFileDescriptor(id, null, title, null, null, path, Constants.FILE_TYPE_DOCUMENTS, mime, size, dateAdded, dateModified, !SystemUtils.hasAndroid11OrNewer());
        }

        public String[] getColumns() {
            return new String[]{FileColumns._ID, FileColumns.DATA, FileColumns.SIZE, FileColumns.TITLE, FileColumns.MIME_TYPE, FileColumns.DATE_ADDED, FileColumns.DATE_MODIFIED};
        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        public Uri getExternalContentUri() {
            if (SystemUtils.hasAndroid10OrNewer()) {
                return MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            }
            return MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL);
        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        public Uri getInternalContentUri() {
            return MediaStore.Files.getContentUri(MediaStore.VOLUME_INTERNAL);
        }

        public byte getFileType() {
            return Constants.FILE_TYPE_DOCUMENTS;
        }

        public String getSortByExpression() {
            return FileColumns.DATE_ADDED + " DESC";
        }

        public void prepareColumnIds(Cursor cur) {
            idCol = cur.getColumnIndex(FileColumns._ID);
            pathCol = cur.getColumnIndex(FileColumns.DATA);
            mimeCol = cur.getColumnIndex(FileColumns.MIME_TYPE);
            titleCol = cur.getColumnIndex(FileColumns.TITLE);
            sizeCol = cur.getColumnIndex(FileColumns.SIZE);
            dateAddedCol = cur.getColumnIndex(FileColumns.DATE_ADDED);
            dateModifiedCol = cur.getColumnIndex(FileColumns.DATE_MODIFIED);
        }
    }

    public static final class DocumentsTableFetcher extends AbstractFilesTableFetcher {

        final static String extensionsWhereSubClause = getExtsWhereSubClause();

        private static String getExtsWhereSubClause() {
            final String[] exts = MediaType.getDocumentMediaType().getExtensions().toArray(new String[0]);
            StringBuilder sb = new StringBuilder();
            sb.append('(');
            int index = 0;
            while (index < exts.length) {
                sb.append(FileColumns.DATA);
                sb.append(" LIKE '%.");
                sb.append(exts[index]);
                sb.append('\'');
                if (index < exts.length - 1) {
                    sb.append(" OR ");
                }
                index++;
            }
            sb.append(") AND ");
            return sb.toString();
        }

        @Override
        public String where() {
            return FileColumns.DATA + " NOT LIKE ? AND " +
                    FileColumns.DATA + " NOT LIKE ? AND " +
                    FileColumns.DATA + " NOT LIKE ? AND " +
                    FileColumns.DATA + " NOT LIKE ? AND " +
                    extensionsWhereSubClause +
                    FileColumns.MEDIA_TYPE + " = " + FileColumns.MEDIA_TYPE_NONE + " AND " +
                    FileColumns.SIZE + " > 0 AND " + FileColumns.SIZE + " != 4096";
        }

        @Override
        public String[] whereArgs() {
            return new String[]{"%cache%", "%/.%", "%/libtorrent/%", "%com.google.%"};
        }

        public int getType() {
            return Constants.FILE_TYPE_DOCUMENTS;
        }
    }

    public static final class TorrentsTableFetcher extends AbstractFilesTableFetcher {

        @Override
        public String where() {
            return FileColumns.DATA + " NOT LIKE ? AND " +
                    FileColumns.DATA + " NOT LIKE ? AND " +
                    FileColumns.DATA + " NOT LIKE ? AND " +
                    FileColumns.DATA + " LIKE ? AND " +
                    FileColumns.MEDIA_TYPE + " = " + FileColumns.MEDIA_TYPE_NONE + " AND " +
                    FileColumns.SIZE + " > 0";
        }

        @Override
        public String[] whereArgs() {
            return new String[]{"%/cache/%", "%/.%", "%/libtorrent/%", "%.torrent"};
        }

        public int getType() {
            return Constants.FILE_TYPE_TORRENTS;
        }
    }

    public static final class RingtonesTableFetcher extends AbstractTableFetcher {
        private int idCol;
        private int pathCol;
        private int mimeCol;
        private int artistCol;
        private int titleCol;
        private int albumCol;
        private int yearCol;
        private int sizeCol;
        private int dateAddedCol;
        private int dateModifiedCol;

        public FWFileDescriptor fetchFWFileDescriptor(Cursor cur) {
            int id = cur.getInt(idCol);
            String path = cur.getString(pathCol);
            String mime = cur.getString(mimeCol);
            String artist = cur.getString(artistCol);
            String title = cur.getString(titleCol);
            String album = cur.getString(albumCol);
            String year = cur.getString(yearCol);
            int size = cur.getInt(sizeCol);
            long dateAdded = cur.getLong(dateAddedCol);
            long dateModified = cur.getLong(dateModifiedCol);

            return new FWFileDescriptor(id, artist, title, album, year, path,
                    Constants.FILE_TYPE_RINGTONES, mime, size, dateAdded, dateModified,
                    !SystemUtils.hasAndroid11OrNewer());
        }

        public String[] getColumns() {
            return new String[]{_ID, ARTIST, TITLE, ALBUM, DATA, YEAR, MIME_TYPE, SIZE, DATE_ADDED, DATE_MODIFIED};
        }

        public Uri getExternalContentUri() {
            return null;
        }

        public Uri getInternalContentUri() {
            return MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        }

        public byte getFileType() {
            return Constants.FILE_TYPE_RINGTONES;
        }

        public String getSortByExpression() {
            return DATE_ADDED + " DESC";
        }

        public void prepareColumnIds(Cursor cur) {
            idCol = cur.getColumnIndex(_ID);
            pathCol = cur.getColumnIndex(DATA);
            mimeCol = cur.getColumnIndex(MIME_TYPE);
            artistCol = cur.getColumnIndex(ARTIST);
            titleCol = cur.getColumnIndex(TITLE);
            albumCol = cur.getColumnIndex(ALBUM);
            yearCol = cur.getColumnIndex(YEAR);
            sizeCol = cur.getColumnIndex(SIZE);
            dateAddedCol = cur.getColumnIndex(DATE_ADDED);
            dateModifiedCol = cur.getColumnIndex(DATE_MODIFIED);
        }

        public List<FWFileDescriptor> externalFolderFWFileDescriptors(Context context) {
            return new ArrayList<>();
        }

        public int getType() {
            return Constants.FILE_TYPE_RINGTONES;
        }

    }

    private static class UnknownTableFetcher implements TableFetcher {
        @Override
        public String[] getColumns() {
            return new String[0];
        }

        @Override
        public String getSortByExpression() {
            return null;
        }

        @Override
        public Uri getExternalContentUri() {
            return null;
        }

        @Override
        public void prepareColumnIds(Cursor cur) {

        }

        @Override
        public FWFileDescriptor fetchFWFileDescriptor(Cursor cur) {
            return null;
        }

        @Override
        public byte getFileType() {
            return 0;
        }

        @Override
        public String where() {
            return null;
        }

        @Override
        public String[] whereArgs() {
            return new String[0];
        }

        @Override
        public int getType() {
            return 0;
        }
    }

    public static TableFetcher getFetcher(byte fileType) {
        switch (fileType) {
            case Constants.FILE_TYPE_AUDIO:
                return AUDIO_TABLE_FETCHER;
            case Constants.FILE_TYPE_PICTURES:
                return PICTURES_TABLE_FETCHER;
            case Constants.FILE_TYPE_VIDEOS:
                return VIDEOS_TABLE_FETCHER;
            case Constants.FILE_TYPE_DOCUMENTS:
                return DOCUMENTS_TABLE_FETCHER;
            case Constants.FILE_TYPE_RINGTONES:
                return RINGTONES_TABLE_FETCHER;
            case Constants.FILE_TYPE_TORRENTS:
                return TORRENTS_TABLE_FETCHER;
            default:
                return UNKNOWN_TABLE_FETCHER;
        }
    }

}
