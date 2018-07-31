package com.roshine.lstypechoblog.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Roshine
 * @date 2017/8/14 15:08
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc io流关闭管理
 */
public class IOUtil {

    private IOUtil() {
        throw new AssertionError();
    }

    public static void close(Closeable... closeable) {
        if (closeable != null) {
            try {
                for (int i = 0; i < closeable.length; i++) {
                    closeable[i].close();
                }
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            }
        }
    }

    /**
     * Close closable and hide possible {@link IOException}
     * @param closeable closeable object
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // Ignored
            }
        }
    }

}