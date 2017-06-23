package com.example.dell.mysqliteapplicationtest;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity implements Config{
    public  static boolean first = false;
    private String server_url ="https://randomuser.me/api?results=50";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StoreSqliteData();
    }

    private void StoreSqliteData() {
        if (isNetworkAvailable()) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        //mTextView.setText(response);
                        DbHelper dbHelper = new DbHelper(SplashActivity.this);
                        SQLiteDatabase database = dbHelper.getWritableDatabase();
                        JSONArray users = null;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            users = jsonObject.getJSONArray("results");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < users.length(); i++) {
                            try {
                                JSONObject jsons = users.getJSONObject(i);
                                String email = (jsons.isNull(Email) || jsons.getString(Email).equals("")) ? "" : jsons.getString(Email);
                                String phone = (jsons.isNull(Phone) || jsons.getString(Phone).equals("")) ? "" : jsons.getString(Phone);
                                JSONObject jsonObjec = jsons.getJSONObject("name");
                                String title = (jsonObjec.isNull(Title) || jsonObjec.getString(Title).equals("")) ? "" : jsonObjec.getString(Title);
                                String first = (jsonObjec.isNull(First) || jsonObjec.getString(First).equals("")) ? "" : jsonObjec.getString(First);
                                String last = (jsonObjec.isNull(Last) || jsonObjec.getString(Last).equals("")) ? "" : jsonObjec.getString(Last);
                                JSONObject jsonObjectLocation = jsons.getJSONObject("location");
                                String street = (jsonObjectLocation.isNull(Street) || jsonObjectLocation.getString(Street).equals("")) ? "" : jsonObjectLocation.getString(Street);
                                String city = (jsonObjectLocation.isNull(City) || jsonObjectLocation.getString(City).equals("")) ? "" : jsonObjectLocation.getString(City);
                                String state = (jsonObjectLocation.isNull(State) || jsonObjectLocation.getString(State).equals("")) ? "" : jsonObjectLocation.getString(State);
                                String postcode = (jsonObjectLocation.isNull(Postcode) || jsonObjectLocation.getString(Postcode).equals("")) ? "" : jsonObjectLocation.getString(Postcode);
                                JSONObject jsonObjectPicture = jsons.getJSONObject("picture");
                                String thumbnail = (jsonObjectPicture.isNull(Thumbnail) || jsonObjectPicture.getString(Thumbnail).equals("")) ? "" : jsonObjectPicture.getString(Thumbnail);

                                //listSuperHeroes.add(new RandomUser(city, email, first, last, phone, postcode, state, street, thumbnail, title));
                                dbHelper.SaveToLocalDB(title,first,last,email,street,thumbnail,city,state,phone,postcode,database);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //    listSuperHeroes.add(superHero);
                        }
                        dbHelper.close();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when get error
                        ////Snackbar.make(mCLayout,"Error...",Snackbar.LENGTH_LONG).show();
                    }
                }
        );

        // Add StringRequest to the RequestQueue
        MySingleton.getInstance(SplashActivity.this).addToRequestQueue(stringRequest);
            new CountDownTimer(4000,1000) {

                /** This method will be invoked on finishing or expiring the timer */
                @Override
                public void onFinish() {
                    Intent intent = new Intent(SplashActivity.this,RandomUserActivity.class);
                    startActivity(intent);
                    first = true;
                    finish();
                }

                /** This method will be invoked in every 1000 milli seconds until
                 * this timer is expired.Because we specified 1000 as tick time
                 * while creating this CountDownTimer
                 */
                @Override
                public void onTick(long millisUntilFinished) {

                }
            }.start();


        } else {
            Toast.makeText(this, "Check your internet connections", Toast.LENGTH_LONG).show();
            if (first){
                        Intent intent = new Intent(SplashActivity.this,RandomUserActivity.class);
                        startActivity(intent);
                        finish();
            }
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
