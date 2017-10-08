package com.dicoding.tesyant.kamus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dicoding.tesyant.kamus.adapter.EnglishAdapter;
import com.dicoding.tesyant.kamus.helper.EnglishHelper;

public class EnglishActivity extends Activity {

    RecyclerView recyclerView;
    EnglishAdapter englishAdapter;
    EnglishHelper englishHelper;

    Toolbar toolbar;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);

        editText = (EditText)findViewById(R.id.edt_search);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);

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

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int actionId, KeyEvent keyEvent) {
                String word = editText.getText().toString().trim();
                String result = englishHelper.getData(word);
                int result_size = result.length();
                final String[] list_vocab = new String[result_size];
                for (int i = 0; i<result_size; i++) {
                    list_vocab[i] = String.valueOf(result);
                    Toast.makeText(EnglishActivity.this, (list_vocab[i]), Toast.LENGTH_SHORT).show();
                    Log.e("check", "list " + list_vocab[i] + " ok");
                }



                return false;
            }
        });

    }


    //        String word = editText.getText().toString();
//        String result = englishHelper.getData(word);





//        ArrayList<EnglishModel> vocabs = new ArrayList<>();
//        int size = vocabs.size();
//        final String[] list_kata_id = new String[size];
//        for (int i=0; i<size; i++) {
//            list_kata_id[i] = String.valueOf(result);
//        }
//
//        EnglishAdapter customAdapter = new EnglishAdapter(vocabs, EnglishActivity.this, new CustomItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                String id = list_kata_id[position];
//                Intent intent = new Intent(EnglishActivity.this, DetailActivity.class);
//                intent.putExtra("wordId", id);
//                startActivity(intent);
//            }
//        });
//
//
//
//        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//
//        recyclerView.setLayoutManager(llm);
//        recyclerView.setAdapter(customAdapter);


//        ArrayList<EnglishModel> englishModels = englishHelper.getAllData();
//
//        englishAdapter = new EnglishAdapter(this, englishModels);
//        recyclerView.setAdapter(englishAdapter);
//
//englishHelper.searchQueryByName(editText.getText().toString());
//englishAdapter = new EnglishAdapter(editText.getText().toString());
//englishHelper.close();
//        englishAdapter.addItem(englishModels);
}
