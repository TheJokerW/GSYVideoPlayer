/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2020, FrostWire(R). All rights reserved.
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

package com.joker.android.gui.util;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.google.android.material.snackbar.Snackbar;
import com.joker.android.core.ConfigurationManager;
import com.joker.util.Logger;
import com.joker.util.MimeDetector;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

/**
 * @author gubatron
 * @author aldenml
 * @author votaguz
 */
public final class UIUtils {

    private static final Logger LOG = Logger.getLogger(UIUtils.class);

    /**
     * Localizable Number Format constant for the current default locale.
     */
    private static final NumberFormat NUMBER_FORMAT0; // localized "#,##0"

    private static final String[] BYTE_UNITS = new String[]{"b", "KB", "Mb", "Gb", "Tb"};

    private static final String GENERAL_UNIT_KBPSEC = "KB/s";


    static {
        NUMBER_FORMAT0 = NumberFormat.getNumberInstance(Locale.getDefault());
        NUMBER_FORMAT0.setMaximumFractionDigits(0);
        NUMBER_FORMAT0.setMinimumFractionDigits(0);
        NUMBER_FORMAT0.setGroupingUsed(true);
    }

    private static void showToastMessage(Context context, String message, int duration, int gravity, int xOffset, int yOffset) {
        if (context != null && message != null) {
            Toast toast = Toast.makeText(context, message, duration);
            if (gravity != (Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM)) {
                toast.setGravity(gravity, xOffset, yOffset);
            }
            toast.show();
        }
    }

    public static void showShortMessage(View view, int resourceId) {
        Snackbar.make(view, resourceId, Snackbar.LENGTH_SHORT).show();
    }

    public static void showLongMessage(View view, int resourceId) {
        Snackbar.make(view, resourceId, Snackbar.LENGTH_LONG).show();
    }

    public static void showLongMessage(Context context, int resourceId, Object... formatArgs) {
        showLongMessage(context, context.getResources().getString(resourceId, formatArgs));
    }




    public static void showToastMessage(Context context, String message, int duration) {
        showToastMessage(context, message, duration, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
    }

    public static void showShortMessage(Context context, String message) {
        showToastMessage(context, message, Toast.LENGTH_SHORT);
    }

    public static void showLongMessage(Context context, String message) {
        showToastMessage(context, message, Toast.LENGTH_LONG);
    }

    public static void showShortMessage(Context context, int resId) {
        showShortMessage(context, context.getString(resId));
    }

    public static void showLongMessage(Context context, @StringRes int resId) {
        showLongMessage(context, context.getString(resId));
    }

    public static void showShortMessage(Context context, int resId, Object... formatArgs) {
        showShortMessage(context, context.getString(resId, formatArgs));
    }






    public static String getBytesInHuman(double size) {
        int i;
        float sizeFloat = (float) size;
        for (i = 0; sizeFloat > 1024; i++) {
            sizeFloat /= 1024f;
        }
        return String.format(Locale.US, "%.2f %s", sizeFloat, BYTE_UNITS[i]);
    }

    /**
     * Converts an rate into a human readable and localized KB/s speed.
     */
    public static String rate2speed(double rate) {
        return NUMBER_FORMAT0.format(rate) + " " + GENERAL_UNIT_KBPSEC;
    }






    /**
     * Takes a screenshot of the given view
     *
     * @return File with jpeg of the screenshot taken. null if there was a problem.
     */
    public static File takeScreenshot(View view) {
        view.setDrawingCacheEnabled(true);
        try {
            Thread.sleep(300);
        } catch (Throwable ignore) {
        }
        Bitmap drawingCache = null;
        try {
            drawingCache = view.getDrawingCache();
        } catch (Throwable ignored) {
        }
        Bitmap screenshotBitmap = null;
        if (drawingCache != null) {
            try {
                screenshotBitmap = Bitmap.createBitmap(drawingCache);
            } catch (Throwable ignored) {
            }
        }
        view.setDrawingCacheEnabled(false);
        if (screenshotBitmap == null) {
            return null;
        }
        File screenshotFile = new File(Environment.getExternalStorageDirectory().toString(), "fwPlayerScreenshot.tmp.jpg");
        if (screenshotFile.exists()) {
            screenshotFile.delete();
            try {
                screenshotFile.createNewFile();
            } catch (IOException ignore) {
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(screenshotFile);
            screenshotBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Throwable t) {
            screenshotFile.delete();
            screenshotFile = null;
        }
        return screenshotFile;
    }

    public static void openURL(Context context, String url) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            // ignore
            // yes, it happens
        }
    }

    public static void setupClickUrl(View v, final String url) {
        v.setOnClickListener(view -> UIUtils.openURL(view.getContext(), url));
    }

    public static String getMimeType(String filePath) {
        try {
            return MimeDetector.getMimeType(FilenameUtils.getExtension(filePath));
        } catch (Throwable e) {
            LOG.error("Failed to read mime type for: " + filePath);
            return MimeDetector.UNKNOWN;
        }
    }





    public static void showKeyboard(Context context, View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static void hideKeyboardFromActivity(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }




    // tried playing around with <T> but at the moment I only need ByteExtra's, no need to over engineer.
    public static class IntentByteExtra {
        public final String name;
        public final byte value;

        public IntentByteExtra(String name, byte value) {
            this.name = name;
            this.value = value;
        }
    }

    public static void broadcastAction(Context ctx, String actionCode, IntentByteExtra... extras) {
        if (ctx == null || actionCode == null) {
            return;
        }
        final Intent intent = new Intent(actionCode);
        if (extras != null && extras.length > 0) {
            for (IntentByteExtra extra : extras) {
                intent.putExtra(extra.name, extra.value);
            }
        }
        ctx.sendBroadcast(intent);
    }


    public static double getScreenInches(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x_sq = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y_sq = Math.pow(dm.heightPixels / dm.ydpi, 2);
        // Thank you Pitagoras
        return Math.sqrt(x_sq + y_sq);
    }

    public static boolean isPortrait(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels > dm.widthPixels;
    }

    /**
     * @param thresholdPreferenceKey - preference key for an int threshold
     * @return true if the threshold is >= 100, otherwise true if the dice roll is below the threshold
     */
    public static boolean diceRollPassesThreshold(ConfigurationManager cm, String thresholdPreferenceKey) {
        int thresholdValue = cm.getInt(thresholdPreferenceKey);
        int diceRoll = new Random().nextInt(100) + 1; //1-100
        if (thresholdValue <= 0) {
            LOG.info("diceRollPassesThreshold(" + thresholdPreferenceKey + "=" + thresholdValue + ") -> false");
            return false;
        }
        if (thresholdValue >= 100) {
            LOG.info("diceRollPassesThreshold(" + thresholdPreferenceKey + "=" + thresholdValue + ") -> true (always)");
            return true;
        }
        LOG.info("diceRollPassesThreshold(" + thresholdPreferenceKey + "=" + thresholdValue + ", roll=" + diceRoll + ") -> " + (diceRoll <= thresholdValue));
        return diceRoll <= thresholdValue;
    }


    public static boolean isScreenLocked(final Context context) {
        if (context == null) {
            return true;
        }
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        if (km == null) {
            return true;
        }
        return km.isKeyguardLocked();
    }

}
