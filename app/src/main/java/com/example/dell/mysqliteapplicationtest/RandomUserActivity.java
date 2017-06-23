package com.example.dell.mysqliteapplicationtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import java.util.List;

/**
 * Created by dell on 22/06/2017.
 */

public class RandomUserActivity extends Activity{

    private List<RandomUser> listSuperHeroes;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RandomUserAdapter adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        new BackGroundTask(recyclerView,progressBar,this).execute();

    }

}
