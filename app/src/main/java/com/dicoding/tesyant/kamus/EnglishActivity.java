package com.dicoding.tesyant.kamus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dicoding.tesyant.kamus.adapter.CustomItemClickListener;
import com.dicoding.tesyant.kamus.adapter.EnglishAdapter;
import com.dicoding.tesyant.kamus.helper.EnglishHelper;

import java.util.ArrayList;

public class EnglishActivity extends Activity implements View.OnClickListener{

    RecyclerView recyclerView;
    EnglishAdapter englishAdapter;
    EnglishHelper englishHelper;

    Toolbar toolbar;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);

        editText = (EditText)findViewById(R.id.edt_search);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        button = (Button)findViewById(R.id.btn_search);
        button.setOnClickListener(this);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        englishHelper = new EnglishHelper(this);
        englishHelper.open();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        String word = editText.getText().toString().trim();
        ArrayList<String> result = new ArrayList<>();
        result = englishHelper.getData(word);

        final String[] results = new String[result.size()];

        if (result.size()>0) {
            for (int i = 0; i<result.size(); i++) {
                results[i] = result.get(i);
                Log.e("check", "query " + i + " is " + results[i]);
            }
        }

        EnglishAdapter customAdapter = new EnglishAdapter(getApplicationContext(), result, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String id = results[position];
                Intent intent = new Intent(EnglishActivity.this, DetailActivity.class);
                intent.putExtra("vocabId", id);
                startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(customAdapter);
    }
}