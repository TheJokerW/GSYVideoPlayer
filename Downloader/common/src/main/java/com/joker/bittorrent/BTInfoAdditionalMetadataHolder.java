/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2015, FrostWire(R). All rights reserved.
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

package com.joker.bittorrent;


import com.frostwire.jlibtorrent.Entry;
import com.frostwire.jlibtorrent.TorrentInfo;
import com.frostwire.jlibtorrent.swig.byte_vector;
import com.frostwire.jlibtorrent.swig.entry;

import java.io.File;
import java.util.Map;

/**
 * Here to factor out the initialization of additional metadata objects found
 * inside the info map of a torrent download manager.
 *
 * @author gubatron
 */
public class BTInfoAdditionalMetadataHolder {
    private final CopyrightLicenseBroker license;
    private final PaymentOptions paymentOptions;

    public BTInfoAdditionalMetadataHolder(byte[] torrentBytes, String paymentOptionsDisplayName) {
        this(TorrentInfo.bdecode(torrentBytes), paymentOptionsDisplayName);
    }

    public BTInfoAdditionalMetadataHolder(File torrent, String paymentOptionsDisplayName) {
        this(new TorrentInfo(torrent), paymentOptionsDisplayName);
    }

    public BTInfoAdditionalMetadataHolder(TorrentInfo tinfo, String paymentOptionsDisplayName) {
        final Map<String, Entry> additionalInfoProperties = getInfo(tinfo.toEntry().dictionary().get("info").swig());
        final Entry licenseEntry = additionalInfoProperties.get("license");
        final Entry paymentOptionsEntry = additionalInfoProperties.get("paymentOptions");
        boolean hasLicense = licenseEntry != null;
        boolean hasPaymentOptions = paymentOptionsEntry != null;
        if (hasLicense) {
            license = new CopyrightLicenseBroker(licenseEntry.dictionary());
        } else {
            license = null;
        }
        if (hasPaymentOptions) {
            paymentOptions = new PaymentOptions(paymentOptionsEntry.dictionary());
        } else {
            paymentOptions = new PaymentOptions(null, null);
        }
        paymentOptions.setItemName(paymentOptionsDisplayName);
    }

    private static Map<String, Entry> getInfo(entry e) {
        entry.data_type type = e.type();
        if (type == entry.data_type.dictionary_t) {
            return new Entry(e).dictionary();
        } else if (type == entry.data_type.preformatted_t) {
            byte_vector v = e.preformatted_bytes();
            e = entry.bdecode(v);
            return new Entry(e).dictionary();
        }
        return null;
    }

    public CopyrightLicenseBroker getLicenseBroker() {
        return license;
    }

    public PaymentOptions getPaymentOptions() {
        return paymentOptions;
    }
}
