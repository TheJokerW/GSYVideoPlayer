/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2020, FrostWire(R). All rights reserved.
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

package com.joker.transfers;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author gubatron
 * @author aldenml
 */
public interface Transfer {
    String getName();

    String getDisplayName();

    File getSavePath();

    File previewFile();

    double getSize();

    Date getCreated();

    TransferState getState();

    long getBytesReceived();

    long getBytesSent();

    long getDownloadSpeed();

    long getUploadSpeed();

    boolean isDownloading();

    long getETA();

    /**
     * [0..100]
     *
     * @return
     */
    int getProgress();

    boolean isComplete();

    List<TransferItem> getItems();

    void remove(boolean deleteData);
}
