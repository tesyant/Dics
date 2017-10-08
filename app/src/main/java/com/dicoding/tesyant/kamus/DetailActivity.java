package com.dicoding.tesyant.kamus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DetailActivity extends Activity {

    TextView detailWord, detailMeaning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String WORD_ID = "";

        detailWord = (TextView)findViewById(R.id.dtl_word);
        detailMeaning = (TextView)findViewById(R.id.dtl_meaning);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            WORD_ID = (String) bundle.get("wordId".toString());
            Log.e("Check", "Word ID : " + WORD_ID);
        }

    }

    private void SetText(String word, String meaning) {
        detailWord.setText(word);
        detailMeaning.setText(meaning);
    }

}
