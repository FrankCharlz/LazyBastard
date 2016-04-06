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
    public static List<UssdCode> List;

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


    public static List<UssdCode> getFromDB(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("db", 0);
        Set<String> set = prefs.getStringSet("set", new HashSet<String>());

        ArrayList<UssdCode> ussdCodes = new ArrayList<>(6);
        for (String str : set){
            ussdCodes.add(UssdCode.fromString(str));
        }
        return ussdCodes;
    }

    public static void addToDB(Context context, UssdCode ussdCode) {

        SharedPreferences prefs = context.getSharedPreferences("db", 0);
        Set<String> set = prefs.getStringSet("set", new HashSet<String>());

        Set<String> in_set = new HashSet<String>(set);
        in_set.add(String.valueOf(ussdCode.toString()));
        prefs.edit().putStringSet("set", in_set).apply();
    }

    public static void initList(Context context, String fname) {
        List = new ArrayList<>(10);
        try {
            FileInputStream fis = context.openFileInput(fname);
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<UssdCode> temp = (List<UssdCode>) ois.readObject();
            List.addAll(temp);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("mj", "An error in reading : "+e.getMessage());
        }
    }
}
