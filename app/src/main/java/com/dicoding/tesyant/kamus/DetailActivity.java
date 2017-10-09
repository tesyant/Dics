package com.dicoding.tesyant.kamus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends Activity {

    TextView detailWord, detailMeaning;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String VOCAB_ID = "";

        detailWord = (TextView)findViewById(R.id.dtl_word);
        detailMeaning = (TextView)findViewById(R.id.dtl_meaning);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            VOCAB_ID = (String) bundle.get("vocabId").toString();
            Log.e("Check", "Word ID : " + VOCAB_ID);
        }



    }

    private void SetText(String vocab, String meaning) {
        detailWord.setText(vocab);
        detailMeaning.setText(meaning);
    }

}
