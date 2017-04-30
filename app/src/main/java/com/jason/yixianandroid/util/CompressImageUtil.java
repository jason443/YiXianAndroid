package com.jason.yixianandroid.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Created by jason on 2017/4/19.
 */

public class CompressImageUtil {

    private static final String TAG = "CompressImageUtil";


    /**
     * 获得图片文件的图片尺寸
     *
     * @return 返回的数组0位是宽，1位是高
     */
    private static int[] getImageSize(String imagePath) {
        int[] res = new int[2];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        BitmapFactory.decodeFile(imagePath, options);

        res[0] = options.outWidth;
        res[1] = options.outHeight;

        return res;
    }

    public static Bitmap getSmallBitmap(String filePath, int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Log.v(CompressImageUtil.class.getSimpleName(), "filePath:" + filePath + "  imSampleSize:" + inSampleSize);
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 压缩位图到指定大小， 每次压缩减少6点quality
     *
     * @param maxLenth 单位：KB
     * @return
     */
    public static byte[] compressBitmapSmallTo(Bitmap bitmap, int maxLenth) throws RuntimeException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        while (stream.toByteArray().length / 1024 > maxLenth && quality - 6 > 0) {
            stream.reset();
            quality -= 6;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        }
        return stream.toByteArray();
    }

}
