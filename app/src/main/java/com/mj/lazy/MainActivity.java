package com.mj.lazy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity  {

    private static final String FILENAME = "ussd_data";
    private Button btnAdd;
    private RecyclerView recyclerView;
    private ListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UssdCode.initList(this, FILENAME);
        for (int i = 0; i<3; i++) UssdCode.List.add(new UssdCode("m-pesa", "*15"+i+"*00#", i));

        adapter = new ListAdapter(this, UssdCode.List);

        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.list_view);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);


        btnAdd = (Button) findViewById(R.id.button);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddUssdDialog(MainActivity.this, adapter).show();
            }
        });


    }



    @Override
    protected void onStop() {
        super.onStop();
        try {
            saveToFile(UssdCode.List);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("mj", e.getMessage() + "error when writing..");
        }
    }

    private void saveToFile(List<UssdCode> list) throws IOException {
        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_APPEND);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(list);
    }

}
