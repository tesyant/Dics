package com.dicoding.tesyant.kamus;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
            englishHelper = new EnglishHelper(MainActivity.this);
            indonesiaHelper = new IndonesiaHelper(MainActivity.this);
            appPreference = new AppPreference(MainActivity.this);

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
            Toast.makeText(MainActivity.this, "Data has been saved", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_enin) {
            startActivity(new Intent(getApplicationContext(), EnglishActivity.class));
        }

        else if (id == R.id.nav_inen) {
            startActivity(new Intent(getApplicationContext(), IndonesiaActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
