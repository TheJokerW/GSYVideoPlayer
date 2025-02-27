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

package com.joker.search.archiveorg;

import java.util.List;

/**
 * @author gubatron
 * @author aldenml
 */
public final class ArchiveorgItem {
    public Object title;
    public String mediatype;
    //public Object description;
    public String licenseurl;
    public String publicdate;
    public int downloads;
    public int month;
    public String identifier;
    public List<String> format;
    public List<String> collection;
}
