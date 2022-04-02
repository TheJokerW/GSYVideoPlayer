package com.joker.videoCache.interfaces

import com.joker.videoCache.VideoType

/**
 * @ClassName IUrlParser
 * @Description TODO
 * @Author huangziwen
 * @Date 2022/3/28 11:52 上午
 */
interface IUrlParser {
    fun typeConvertCacheTask(url: String):  VideoType
}
