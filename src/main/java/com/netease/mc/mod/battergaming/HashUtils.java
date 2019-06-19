package com.netease.mc.mod.battergaming;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.io.InputStream;

public class HashUtils {
    public static String fileStream2Hash(InputStream stream) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] readByte = new byte[1024];
            int read = -1;
            while ((read = stream.read(readByte, 0, 1024)) != -1) {
                digest.update(readByte, 0, read);
            }
            stream.close();
            byte[] digestBytes = digest.digest();
            return new BigInteger(1, digestBytes).toString(16);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String fileName2Hash(String fileName) throws FileNotFoundException {
        return HashUtils.fileStream2Hash(new FileInputStream(fileName));
    }
}
