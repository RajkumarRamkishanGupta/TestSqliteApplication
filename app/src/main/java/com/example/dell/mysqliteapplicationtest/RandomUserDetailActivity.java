package com.example.dell.mysqliteapplicationtest;

/**
 * Created by dell on 22/06/2017.
 */
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;


/**
 * Created by dell on 21/06/2017.
 */

public class RandomUserDetailActivity extends AppCompatActivity implements Config {

    TextView textName,textPoneNumber,textLocation,textEmail;
    ImageView imgPic;
    String Pic, Email, Location, Phone, Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomuser_detail);
        textName = (TextView) findViewById(R.id.textName);
        textEmail = (TextView) findViewById(R.id.textEmail);
        imgPic = (ImageView) findViewById(R.id.imgPic);
        textPoneNumber = (TextView) findViewById(R.id.textPoneNumber);
        textLocation = (TextView) findViewById(R.id.textLocation);

        try {
            Email = getIntent().getStringExtra("email");
            Pic = getIntent().getStringExtra("pic");
            Location = getIntent().getStringExtra("location");
            Phone = getIntent().getStringExtra("phone");
            Name = getIntent().getStringExtra("name");
        } catch (Exception e) {
            e.printStackTrace();
        }

        textName.setText(Name);
        textPoneNumber.setText(Phone);
        textEmail.setText(Email);
        textLocation.setText(Location);


        Glide.with(getApplicationContext()).load(Pic).asBitmap().placeholder(R.drawable.img_marico_logo).centerCrop().into(new BitmapImageViewTarget(imgPic) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imgPic.setImageDrawable(circularBitmapDrawable);
            }
        });
    }
}
