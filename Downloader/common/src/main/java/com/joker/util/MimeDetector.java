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

package com.joker.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gubatron
 * @author aldenml
 */
public final class MimeDetector {
    public static final String UNKNOWN = "application/octet-stream";
    private static Map<String, String> map = null;

    private MimeDetector() {
    }

    public static String getMimeType(String ext) {
        ensureMap();
        return map.containsKey(ext) ? map.get(ext) : UNKNOWN;
    }

    private static void ensureMap() {
        if (map == null) {
            map = buildMimeTypeMap();
        }
    }

    private static Map<String, String> buildMimeTypeMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("apk", "application/vnd.android.package-archive");
        map.put("fx", "text/plain");
        map.put("bctpuri", "application/x-bctp-uri");
        map.put("deepv", "application/x-deepv");
        map.put("shar", "application/x-shar");
        map.put("wdb", "application/vndms-works");
        map.put("setpay", "application/set-payment-initiation");
        map.put("fs", "text/plain");
        map.put("wcm", "application/vndms-works");
        map.put("datasource", "application/xml");
        map.put("xgz", "xgl/drawing");
        map.put("3dmf", "x-world/x-3dmf");
        map.put("omcr", "application/x-omcregerator");
        map.put("gz", "application/x-gzip");
        map.put("texinfo", "application/x-texinfo");
        map.put("rgb", "image/x-rgb");
        map.put("vsixmanifest", "text/xml");
        map.put("aifc", "audio/aiff");
        map.put("gl", "video/gl");
        map.put("sgl", "application/vnd.stardivision.writer-global");
        map.put("sgm", "text/sgml");
        map.put("aim", "application/x-aim");
        map.put("ec3", "audio/ec3");
        map.put("rgs", "text/plain");
        map.put("aip", "text/x-audiosoft-intra");
        map.put("ascx", "application/xml");
        map.put("bin", "application/octet-stream");
        map.put("aiff", "audio/aiff");
        map.put("gif", "image/gif");
        map.put("vew", "application/groupwise");
        map.put("asf", "video/x-ms-asf");
        map.put("dv", "video/x-dv");
        map.put("vscontent", "application/xml");
        map.put("c", "application/x-cplusplus");
        map.put("aif", "audio/x-aiff");
        map.put("mc$", "application/x-magic-cap-package-10");
        map.put("3gp2", "video/3gpp2");
        map.put("h", "application/x-cplusplus");
        map.put("omcd", "application/x-omcdatamaker");
        map.put("midi", "audio/midi");
        map.put("mak", "text/plain");
        map.put("map", "text/plain");
        map.put("dl", "video/dl");
        map.put("mar", "text/plain");
        map.put("dp", "application/commonground");
        map.put("man", "application/x-troff-man");
        map.put("nef", "image/nef");
        map.put("f", "text/plain");
        map.put("g", "text/plain");
        map.put("sid", "audio/x-psid");
        map.put("c", "text/plain");
        map.put("a", "application/octet-stream");
        map.put("ez", "application/andrew-inset");
        map.put("mbd", "application/mbedlet");
        map.put("323", "text/h323");
        map.put("rexx", "text/x-scriptrexx");
        map.put("o", "application/octet-stream");
        map.put("web", "application/vndxara");
        map.put("m", "text/plain");
        map.put("vdo", "video/vdo");
        map.put("h", "text/plain");
        map.put("i", "text/plain");
        map.put("sit", "application/x-stuffit");
        map.put("m2ts", "video/vnd.dlna.mpeg-tts");
        map.put("asx", "video/x-ms-asf");
        map.put("t", "application/x-troff");
        map.put("s", "text/plain");
        map.put("el", "text/x-scriptelisp");
        map.put("xdr", "application/xml");
        map.put("p", "text/x-pascal");
        map.put("c++", "application/x-cplusplus");
        map.put("h++", "application/x-cplusplus");
        map.put("wdp", "image/vnd.ms-photo");
        map.put("es", "application/x-esrehber");
        map.put("z", "application/x-compress");
        map.put("g3", "image/g3fax");
        map.put("xls", "application/vndms-excel");
        map.put("3gpp", "video/3gpp");
        map.put("uris", "text/uri-list");
        map.put("vcs", "text/x-vcalendar");
        map.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        map.put("sct", "text/scriptlet");
        map.put("ncm", "application/vndnokiaconfiguration-message");
        map.put("xlm", "application/vndms-excel");
        map.put("bz", "application/x-bzip");
        map.put("xll", "application/vndms-excel");
        map.put("pdf", "application/pdf");
        map.put("mcd", "application/mcad");
        map.put("settings", "application/xml");
        map.put("xlk", "application/excel");
        map.put("xld", "application/excel");
        map.put("rct", "text/plain");
        map.put("pcx", "image/x-pcx");
        map.put("sda", "application/vnd.stardivision.draw");
        map.put("qd3d", "x-world/x-3dmf");
        map.put("xla", "application/excel");
        map.put("sdd", "application/vnd.stardivision.impress");
        map.put("jav", "text/plain");
        map.put("xlb", "application/excel");
        map.put("sdc", "application/vnd.stardivision.calc");
        map.put("mcp", "application/netmc");
        map.put("ief", "image/ief");
        map.put("xlc", "application/vndms-excel");
        map.put("pct", "image/x-pict");
        map.put("wbmp", "image/vndwapwbmp");
        map.put("jar", "application/java-archive");
        map.put("bm", "image/bmp");
        map.put("vda", "application/vda");
        map.put("mcf", "image/vasa");
        map.put("pdb", "chemical/x-pdb");
        map.put("jam", "audio/x-jam");
        map.put("sdm", "application/vnd.stardivision.mail");
        map.put("sdp", "application/vnd.stardivision.impress-packed");
        map.put("odg", "application/vnd.oasis.opendocument.graphics");
        map.put("odh", "text/plain");
        map.put("mdb", "application/x-msaccess");
        map.put("odf", "application/vnd.oasis.opendocument.formula");
        map.put("qcp", "audio/vndqcelp");
        map.put("ani", "application/x-navi-animation");
        map.put("odl", "text/plain");
        map.put("odi", "application/vnd.oasis.opendocument.image");
        map.put("au", "audio/basic");
        map.put("odp", "application/vnd.oasis.opendocument.presentation");
        map.put("wax", "audio/x-ms-wax");
        map.put("odm", "application/vnd.oasis.opendocument.text-master");
        map.put("ods", "application/vnd.oasis.opendocument.spreadsheet");
        map.put("odt", "application/vnd.oasis.opendocument.text");
        map.put("cs", "text/plain");
        map.put("pcl", "application/vndhp-pcl");
        map.put("bmp", "image/bmp");
        map.put("vcd", "application/x-cdlink");
        map.put("mdp", "text/plain");
        map.put("jfif", "image/jpeg");
        map.put("scd", "application/x-msschedule");
        map.put("hlsli", "text/plain");
        map.put("qd3", "x-world/x-3dmf");
        map.put("jfif-tbnl", "image/jpeg");
        map.put("odc", "application/vnd.oasis.opendocument.chart");
        map.put("scm", "application/x-lotusscreencam");
        map.put("odb", "application/vnd.oasis.opendocument.database");
        map.put("cc", "text/plain");
        map.put("cd", "text/plain");
        map.put("oda", "application/oda");
        map.put("vcf", "text/x-vcard");
        map.put("raw", "image/raw");
        map.put("nap", "image/naplps");
        map.put("midi", "audio/mid");
        map.put("set", "application/set");
        map.put("rc2", "text/plain");
        map.put("ico", "image/x-icon");
        map.put("pbm", "image/x-portable-bitmap");
        map.put("ras", "image/cmu-raster");
        map.put("rat", "application/rat-file");
        map.put("jnlp", "application/x-java-jnlp-file");
        map.put("acgi", "text/html");
        map.put("rmi", "audio/mid");
        map.put("ashx", "application/xml");
        map.put("fdf", "application/vndfdf");
        map.put("edrwx", "model/vnd.edrwx+xps");
        map.put("vssscc", "text/plain");
        map.put("pas", "text/pascal");
        map.put("ice", "x-conference/x-cooltalk");
        map.put("lam", "audio/x-liveaudio");
        map.put("wiz", "application/msword");
        map.put("jcm", "application/x-java-commerce");
        map.put("talk", "text/x-speech");
        map.put("sdw", "application/vnd.stardivision.writer");
        map.put("htmls", "text/html");
        map.put("sds", "application/vnd.stardivision.chart");
        map.put("vcxproj", "application/xml");
        map.put("sdr", "application/sounder");
        map.put("wiq", "application/xml");
        map.put("sea", "application/sea");
        map.put("wk1", "application/x-123");
        map.put("kdc", "image/kdc");
        map.put("xif", "image/vndxiff");
        map.put("idl", "text/plain");
        map.put("au", "audio/basic");
        map.put("orderedtest", "application/xml");
        map.put("dcr", "application/x-director");
        map.put("dwfx", "model/vnd.dwfx+xps");
        map.put("ai", "application/postscript");
        map.put("boo", "application/book");
        map.put("ram", "audio/x-pn-realaudio");
        map.put("idc", "text/plain");
        map.put("mfp", "application/x-shockwave-flash");
        map.put("tar.gz", "application/x-gzip");
        map.put("hdf", "application/x-hdf");
        map.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        map.put("java", "text/plain");
        map.put("boz", "application/x-bzip2");
        map.put("xht", "application/xhtml+xml");
        map.put("m4a", "audio/mp4");
        map.put("abc", "text/vndabc");
        map.put("wmls", "text/vndwapwmlscript");
        map.put("vsmdi", "application/xml");
        map.put("sr2", "image/sr2");
        map.put("iii", "application/x-iphone");
        map.put("testsettings", "application/xml");
        map.put("3gp", "video/3gpp");
        map.put("spr", "application/x-sprite");
        map.put("help", "application/x-helpfile");
        map.put("m3u", "audio/x-mpegurl");
        map.put("spl", "application/futuresplash");
        map.put("zoo", "application/octet-stream");
        map.put("wmlc", "application/vndwapwmlc");
        map.put("psc1", "application/powershell");
        map.put("vuze", "application/x-vuze");
        map.put("rpm", "audio/x-pn-realaudio-plugin");
        map.put("spc", "application/x-pkcs7-certificates");
        map.put("naplps", "image/naplps");
        map.put("aab", "application/x-authorware-bin");
        map.put("aac", "audio/vnd.dlna.adts");
        map.put("dxf", "application/dxf");
        map.put("xoml", "text/plain");
        map.put("sol", "text/plain");
        map.put("sor", "text/plain");
        map.put("addin", "text/xml");
        map.put("pfx", "application/x-pkcs12");
        map.put("dxr", "application/x-director");
        map.put("aam", "application/x-authorware-map");
        map.put("aas", "application/x-authorware-seg");
        map.put("ac3", "audio/vnd.dolby.dd-raw");
        map.put("m4v", "video/mp4");
        map.put("pl", "text/plain");
        map.put("pm", "image/x-xpixmap");
        map.put("pgn", "application/x-chess-pgn");
        map.put("bat", "application/bat");
        map.put("pgm", "image/x-portable-graymap");
        map.put("bas", "text/plain");
        map.put("filters", "application/xml");
        map.put("css", "text/css");
        map.put("m2a", "audio/mpeg");
        map.put("dwg", "application/acad");
        map.put("dwf", "drawing/x-dwf");
        map.put("srw", "image/srw");
        map.put("vsscc", "text/plain");
        map.put("hgl", "application/vndhp-hpgl");
        map.put("snippet", "application/xml");
        map.put("3g2", "video/3gpp2");
        map.put("m1v", "video/mpeg");
        map.put("jpe", "image/jpeg");
        map.put("rnx", "application/vndrn-realplayer");
        map.put("igs", "model/iges");
        map.put("asmx", "application/xml");
        map.put("srf", "text/plain");
        map.put("rng", "application/ringing-tones");
        map.put("me", "application/x-troff-me");
        map.put("ksh", "application/x-ksh");
        map.put("src", "application/x-wais-source");
        map.put("mk", "text/plain");
        map.put("mm", "application/base64");
        map.put("rmm", "audio/x-pn-realaudio");
        map.put("dvi", "application/x-dvi");
        map.put("rmp", "audio/x-pn-realaudio");
        map.put("ms", "application/x-troff-ms");
        map.put("mv", "video/x-sgi-movie");
        map.put("movie", "video/x-sgi-movie");
        map.put("qif", "image/x-quicktime");
        map.put("setreg", "application/set-registration-initiation");
        map.put("my", "audio/make");
        map.put("m2v", "video/mpeg");
        map.put("master", "application/xml");
        map.put("jpg", "image/jpeg");
        map.put("m2t", "video/vnd.dlna.mpeg-tts");
        map.put("vrml", "model/vrml");
        map.put("vbproj", "text/plain");
        map.put("nc", "application/x-netcdf");
        map.put("pef", "image/pef");
        map.put("cur", "text/plain");
        map.put("jps", "image/x-jps");
        map.put("osdx", "application/opensearchdescription+xml");
        map.put("rmi", "audio/mid");
        map.put("lzh", "application/octet-stream");
        map.put("m14", "application/x-msmediaview");
        map.put("sln", "text/plain");
        map.put("js", "application/javascript");
        map.put("chat", "application/x-chat");
        map.put("bcpio", "application/x-bcpio");
        map.put("vssettings", "text/xml");
        map.put("afl", "video/animaflex");
        map.put("turbot", "image/florian");
        map.put("gitmodules", "text/plain");
        map.put("inc", "text/plain");
        map.put("cxx", "text/plain");
        map.put("plx", "application/x-pixclscript");
        map.put("xaml", "application/xaml+xml");
        map.put("m13", "application/x-msmediaview");
        map.put("mesh", "model/mesh");
        map.put("mzz", "application/x-vndaudioexplosionmzz");
        map.put("gzip", "application/x-gzip");
        map.put("skm", "application/x-koan");
        map.put("aifc", "audio/x-aiff");
        map.put("ins", "application/x-internett-signup");
        map.put("vstemplate", "text/xml");
        map.put("inf", "application/inf");
        map.put("pkg", "application/x-newton-compatible-pkg");
        map.put("skp", "application/x-koan");
        map.put("aiff", "audio/x-aiff");
        map.put("ini", "text/plain");
        map.put("skt", "application/x-koan");
        map.put("inl", "text/plain");
        map.put("xbm", "image/x-xbitmap");
        map.put("pko", "application/vnd.ms-pki.pko");
        map.put("lzx", "application/lzx");
        map.put("pm4", "application/x-pagemaker");
        map.put("mp4v", "video/mp4");
        map.put("la", "audio/nspaudio");
        map.put("eprtx", "model/vnd.eprtx+xps");
        map.put("pm5", "application/x-pagemaker");
        map.put("skd", "application/x-koan");
        map.put("aspx", "application/xml");
        map.put("exe", "application/x-msdownload");
        map.put("wsdl", "application/xml");
        map.put("wax", "audio/x-ms-wax");
        map.put("moov", "video/quicktime");
        map.put("hh", "text/plain");
        map.put("wav", "audio/wav");
        map.put("txt", "text/plain");
        map.put("mpv2", "video/mpeg");
        map.put("psess", "application/xml");
        map.put("hlb", "text/x-script");
        map.put("adt", "audio/vnd.dlna.adts");
        map.put("roff", "application/x-troff");
        map.put("snd", "audio/basic");
        map.put("rast", "image/cmu-raster");
        map.put("zip", "application/zip");
        map.put("application", "application/x-ms-application");
        map.put("wb1", "application/x-qpro");
        map.put("it", "audio/it");
        map.put("iv", "application/x-inventor");
        map.put("dgml", "application/xml");
        map.put("pkgundef", "text/plain");
        map.put("list", "text/plain");
        map.put("pic", "image/pict");
        map.put("hlp", "application/hlp");
        map.put("ip", "application/x-ip2");
        map.put("accountpicture-ms", "application/windows-accountpicture");
        map.put("ima", "application/x-ima");
        map.put("adts", "audio/vnd.dlna.adts");
        map.put("xaf", "x-world/x-vrml");
        map.put("acx", "application/internet-property-stream");
        map.put("smf", "application/vnd.stardivision.math");
        map.put("ustar", "application/x-ustar");
        map.put("smi", "application/smil");
        map.put("step", "application/step");
        map.put("tcsh", "text/x-scripttcsh");
        map.put("mpg", "video/mpeg");
        map.put("rwl", "image/rwl");
        map.put("mpe", "video/mpeg");
        map.put("xl", "application/excel");
        map.put("xpix", "application/x-vndls-xpix");
        map.put("mpc", "application/x-project");
        map.put("xm", "audio/xm");
        map.put("mpp", "application/vndms-project");
        map.put("jxr", "image/vnd.ms-photo");
        map.put("csproj", "text/plain");
        map.put("pov", "model/x-pov");
        map.put("pfunk", "audio/make");
        map.put("swf", "application/x-shockwave-flash");
        map.put("pot", "application/mspowerpoint");
        map.put("dot", "application/msword");
        map.put("uue", "text/x-uuencode");
        map.put("vql", "audio/x-twinvq-plugin");
        map.put("sxc", "application/vnd.sun.xml.calc");
        map.put("sxd", "application/vnd.sun.xml.draw");
        map.put("mpx", "application/x-project");
        map.put("vqf", "audio/x-twinvq");
        map.put("oxt", "application/vnd.openofficeorg.extension");
        map.put("mpv", "application/x-project");
        map.put("vqe", "audio/x-twinvq-plugin");
        map.put("hlsl", "text/plain");
        map.put("mpt", "application/x-project");
        map.put("bcuri", "application/x-bc-uri");
        map.put("lsproj", "text/plain");
        map.put("hpp", "text/plain");
        map.put("ppz", "application/mspowerpoint");
        map.put("vrt", "x-world/x-vrt");
        map.put("sxm", "application/vnd.sun.xml.math");
        map.put("wm", "video/x-ms-wm");
        map.put("mod", "video/mpeg");
        map.put("isp", "application/x-internet-signup");
        map.put("sxi", "application/vnd.sun.xml.impress");
        map.put("wp", "application/wordperfect");
        map.put("wvx", "video/x-ms-wvx");
        map.put("pps", "application/mspowerpoint");
        map.put("hpg", "application/vndhp-hpgl");
        map.put("isu", "video/x-isvideo");
        map.put("trm", "application/x-msterminal");
        map.put("ppt", "application/mspowerpoint");
        map.put("log", "text/plain");
        map.put("clp", "application/x-msclip");
        map.put("sxg", "application/vnd.sun.xml.writer.global");
        map.put("mov", "video/quicktime");
        map.put("qtif", "image/x-quicktime");
        map.put("ppm", "image/x-portable-pixmap");
        map.put("ppa", "application/vndms-powerpoint");
        map.put("etx", "text/x-setext");
        map.put("nws", "message/rfc822");
        map.put("mpa", "audio/mpeg");
        map.put("gitignore", "text/plain");
        map.put("sxw", "application/vnd.sun.xml.writer");
        map.put("trx", "application/xml");
        map.put("vsw", "application/x-visio");
        map.put("cmx", "image/x-cmx");
        map.put("ivr", "i-world/i-vrml");
        map.put("easmx", "model/vnd.easmx+xps");
        map.put("pmw", "application/x-perfmon");
        map.put("lnk", "application/x-ms-shortcut");
        map.put("hpgl", "application/vndhp-hpgl");
        map.put("sys", "video/x-mpeg-system");
        map.put("xhtml", "application/xhtml+xml");
        map.put("tsi", "audio/tsp-audio");
        map.put("dms", "application/octet-stream");
        map.put("rw2", "image/rw2");
        map.put("pmr", "application/x-perfmon");
        map.put("conf", "text/plain");
        map.put("ivy", "application/x-livescreen");
        map.put("axs", "application/olescript");
        map.put("vst", "application/x-visio");
        map.put("mrw", "image/mrw");
        map.put("wsc", "text/scriptlet");
        map.put("pml", "application/x-perfmon");
        map.put("pict", "image/pict");
        map.put("tsp", "application/dsptype");
        map.put("tsv", "text/tab-separated-values");
        map.put("vsi", "application/ms-vsi");
        map.put("uu", "application/octet-stream");
        map.put("pmc", "application/x-perfmon");
        map.put("pma", "application/x-perfmon");
        map.put("vb", "text/plain");
        map.put("vsd", "application/x-visio");
        map.put("vivo", "video/vndvivo");
        map.put("s3m", "audio/s3m");
        map.put("vsto", "application/x-ms-vsto");
        map.put("jut", "image/jutvision");
        map.put("xsl", "text/xml");
        map.put("xsr", "video/x-amt-showrun");
        map.put("zsh", "text/x-scriptzsh");
        map.put("xss", "application/xml");
        map.put("nvd", "application/x-navidoc");
        map.put("lma", "audio/nspaudio");
        map.put("dvr-ms", "video/x-ms-dvr");
        map.put("tts", "video/vnd.dlna.mpeg-tts");
        map.put("p7m", "application/pkcs7-mime");
        map.put("pnm", "image/x-portable-anymap");
        map.put("mp2v", "video/mpeg");
        map.put("xslt", "application/xml");
        map.put("cod", "text/plain");
        map.put("png", "image/png");
        map.put("w6w", "application/msword");
        map.put("p7s", "application/pkcs7-signature");
        map.put("doc", "application/msword");
        map.put("xsd", "application/xml");
        map.put("xsc", "application/xml");
        map.put("latex", "application/x-latex");
        map.put("wtk", "application/x-wintalk");
        map.put("part", "application/pro_eng");
        map.put("evy", "application/envoy");
        map.put("mrc", "application/marc");
        map.put("p7r", "application/x-pkcs7-certreqresp");
        map.put("p7a", "application/x-pkcs7-signature");
        map.put("p7b", "application/x-pkcs7-certificates");
        map.put("mts", "video/vnd.dlna.mpeg-tts");
        map.put("com", "application/octet-stream");
        map.put("ts", "video/vnd.dlna.mpeg-tts");
        map.put("jpeg", "image/jpeg");
        map.put("smil", "application/smil");
        map.put("tr", "application/x-troff");
        map.put("dsw", "text/plain");
        map.put("p7c", "application/pkcs7-mime");
        map.put("ips", "application/x-ipscript");
        map.put("jtx", "application/x-jtx+xps");
        map.put("f77", "text/x-fortran");
        map.put("sprite", "application/x-sprite");
        map.put("ssm", "application/streamingmedia");
        map.put("dsp", "text/plain");
        map.put("ssi", "text/x-server-parsed-html");
        map.put("mhtml", "message/rfc822");
        map.put("psd", "application/octet-stream");
        map.put("sv4crc", "application/x-sv4crc");
        map.put("ltx", "application/x-latex");
        map.put("htx", "text/html");
        map.put("sst", "application/vnd.ms-pki.certstore");
        map.put("oth", "application/vnd.oasis.opendocument.text-web");
        map.put("otg", "application/vnd.oasis.opendocument.graphics-template");
        map.put("htt", "text/webviewhtml");
        map.put("ott", "application/vnd.oasis.opendocument.text-template");
        map.put("ots", "application/vnd.oasis.opendocument.spreadsheet-template");
        map.put("ipx", "application/x-ipix");
        map.put("dtd", "application/xml-dtd");
        map.put("xmz", "xgl/movie");
        map.put("fsx", "text/plain");
        map.put("otp", "application/vnd.oasis.opendocument.presentation-template");
        map.put("xrm-ms", "text/xml");
        map.put("htm", "text/html");
        map.put("sl", "application/x-seelogo");
        map.put("text", "application/plain");
        map.put("cr2", "image/cr2");
        map.put("cpp", "text/plain");
        map.put("jsp", "text/html");
        map.put("stc", "application/vnd.sun.xml.calc.template");
        map.put("cpt", "application/mac-compactpro");
        map.put("std", "application/vnd.sun.xml.draw.template");
        map.put("stm", "text/html");
        map.put("lsp", "application/x-lisp");
        map.put("hta", "application/hta");
        map.put("xml", "text/xml");
        map.put("htc", "text/x-component");
        map.put("stp", "application/step");
        map.put("rtf", "text/rtf");
        map.put("sh", "application/x-sh");
        map.put("sti", "application/vnd.sun.xml.impress.template");
        map.put("pub", "application/x-mspublisher");
        map.put("lst", "text/plain");
        map.put("texi", "application/x-texinfo");
        map.put("uri", "text/uri-list");
        map.put("msh", "model/mesh");
        map.put("url", "wwwserver/redirection");
        map.put("stl", "application/vnd.ms-pki.stl");
        map.put("x-png", "image/png");
        map.put("lsx", "text/x-la-asf");
        map.put("f90", "text/plain");
        map.put("eps", "application/postscript");
        map.put("w60", "application/wordperfect60");
        map.put("w61", "application/wordperfect61");
        map.put("sitemap", "application/xml");
        map.put("stw", "application/vnd.sun.xml.writer.template");
        map.put("shtml", "text/html");
        map.put("rt", "text/richtext");
        map.put("qti", "image/x-quicktime");
        map.put("rv", "video/vndrn-realvideo");
        map.put("xlt", "application/excel");
        map.put("qtc", "video/x-qtc");
        map.put("wmlsc", "application/vndwapwmlscriptc");
        map.put("xlv", "application/excel");
        map.put("rtx", "text/richtext");
        map.put("xlw", "application/vndms-excel");
        map.put("xps", "application/vnd.ms-xpsdocument");
        map.put("nsc", "application/x-conference");
        map.put("rp", "image/vndrn-realpix");
        map.put("tod", "video/mpeg");
        map.put("cat", "application/vnd.ms-pki.seccat");
        map.put("dump", "application/octet-stream");
        map.put("rm", "audio/x-pn-realaudio");
        map.put("erf", "image/erf");
        map.put("rf", "image/vndrn-realflash");
        map.put("nrw", "image/nrw");
        map.put("mid", "audio/mid");
        map.put("rc", "text/plain");
        map.put("gsd", "audio/x-gsm");
        map.put("xpm", "image/x-xpixmap");
        map.put("cpio", "application/x-cpio");
        map.put("ra", "audio/x-realaudio");
        map.put("cab", "application/vnd.ms-cab-compressed");
        map.put("crd", "application/x-mscardfile");
        map.put("lsf", "video/x-la-asf");
        map.put("3dm", "x-world/x-3dmf");
        map.put("crl", "application/pkix-crl");
        map.put("gtar", "application/x-gtar");
        map.put("qt", "video/quicktime");
        map.put("svc", "application/xml");
        map.put("niff", "image/x-niff");
        map.put("drw", "application/drafting");
        map.put("crt", "application/x-x509-ca-cert");
        map.put("svf", "image/vnddwg");
        map.put("svg", "image/svg+xml");
        map.put("prt", "application/pro_eng");
        map.put("crw", "image/crw");
        map.put("fsi", "text/plain");
        map.put("torrent", "application/x-bittorrent");
        map.put("svr", "application/x-world");
        map.put("mvb", "application/x-msmediaview");
        map.put("prf", "application/pics-rules");
        map.put("frl", "application/freeloader");
        map.put("coverage", "application/xml");
        map.put("gitattributes", "text/plain");
        map.put("py", "text/plain");
        map.put("pre", "application/x-freelance");
        map.put("hqx", "application/mac-binhex40");
        map.put("unis", "text/uri-list");
        map.put("xof", "x-world/x-vrml");
        map.put("wvx", "video/x-ms-wvx");
        map.put("html", "text/html");
        map.put("csh", "application/x-csh");
        map.put("ps", "application/postscript");
        map.put("lhx", "application/octet-stream");
        map.put("tif", "image/tiff");
        map.put("for", "text/plain");
        map.put("wma", "audio/x-ms-wma");
        map.put("resjson", "text/plain");
        map.put("hxx", "text/plain");
        map.put("mht", "message/rfc822");
        map.put("wmd", "application/x-ms-wmd");
        map.put("wmf", "image/x-wmf");
        map.put("arc", "application/octet-stream");
        map.put("cco", "application/x-cocoa");
        map.put("wml", "text/vndwapwml");
        map.put("mpga", "audio/mpeg");
        map.put("config", "application/xml");
        map.put("pwz", "application/vndms-powerpoint");
        map.put("lha", "application/octet-stream");
        map.put("elc", "application/x-bytecodeelisp");
        map.put("arj", "application/arj");
        map.put("tiff", "image/tiff");
        map.put("fpx", "image/vndfpx");
        map.put("testrunconfig", "application/xml");
        map.put("wmx", "video/x-ms-wmx");
        map.put("arw", "image/arw");
        map.put("kar", "audio/midi");
        map.put("vsix", "application/vsix");
        map.put("mpeg", "video/mpeg");
        map.put("tbk", "application/toolbook");
        map.put("wmv", "video/x-ms-wmv");
        map.put("dht", "application/x-dht");
        map.put("unv", "application/i-deas");
        map.put("saveme", "application/octet-stream");
        map.put("art", "image/x-jg");
        map.put("wmz", "application/x-ms-wmz");
        map.put("asa", "application/xml");
        map.put("dib", "image/bmp");
        map.put("gsm", "audio/x-gsm");
        map.put("uni", "text/uri-list");
        map.put("viv", "video/vndvivo");
        map.put("gss", "application/x-gss");
        map.put("sbk", "application/x-tbook");
        map.put("gsp", "application/x-gsp");
        map.put("pyc", "applicaiton/x-bytecodepython");
        map.put("asf", "video/x-ms-asf");
        map.put("asc", "text/plain");
        map.put("library-ms", "application/windows-library+xml");
        map.put("aif", "audio/aiff");
        map.put("tar", "application/x-tar");
        map.put("mime", "www/mime");
        map.put("asp", "text/asp");
        map.put("emf", "image/x-emf");
        map.put("cdf", "application/x-netcdf");
        map.put("asm", "text/plain");
        map.put("bsh", "application/x-bsh");
        map.put("wp5", "application/wordperfect");
        map.put("wp6", "application/wordperfect");
        map.put("xyz", "chemical/x-pdb");
        map.put("der", "application/x-x509-ca-cert");
        map.put("vspscc", "text/plain");
        map.put("tcl", "application/x-tcl");
        map.put("aos", "application/x-nokia-9000-communicator-add-on-software");
        map.put("orf", "image/orf");
        map.put("ccad", "application/clariscad");
        map.put("cer", "application/x-x509-ca-cert");
        map.put("contact", "text/x-ms-contact");
        map.put("def", "text/plain");
        map.put("mjf", "audio/x-vndaudioexplosionmjuicemediafile");
        map.put("m2v", "video/mpeg");
        map.put("wks", "application/vndms-works");
        map.put("iges", "model/iges");
        map.put("sgml", "text/sgml");
        map.put("pvu", "paleovu/x-pv");
        map.put("tli", "text/plain");
        map.put("iefs", "image/ief");
        map.put("tlh", "text/plain");
        map.put("uls", "text/iuls");
        map.put("aps", "application/mime");
        map.put("vcproj", "application/xml");
        map.put("p10", "application/pkcs10");
        map.put("vsixlangpack", "text/xml");
        map.put("env", "application/x-envoy");
        map.put("silo", "model/mesh");
        map.put("p12", "application/x-pkcs12");
        map.put("mif", "application/vndmif");
        map.put("m1v", "video/mpeg");
        map.put("mid", "audio/midi");
        map.put("ruleset", "application/xml");
        map.put("mjpg", "video/x-motion-jpeg");
        map.put("asax", "application/xml");
        map.put("uil", "text/x-uil");
        map.put("vmd", "application/vocaltec-media-desc");
        map.put("vmf", "application/vocaltec-media-file");
        map.put("cha", "application/x-chat");
        map.put("skin", "application/xml");
        map.put("wsrc", "application/x-wais-source");
        map.put("rdlc", "application/xml");
        map.put("avs", "video/avs-video");
        map.put("sv4cpio", "application/x-sv4cpio");
        map.put("funk", "audio/make");
        map.put("wmd", "application/x-ms-wmd");
        map.put("avi", "video/avi");
        map.put("searchconnector-ms", "application/windows-search-connector+xml");
        map.put("fli", "video/x-fli");
        map.put("wri", "application/mswrite");
        map.put("class", "application/octet-stream");
        map.put("properties", "text/plain");
        map.put("flr", "x-world/x-vrml");
        map.put("flo", "image/florian");
        map.put("flx", "text/vndfmiflexstor");
        map.put("imap", "application/x-httpd-imap");
        map.put("wrz", "model/vrml");
        map.put("resx", "application/xml");
        map.put("tex", "application/x-tex");
        map.put("fmf", "video/x-atomic3d-feature");
        map.put("dll", "application/x-msdownload");
        map.put("word", "application/msword");
        map.put("wrl", "model/vrml");
        map.put("pkgdef", "text/plain");
        map.put("xbap", "application/x-ms-xbap");
        map.put("mny", "application/x-msmoney");
        map.put("fif", "application/fractals");
        map.put("mp4", "video/mp4");
        map.put("mp3", "audio/mpeg");
        map.put("snd", "audio/basic");
        map.put("fsscript", "text/plain");
        map.put("voc", "audio/voc");
        map.put("pyw", "text/plain");
        map.put("asr", "video/x-ms-asf");
        map.put("asx", "application/x-mplayer2");
        map.put("dir", "application/x-director");
        map.put("mp2", "audio/mpeg");
        map.put("dif", "video/x-dv");
        map.put("wq1", "application/x-lotus");
        map.put("vos", "video/vosaic");
        map.put("bz2", "application/x-bzip2");
        map.put("vox", "audio/voxware");
        map.put("nif", "image/x-niff");
        map.put("website", "application/x-mswebsite");
        map.put("wpd", "application/wordperfect");
        map.put("omc", "application/x-omc");
        map.put("vsct", "text/xml");
        map.put("xwd", "image/x-xwindowdump");
        map.put("sdml", "text/plain");
        map.put("wmx", "video/x-ms-wmx");
        map.put("nix", "application/x-mix-transfer");
        map.put("wmz", "application/x-ms-wmz");
        map.put("wps", "application/vndms-works");
        map.put("book", "application/book");
        map.put("group", "text/x-ms-group");
        map.put("wpl", "application/vnd.ms-wpl");
        map.put("tgz", "application/gnutar");
        map.put("user", "text/plain");
        map.put("mme", "application/base64");
        map.put("ogx", "application/ogg");
        map.put("ogv", "video/ogg");
        map.put("oga", "audio/ogg");
        map.put("ogg", "audio/ogg");
        map.put("spx", "audio/ogg");
        map.put("flac", "audio/flac");
        map.put("anx", "application/annodex");
        map.put("axa", "audio/annodex");
        map.put("axv", "video/annodex");
        map.put("xspf", "application/xspf+xml");
        map.put("kate", "application/kate");
        map.put("flv", "video/x-flv");
        map.put("f4v", "video/mp4");
        map.put("f4p", "video/mp4");
        map.put("f4a", "audio/mp4");
        map.put("f4b", "audio/mp4");
        map.put("mka", "audio/x-matroska");
        map.put("mkv", "video/x-matroska");
        map.put("mk3d", "video/x-matroska-3d");
        map.put("webm", "video/webm");
        return map;
    }
}
