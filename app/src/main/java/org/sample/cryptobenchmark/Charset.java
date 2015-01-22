package org.sample.cryptobenchmark;

public class Charset {
    private String charset;

    public Charset(String charset) {
        this.charset = charset;
    }

    public Character getCharAt(int i) {
        if (i < charset.length()) return charset.charAt(i);
        else return null;
    }

    public Character getCharAtModulo(int i) {
        return charset.charAt(i % charset.length());
    }

    public int getLength() {
        return charset.length();
    }
}
