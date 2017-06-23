package com.example.dell.mysqliteapplicationtest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 22/06/2017.
 */

public class BackGroundTask extends AsyncTask<Void,RandomUser,Void>{


     private ProgressBar progressBar;
     private RecyclerView recyclerView;
     private RandomUserAdapter randomUserAdapter;

    private List<RandomUser> listSuperHeroes = new ArrayList<>();

    private Context context;

    public BackGroundTask(RecyclerView recyclerView, ProgressBar progressBar, Context context) {
        this.recyclerView = recyclerView;
        this.progressBar = progressBar;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        randomUserAdapter = new RandomUserAdapter(listSuperHeroes,context);
        recyclerView.setAdapter(randomUserAdapter);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... params) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String Street,Last,Title,First,Email,Thumbnail,City,State,Postcode,Phone;
        Cursor cursor = dbHelper.readFromLocalDb(database);
        while (cursor.moveToNext()){
            Title = cursor.getString(cursor.getColumnIndex(DbContract.TITLE));
            First = cursor.getString(cursor.getColumnIndex(DbContract.FIRST));
            Last = cursor.getString(cursor.getColumnIndex(DbContract.LAST));
            Street = cursor.getString(cursor.getColumnIndex(DbContract.STREET));
            Email = cursor.getString(cursor.getColumnIndex(DbContract.EMAIL));
            Thumbnail = cursor.getString(cursor.getColumnIndex(DbContract.THUMBNAIL));
            City = cursor.getString(cursor.getColumnIndex(DbContract.CITY));
            State = cursor.getString(cursor.getColumnIndex(DbContract.STATE));
            Postcode = cursor.getString(cursor.getColumnIndex(DbContract.POSTCODE));
            Phone = cursor.getString(cursor.getColumnIndex(DbContract.PHONE));

            publishProgress(new RandomUser(City,Email,First,Last,Phone,Postcode,State,Street,Thumbnail,Title));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        cursor.close();
        dbHelper.close();
        return null;

    }

    @Override
    protected void onProgressUpdate(RandomUser... values) {
        listSuperHeroes.add(values[0]);
        randomUserAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        progressBar.setVisibility(View.GONE);
    }

}
