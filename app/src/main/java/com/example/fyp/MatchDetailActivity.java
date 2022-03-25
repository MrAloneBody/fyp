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

import org.json.JSONObject;

import java.net.HttpURLConnection;



public class MatchDetailActivity extends AppCompatActivity {


    TextView mTeam1Tv, mTeam2Tv, mMatchStatusTv, mScoreTv, mDescriptionTv, mDateTv;

    // we will get unique id of the match from on click(intent)


    private String url= "https://api.cricapi.com/v1/match_info?apikey=7d2dc5ae-9763-41fe-8f0d-00217c6a0d8f&offset=0&id=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        //Actionbar

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Match Details");

        //Back button

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //get data from intent (Onclick from previous activity)
        Intent intent = getIntent();
        String id= intent.getStringExtra("match_id");
        String date = intent.getStringExtra("date");

        url = url + id;

        mTeam1Tv = findViewById(R.id.team1tv);
        mTeam2Tv = findViewById(R.id.team2tv);
        mMatchStatusTv = findViewById(R.id.matchstatustv);
        mScoreTv = findViewById(R.id.scoreTv);
        mDescriptionTv = findViewById(R.id.descriptionTv);
        mDateTv = findViewById(R.id.datetv);

        mDateTv.setText(date);

        //get set data
        loadData();

    }

    private void loadData() {
        //progress bar to be displayed while data is retrieving
        ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //this method will becalled when the response from the server is recieved, dismiss the dialog first


                        pd.dismiss();


                        /*json data is in response variable parameter of this function
                        it may cause exception so we will use try catch*/
                        try {

                            // DAta from Api
                            JSONObject jsonObject = new JSONObject(response);
                            // these values will be received whether match is started or not
                            String team1 = jsonObject.getString("0");
                            String team2 = jsonObject.getString("1");
                            String matchStatus = jsonObject.getString("status");

                            if (matchStatus.equals("success")){
                                matchStatus = "Match Started";
                            }
                            else {
                                matchStatus = "Match not Started";
                            }
                            //set this data
                            mTeam1Tv.setText(team1);
                            mTeam2Tv.setText(team2);
                            mMatchStatusTv.setText(matchStatus);

                            try {
                                //these values will be received only if the match is started
                                // so we are enclosing this in a separate try catch
                                String score = jsonObject.getString("score");
                                String description = jsonObject.getString("description");
                                mScoreTv.setText(score);
                                mDescriptionTv.setText(description);


                            }
                            catch (Exception e){
                                Toast.makeText(MatchDetailActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (Exception e){
                            Toast.makeText(MatchDetailActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //if anything gets wrong, get the show error message
                Toast.makeText(MatchDetailActivity.this,"Error "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //enqueue the req
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();//goto prev activity


        return super.onSupportNavigateUp();
    }
}