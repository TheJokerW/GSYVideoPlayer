/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2016, FrostWire(R). All rights reserved.
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

package com.joker.mp4;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author gubatron
 * @author aldenml
 */
public final class DataEntryUrlBox extends FullBox {
    protected byte[] location;

    DataEntryUrlBox() {
        super(url_);
    }

    public String location() {
        return location != null ? Utf8.convert(location) : null;
    }

    public void location(String value) {
        location = value != null ? Utf8.convert(value) : null;
    }

    @Override
    void read(InputChannel ch, ByteBuffer buf) throws IOException {
        super.read(ch, buf);
        int len = (int) (length() - 4);
        if (len != 0) {
            IO.read(ch, len, buf);
            if (buf.remaining() > 0) {
                location = IO.str(buf);
            }
        }
    }

    @Override
    void write(OutputChannel ch, ByteBuffer buf) throws IOException {
        super.write(ch, buf);
        if (location != null) {
            buf.put(location);
            buf.put((byte) 0);
        }
        if (buf.position() > 0) {
            IO.write(ch, buf.position(), buf);
        }
    }

    @Override
    void update() {
        long s = 0;
        s += 4; // full box
        if (location != null) {
            s += location.length + 1;
        }
        length(s);
    }
}
