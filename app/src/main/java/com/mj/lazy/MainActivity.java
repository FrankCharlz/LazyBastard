package com.mj.lazy;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    /***
     *
     *
     04-06 16:22:50.304 4306-4306/? E/mj: read : imei___*#06#___3
     04-06 16:22:50.304 4306-4306/? E/mj: read : salio___*102#___2
     04-06 16:22:50.304 4306-4306/? E/mj: read : airtel yatosha___*149*94#___2
     04-06 16:22:50.305 4306-4306/? E/mj: read : m-pesa___*150*00#___1
     04-06 16:22:50.305 4306-4306/? E/mj: read : voda salio bando___*149*60#___0
     04-06 16:22:50.312 4306-4306/? E/mj: read : university bando___*149*42#___0
     *
     */

    private static final String FILENAME = "ussd_data";
    private Button btnAdd;
    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private ArrayList<UssdCode> ussdCodes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ussdCodes = new ArrayList<>(10);
        readFromFile();

        Collections.sort(ussdCodes);
        adapter = new ListAdapter(this, ussdCodes);
        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    private void addBofywad() {
        new AddUssdDialog(MainActivity.this,
                new Dismissable() {
                    @Override
                    public void addCode(UssdCode code) {
                        ussdCodes.add(code);
                        adapter.notifyDataSetChanged();
                    }

                }).show();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_new : addBofywad(); break;
            case R.id.menu_add_std : addStandard(); break;
            default: break;

        }
        return false;

    }

    private void addStandard() {
        String default_codes[] = new String[] {
                "imei___*#06#___0",
                "salio___*102#___3",
                "airtel yatosha___*149*94#___2",
                "m-pesa___*150*00#___1",
                "voda salio bando___*149*60#___1",
                "university bando___*149*42#___0"
        };
        for (String code : default_codes) {
            ussdCodes.add(UssdCode.fromString(code));
        }
        Collections.sort(ussdCodes);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onStop() {
        super.onStop();
        try {
            saveToFile(ussdCodes);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("mj", e.getMessage() + "error when writing..");
        }
    }

    private void saveToFile(List<UssdCode> list) throws IOException {

        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fos));
        for (UssdCode code : list) {
            br.write(code.toString());
            br.newLine();
            Log.e("mj", "wrote : "+code.toString());
        }
        br.flush();
        br.close();
        fos.close();
    }

    private void readFromFile() {
        //reading from file;
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader rd = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = rd.readLine()) != null) {
                if (line.length() < 3) continue;
                ussdCodes.add(UssdCode.fromString(line));
                Log.e("mj", "read : " + line);
            }

            rd.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("mj", "An error in reading : " + e.getMessage());
            Log.e("mj", "An error in reading details : "+e.getLocalizedMessage() );
        }

    }


}
