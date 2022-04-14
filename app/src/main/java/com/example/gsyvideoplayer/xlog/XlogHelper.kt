package com.example.gsyvideoplayer.xlog

import android.os.Environment
import com.example.gsyvideoplayer.BuildConfig
import com.example.gsyvideoplayer.XCApplication
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Log.setConsoleLogOpen
import com.tencent.mars.xlog.Xlog
import com.tencent.mars.xlog.Xlog.XLogConfig

/**
 * @ClassName XlogHelper
 * @Description TODO
 * @Author huangziwen
 * @Date 2022/4/13 8:32 下午
 */
class XlogHelper {
    companion object {
        fun initXlog(application: XCApplication) {
            System.loadLibrary("c++_shared")
            System.loadLibrary("marsxlog")

            val SDCARD = Environment.getExternalStorageDirectory().absolutePath
            val logPath = "$SDCARD/marssample/log"
            // this is necessary, or may crash for SIGBUS
            val cachePath: String = application.filesDir.toString() + "/xlog"
            val logFileName = "XCLOG"
            //init xlog
            val logConfig = XLogConfig()
            logConfig.mode = Xlog.AppednerModeAsync
            logConfig.logdir = logPath
            logConfig.nameprefix = logFileName
            logConfig.pubkey = ""
            logConfig.compressmode = Xlog.ZLIB_MODE
            logConfig.compresslevel = 0
            logConfig.cachedir = ""
            logConfig.cachedays = 0
            if (BuildConfig.DEBUG) {
                logConfig.level = Xlog.LEVEL_VERBOSE
                setConsoleLogOpen(true)
            } else {
                logConfig.level = Xlog.LEVEL_INFO
                setConsoleLogOpen(false)
            }

            Log.setLogImp(Xlog())
        }
    }
}
