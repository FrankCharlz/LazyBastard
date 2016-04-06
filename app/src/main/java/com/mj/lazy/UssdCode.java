package com.mj.lazy;

/**
 * Created by Frank on 3/29/2016.
 *
 */
public class UssdCode implements Comparable<UssdCode> {
    private static final String SEPARATOR = "___";
    private String name;
    private String code;
    private int frequency = 0;

    public UssdCode(String name, String code, int frequency) {
        this.code = code;
        this.name = name;
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public void incrementFrequency() {
        this.frequency = (frequency + 1);
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return name + SEPARATOR + code + SEPARATOR + frequency;
    }


    public static UssdCode fromString(String str) {
        String[] parts = str.split(SEPARATOR);

        if (parts.length == 3) {
            int f = Integer.parseInt(parts[2]);
            return  new UssdCode(parts[0], parts[1], f);
        }
        return  null;
    }


    @Override
    public int compareTo(UssdCode other) {
        return other.getFrequency() - getFrequency();
    }
}
