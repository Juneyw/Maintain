package com.yantang.juney.maintain.utils;

import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;

public class GzipUtils {
    /**
     * GZIP解压缩
     *
     * @param result
     * @return
     */
    public static String uncompressToString(String result) {

        byte[] bytes = Base64.decode(result, Base64.DEFAULT);


        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString("UTF-8");
        } catch (Exception e) {
//            LoggerUtil.d("uncompressToString",   e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
