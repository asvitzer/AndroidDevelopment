package com.alvinsvitzer.blamegame;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by Alvin on 2/3/16.
 */
public class PictureUtils {

    public static Bitmap getScaledBitmap(String path, Activity activity){

        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);

        return getScaledBitmap(path, size.x, size.y);

    }

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight){

        // Read in the dimensons of the image on desk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        //Find scaling needed
        int inSampleSize = 1;

        if (srcHeight > destHeight || srcWidth > destHeight) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / srcWidth);
            } else {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeFile(path, options);

    }



}
