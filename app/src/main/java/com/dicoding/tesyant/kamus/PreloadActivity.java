package com.dicoding.tesyant.kamus;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dicoding.tesyant.kamus.helper.EnglishHelper;
import com.dicoding.tesyant.kamus.helper.IndonesiaHelper;
import com.dicoding.tesyant.kamus.model.EnglishModel;
import com.dicoding.tesyant.kamus.model.IndonesiaModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PreloadActivity extends Activity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preload);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        EnglishHelper englishHelper;
        IndonesiaHelper indonesiaHelper;
        AppPreference appPreference;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            englishHelper = new EnglishHelper(PreloadActivity.this);
            indonesiaHelper = new IndonesiaHelper(PreloadActivity.this);
            appPreference = new AppPreference(PreloadActivity.this);

        }

        @Override
        protected Void doInBackground(Void... params) {
            Boolean firstRun = appPreference.getFirstRun();
            Log.d("First run ", ":" + firstRun);

            if (firstRun) {
                ArrayList<EnglishModel> englishModels = preLoadRaw();
                ArrayList<IndonesiaModel> indonesiaModels = preLoadIndRaw();
                Log.d("size", " " +englishModels.size());
                progress = 30;
                publishProgress((int)progress);
                englishHelper.open();
                indonesiaHelper.open();

                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / englishModels.size();
                Double progressDiffInd = (progressMaxInsert - progress) / indonesiaModels.size();

                englishHelper.insertTransaction(englishModels);
                indonesiaHelper.insertTransaction(indonesiaModels);

                englishHelper.close();
                indonesiaHelper.close();
                appPreference.setFirstRun(false);

                publishProgress((int)maxprogress);
            }

            else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);
                        this.wait(2000);
                        publishProgress((int)maxprogress);
                    }
                }
                catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(PreloadActivity.this, "Data has been saved", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    public ArrayList<EnglishModel> preLoadRaw() {
        ArrayList<EnglishModel> englishModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources resources = getResources();
            InputStream raw_dict = resources.openRawResource(R.raw.english_indonesia);
            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");
                EnglishModel englishModel;
                englishModel = new EnglishModel(splitstr[0], splitstr[1]);
                englishModels.add(englishModel);
                count++;
            }
            while (line != null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return englishModels;
    }

    public ArrayList<IndonesiaModel> preLoadIndRaw() {
        ArrayList<IndonesiaModel> indonesiaModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources resources = getResources();
            InputStream raw_dict = resources.openRawResource(R.raw.indonesia_english);
            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");
                IndonesiaModel indonesiaModel;
                indonesiaModel = new IndonesiaModel(splitstr[0], splitstr[1]);
                indonesiaModels.add(indonesiaModel);
                count++;
            }
            while (line != null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return indonesiaModels;
    }
}
