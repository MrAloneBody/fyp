package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;


    //for API
    private String url= "";

    private RecyclerView.Adapter mAdapter;
    private List<Model> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recyclerview
        mRecyclerView = findViewById(R.id.recyclerview);
        //set its properties
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        modelList = new ArrayList<>();

        // Function to get show data from website
        loadUrlData();

    }

    private void loadUrlData() {

        //progress bar to be displayed while data is retrieving
        ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();


        // Requesting the data , since we are requesting so we'll use getters

//        StringRequest stringRequest = new StringRequest(DownloadManager.Request.Method.Get, url, new Response.Listener<String>() );

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //this method will be called when response from the server is recieved , so dismiss dialog first
                pd.dismiss();

                /*Json data is in response variable parameter of this function
                    it may cause exception so we'll try to catch
                 */

                try {
                    //get json object from response and get json
                    JSONArray jsonArray = new JSONObject(response).getJSONArray("matches");

                    //continue getting and setting data while array is completed
                    for (int i=0; i<jsonArray.length();i++){

                        try {
                            // get data
                            String uniqueid= jsonArray.getJSONObject(i).getString("unique_id");
                            String team1= jsonArray.getJSONObject(i).getString("team1");
                            String team2= jsonArray.getJSONObject(i).getString("team2");
                            String matchtype= jsonArray.getJSONObject(i).getString("type");
                            String matchstatus= jsonArray.getJSONObject(i).getString("matchstarted");
                            if (matchstatus.equals("true")){
                                matchstatus="Match Started";
                            }
                            else
                            {
                                matchstatus="Match not Started";
                            }
                            String datetimeGMT = jsonArray.getJSONObject(i).getString("datetimeGMT");
                            //convert date format
                            SimpleDateFormat format1= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.SSS'Z'");
                            format1.setTimeZone(TimeZone.getTimeZone(datetimeGMT));
                            Date date= format1.parse(datetimeGMT);
                            //convert to dd/MM/yyyy
                            SimpleDateFormat format2= new SimpleDateFormat("dd/MM/yyyy");
                            format2.setTimeZone(TimeZone.getTimeZone("GMT"));
                            //formated datetime
                            String datetime= format2.format(date);
                            //set date
                            Model model= new Model(uniqueid,team1,team2, matchtype, matchstatus, datetime);
                            modelList.add(model);
                        }
                        catch (Exception e){
                            Toast.makeText(MainActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    //adapter to be set to recycler  view
                    mAdapter = new MyAdaptor(modelList, getApplicationContext());
                    mRecyclerView.setAdapter(mAdapter);


                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //if there is any error (failed to get response from server, get and show error message)
                Toast.makeText(MainActivity.this, "Error :"+error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        //enqueue the request
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}