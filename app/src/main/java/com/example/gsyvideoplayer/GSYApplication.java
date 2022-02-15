package com.example.gsyvideoplayer;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDexApplication;

import com.example.gsyvideoplayer.exosource.GSYExoHttpDataSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.joker.android.AndroidPlatform;
import com.joker.bittorrent.BTContext;
import com.joker.bittorrent.BTEngine;
import com.joker.platform.Platforms;
import com.joker.platform.SystemPaths;
import com.joker.util.Ref;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import tv.danmaku.ijk.media.exo2.ExoMediaSourceInterceptListener;
import tv.danmaku.ijk.media.exo2.ExoSourceManager;

/**
 * Created by shuyu on 2016/11/11.
 */

public class GSYApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new BTEngineInitializer(Ref.weak(this))).start();
        Platforms.set(new AndroidPlatform(this));

        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }*/
        //LeakCanary.install(this);

        //GSYVideoType.enableMediaCodec();
        //GSYVideoType.enableMediaCodecTexture();

        //PlayerFactory.setPlayManager(Exo2PlayerManager.class);//EXO模式
        //ExoSourceManager.setSkipSSLChain(true);


        //PlayerFactory.setPlayManager(SystemPlayerManager.class);//系统模式
        //PlayerFactory.setPlayManager(IjkPlayerManager.class);//ijk模式

        //CacheFactory.setCacheManager(ExoPlayerCacheManager.class);//exo缓存模式，支持m3u8，只支持exo
        //CacheFactory.setCacheManager(ProxyCacheManager.class);//代理缓存模式，支持所有模式，不支持m3u8等

        //GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL);
        //GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL);
        //GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL);

        //GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_CUSTOM);
        //GSYVideoType.setScreenScaleRatio(9.0f/16);

        //GSYVideoType.setRenderType(GSYVideoType.SUFRACE);
        //GSYVideoType.setRenderType(GSYVideoType.GLSURFACE);

        //IjkPlayerManager.setLogLevel(IjkMediaPlayer.IJK_LOG_SILENT);

        //GSYVideoType.setRenderType(GSYVideoType.SUFRACE);

        ExoSourceManager.setExoMediaSourceInterceptListener(new ExoMediaSourceInterceptListener() {
            @Override
            public MediaSource getMediaSource(String dataSource, boolean preview, boolean cacheEnable, boolean isLooping, File cacheDir) {
                //如果返回 null，就使用默认的
                return null;
            }

            /**
             * 通过自定义的 HttpDataSource ，可以设置自签证书或者忽略证书
             * demo 里的 GSYExoHttpDataSourceFactory 使用的是忽略证书
             * */
            @Override
            public DataSource.Factory getHttpDataSourceFactory(String userAgent, @Nullable TransferListener listener, int connectTimeoutMillis, int readTimeoutMillis,
                                                               Map<String, String> mapHeadData, boolean allowCrossProtocolRedirects) {
                //如果返回 null，就使用默认的
                GSYExoHttpDataSourceFactory factory = new GSYExoHttpDataSourceFactory(userAgent, listener,
                        connectTimeoutMillis,
                        readTimeoutMillis, allowCrossProtocolRedirects);
                factory.setDefaultRequestProperties(mapHeadData);
                return factory;
            }
        });

        /*GSYVideoManager.instance().setPlayerInitSuccessListener(new IPlayerInitSuccessListener() {
            ///播放器初始化成果回调
            @Override
            public void onPlayerInitSuccess(IMediaPlayer player, GSYModel model) {
                if (player instanceof IjkExo2MediaPlayer) {

                    /// 自定义你的
                    ((IjkExo2MediaPlayer) player).setTrackSelector(new DefaultTrackSelector());


//                     DefaultTrackSelector.Parameters parameters = trackSelector.buildUponParameters()
//                        .setMaxVideoBitrate(LO_BITRATE)
//                        .setForceHighestSupportedBitrate(true)
//                        .build();
//                    trackSelector.setParameters(parameters);


                    ((IjkExo2MediaPlayer) player).setLoadControl(new DefaultLoadControl());
                }
            }
        });*/

        /*ProxyCacheManager.instance().setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };
        ProxyCacheManager.instance().setTrustAllCerts(trustAllCerts);*/


    }

    // don't try to refactor this into an async call since this guy runs on a thread
    // outside the Engine threadpool
    private static class BTEngineInitializer implements Runnable {
        private final WeakReference<Context> mainAppRef;

        BTEngineInitializer(WeakReference<Context> mainAppRef) {
            this.mainAppRef = mainAppRef;
        }

        public void run() {
            SystemPaths paths = Platforms.get().systemPaths();

            BTContext ctx = new BTContext();
            ctx.homeDir = paths.libtorrent();
            ctx.torrentsDir = paths.torrents();
            ctx.dataDir = paths.data();
            ctx.optimizeMemory = true;

            // port range [37000, 57000]
            int port0 = 37000 + new Random().nextInt(20000);
            int port1 = port0 + 10; // 10 retries
            String iface = "0.0.0.0:%1$d,[::]:%1$d";
            ctx.interfaces = String.format(Locale.US, iface, port0);
            ctx.retries = port1 - port0;

            ctx.enableDht = true;
//            Simulate slow BTContext initialization
//            try {
//                Thread.sleep(60000);
//            } catch (InterruptedException e) {
//            }
            String[] vStrArray = BuildConfig.VERSION_NAME.split("\\.");
            ctx.version[0] = Integer.parseInt(vStrArray[0]);
            ctx.version[1] = Integer.parseInt(vStrArray[1]);
            ctx.version[2] = Integer.parseInt(vStrArray[2]);

            BTEngine.ctx = ctx;
            BTEngine.onCtxSetupComplete();
            BTEngine.getInstance().start();

        }


}
}
