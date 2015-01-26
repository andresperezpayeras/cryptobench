package org.sample.cryptobenchmark;

public class ByteCharset {
    private byte[] charset;

    public ByteCharset(byte[] charset) {
        this.charset = charset;
    }

    public byte getByteAt(int i) {
        if (i < charset.length) return charset[i];
        else return -1;
    }

    public byte getCharAtModulo(int i) {
        return charset[i % charset.length];
    }

    public int getLength() {
        return charset.length;
    }
}
