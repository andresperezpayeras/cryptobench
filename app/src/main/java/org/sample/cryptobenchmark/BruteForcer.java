package org.sample.cryptobenchmark;

import android.util.Log;
import android.widget.ProgressBar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// http://docs.oracle.com/javase/7/docs/api/java/security/MessageDigest.html

class BruteForcer {
    private ByteCharset byteCharset;
    private PlainGenerator plainGenerator;
    private int length;
    private boolean found = false;
    private String algorithm;

    public BruteForcer(ByteCharset byteCharset, int length) {
        this.byteCharset = byteCharset;
        this.length = length;
        plainGenerator = new PlainGenerator(byteCharset);
    }

    private boolean fastCompare(int[] sample, byte[] hash) {
        for (int i = 0; i < sample.length; ++i) {
            if (sample[i] != (hash[i] & 0xff)) {
                return false;
            }
        }
        return true;
    }

    public int crack(String algorithm, int sample[], ProgressBar progressBar) {

        int i = 0;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            long keySpace = (long) Math.pow(byteCharset.getLength(), length);
            for (i = 0; i < keySpace; ++i) {
                byte[] table = md.digest(plainGenerator.getVariation(i, length));
                if (fastCompare(sample, table)) {
                    found = true;
                }
                progressBar.setProgress((int) (((double) i) / (keySpace) * 100));
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("Bruteforcer: ", "No such algorithm");
        }
        return i;
    }
}
