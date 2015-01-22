// TODO: change package name
package org.sample.cryptobenchmark;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class BruteForcer {
    private Charset charset;
    private PlainGenerator plainGenerator;
    private static int length = 4;
    private boolean found = false;
    private int sample[] = new int[]{0x95, 0xeb, 0xc3, 0xc7, 0xb3, 0xb9, 0xf1, 0xd2, 0xc4, 0x0f, 0xec, 0x14, 0x41, 0x5d, 0x3c, 0xb8};

    public BruteForcer() {
        charset = new Charset("qwertyuiopasdfghjklzxcvbnm");
        plainGenerator = new PlainGenerator(charset);

    }

    private boolean fastCompare(int[] sample, byte[] hash) {
        for (int i = 0; i < sample.length; ++i) {
            if (sample[i] != (hash[i] & 0xff)) {
                return false;
            }
        }
        return true;
    }

    public int crack() {

        int i = 0;
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");

            System.out.println(plainGenerator.getVariation((int) Math.pow(26, 4) - 1, length));

            System.out.println(md.digest(plainGenerator.getVariation(0, length).getBytes())[0]);
            for (i = 0; i < Math.pow(charset.getLength(), length); ++i) {
                byte[] table = md.digest(plainGenerator.getVariation(i, length).getBytes());
                if (fastCompare(sample, table)) {
                    found = true;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("Bruteforcer: ", "No such algorithm");
        }
        return i;
    }

}
