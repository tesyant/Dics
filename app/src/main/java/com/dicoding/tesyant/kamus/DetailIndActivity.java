package com.dicoding.tesyant.kamus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dicoding.tesyant.kamus.helper.IndonesiaHelper;

public class DetailIndActivity extends Activity {

    TextView detailWord, detailMeaning;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ind);

        String VOCAB = "";

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
            VOCAB = (String) bundle.get("vocabId").toString();
            Log.e("Check", "Word ID : " + VOCAB);
        }

        IndonesiaHelper indonesiaHelper = new IndonesiaHelper(this);
        indonesiaHelper.open();

        String MEAN = indonesiaHelper.getMeaningData(VOCAB);
        Log.e("asdas", "hello : " + MEAN);
        SetText(VOCAB, MEAN);
    }

    private void SetText(String vocab, String meaning) {
        detailWord.setText(vocab);
        detailMeaning.setText(meaning);
    }
}