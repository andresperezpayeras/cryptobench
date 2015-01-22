package org.sample.cryptobenchmark;


public class PlainGenerator {
    private Charset charset;

    public PlainGenerator(Charset charset) {
        this.charset = charset;
    }

    String getVariation(int i, int length) {
        int pos = 0;
        String result = "";
        while (pos < length) {
            result += charset.getCharAtModulo(i);
            ++pos;
            i /= charset.getLength();
        }
        return result;
    }

}
