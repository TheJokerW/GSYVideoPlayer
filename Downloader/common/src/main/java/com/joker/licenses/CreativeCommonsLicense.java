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

package com.joker.licenses;

import java.util.Locale;

/**
 * @author gubatron
 * @author aldenml
 */
public final class CreativeCommonsLicense extends License {
    private final String acronym;

    /**
     * To use with raw input
     *
     * @param name
     * @param url
     * @param acronym
     */
    CreativeCommonsLicense(String name, String url, String acronym) {
        super(name, url);
        this.acronym = acronym;
    }

    static CreativeCommonsLicense standard(String name, String acronym, String version) {
        String fullName = "Creative Commons " + name + " " + version;
        String url = "http://creativecommons.org/licenses/" + acronym.toLowerCase(Locale.US) + "/" + version + "/";
        String fullAcronym = "CC " + acronym.toUpperCase(Locale.US) + " " + version;
        return new CreativeCommonsLicense(fullName, url, fullAcronym);
    }

    public String acronym() {
        return acronym;
    }
}
