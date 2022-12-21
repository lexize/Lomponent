package org.lexize.lomponent;

public class LomponentUtils {
    public static byte[] fromHex(String hexString) {
        byte[] hexBytes = new byte[(int)Math.ceil(hexString.length()/2f)];
        for (int i = 0; i < hexString.length(); i++) {
            int byteNum = i/2;
            char c = Character.toLowerCase(hexString.charAt(i));
            byte hexNum = (byte) Character.digit(c, 16);
            hexBytes[byteNum] = (byte) (hexBytes[byteNum] + (hexNum << (i % 2 == 0 ? 0 : 4)));
        }
        return hexBytes;
    }
}
