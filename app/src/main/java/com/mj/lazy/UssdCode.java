package com.mj.lazy;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Created by Frank on 3/29/2016.
 *
 */
public class UssdCode implements Serializable {
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
        String SEPARATOR = "__";
        return name + SEPARATOR + code + SEPARATOR + frequency;
    }


    public static ArrayList<UssdCode> initList(Context context, String fname) {
        ArrayList<UssdCode> rList = new ArrayList<>(10);
        try {
            FileInputStream fis = context.openFileInput(fname);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<UssdCode> temp = (ArrayList<UssdCode>) ois.readObject();
            rList.addAll(temp);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("mj", "An error in reading : "+e.getMessage());
        }
        return rList;
    }
}
