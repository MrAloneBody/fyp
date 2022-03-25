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

public class MatchSummaryActivity extends AppCompatActivity {

    String url="";
    TextView fieldT1TitleTv,fieldT1DetailTv,fieldT2TitleTv,fieldT2DetailTv,
            bowlT1TitleTv,bowlT1DetailTv,bowlT2TitleTv,bowlT2DetailTv,
            batT1TitleTv,batT1DetailTv,batT2TitleTv,batT2DetailTv,
            otherResultsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_summary);


        //Actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Players Details");
        //Back button
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        Intent intent= getIntent();
        String uniqueId = intent.getStringExtra("match_id");
        url = url + uniqueId;

        fieldT1TitleTv=findViewById(R.id.fieldT1TitleTv);
        fieldT1DetailTv=findViewById(R.id.fieldT1DetailTv);
        fieldT2TitleTv=findViewById(R.id.fieldT2TitleTv);
        fieldT2DetailTv=findViewById(R.id.fieldT2DetailTv);

        bowlT1TitleTv=findViewById(R.id.bowlT1TitleTv);
        bowlT1DetailTv=findViewById(R.id.bowlT1DetailTv);
        bowlT2TitleTv=findViewById(R.id.bowlT2TitleTv);
        bowlT2DetailTv=findViewById(R.id.bowlT2DetailTv);

        batT1TitleTv=findViewById(R.id.batT1TitleTv);
        batT1DetailTv=findViewById(R.id.batT1DetailTv);
        batT2TitleTv=findViewById(R.id.batT2TitleTv);
        batT2DetailTv=findViewById(R.id.batT2DetailTv);

        otherResultsTv=findViewById(R.id.otherResultsTv);
        //Load Data
        loadData();

    }

    private void loadData() {

        //progress bar to be displayed while data is retrieving
        ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();


        StringRequest stringRequest= new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //dismiss dialog
                pd.dismiss();
                /* json data is in response variablle  parameter of this function
                it may cause exception so we eill try to catch it
                */

                try {




                }
                catch (Exception e){
                    Toast.makeText(MatchSummaryActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MatchSummaryActivity.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    //enqueue req

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }




    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();//goto prev activity


        return super.onSupportNavigateUp();
    }
}