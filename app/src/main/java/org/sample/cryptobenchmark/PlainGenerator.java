package org.sample.cryptobenchmark;


public class PlainGenerator {
    private ByteCharset byteCharset;

    public PlainGenerator(ByteCharset byteCharset) {
        this.byteCharset = byteCharset;
    }

    byte[] getVariation(int i, int length) {
        int pos = 0;
        byte result[] = new byte[length];
        while (pos < length) {
            result[pos] = byteCharset.getCharAtModulo(i);
            ++pos;
            i /= byteCharset.getLength();
        }
        return result;
    }

}
