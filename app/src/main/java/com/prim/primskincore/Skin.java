package com.prim.primskincore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-17 - 15:00
 * @contact https://jakeprim.cn
 * @name PrimSkinCore
 */
public class Skin implements Serializable {
    public String name;

    private String path;

    public String md5;

    public String url;

    private File file;

    public Skin(String name, String md5, String url) {
        this.name = name;
        this.md5 = md5;
        this.url = url;
    }

    public File getSkinFile(File theme) {
        if (null == file) {
            file = new File(theme, name);
        }
        path = file.getAbsolutePath();
        return file;
    }

    /**
     * 获取一个文件的md5值(可处理大文件)
     *
     * @return md5 value
     */
    public static String getSkinMD5(File file) {
        FileInputStream fis = null;
        BigInteger bi = null;
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[10240];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            byte[] digest = MD5.digest();
            bi = new BigInteger(1, digest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bi.toString(16);
    }
}
