package com.example.fyp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class PlayersActivity extends AppCompatActivity {

    TextView team1NameTv ,team2NameTv, team1PlayersTv ,team2PlayersTv;
    private String url = " https://api.cricapi.com/v1/match_squad?apikey=7d2dc5ae-9763-41fe-8f0d-00217c6a0d8f&offset=0&id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        //Actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Players Details");
        //Back button
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent= getIntent();
        String uniqueid = intent.getStringExtra("match_id");
        url = url + uniqueid;

        team1NameTv = findViewById(R.id.team1NameTv);
        team2NameTv = findViewById(R.id.team2NameTv);
        team1PlayersTv = findViewById(R.id.team1PlayersTv);
        team2PlayersTv = findViewById(R.id.team2PlayersTv);

        //loaddata funsction call
        loadData();

    }

    private void loadData() {
        //progress bar to be displayed while data is retrieving
        ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // dismiss dialog
                pd.dismiss();
                 /*json data is in response variable parameter of this function
                        it may cause exception so we will use try catch*/
                try {

                    JSONArray squadArray = new JSONObject(response).getJSONArray("squad");

                    JSONObject json0 = squadArray.getJSONObject(0);
                    JSONObject json1 = squadArray.getJSONObject(1);

                    //get name of team1 and team2
                    String team1Name = json0.getString("Name");
                    String team2Name = json1.getString("Name");

                    JSONArray team1Array = json0.getJSONArray("PLayers");
                    JSONArray team2Array = json1.getJSONArray("PLayers");

                    team1NameTv.setText(team1Name);
                    team2NameTv.setText(team2Name);

                    //get players name  of  team1
                    for (int i=0; i<team1Array.length(); i++)
                    {
                        String team1 = team1Array.getJSONObject(i).getString("name");
                        team1PlayersTv.append((i+1)+") "+ team1 + "\n");
                    }

                    //get players name  of  team2
                    for (int i=0; i<team2Array.length(); i++)
                    {
                        String team2 = team2Array.getJSONObject(i).getString("name");
                        team2PlayersTv.append((i+1)+") "+ team2 + "\n");
                    }

                }
                catch (Exception  e){
                    Toast.makeText(PlayersActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PlayersActivity.this, "Error : "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // enqueue the req
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();//goto prev activity


        return super.onSupportNavigateUp();
    }

}