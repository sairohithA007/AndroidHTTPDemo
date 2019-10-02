package com.starter.code.httpdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Album> albums;
    private Gson gson;
    private RequestQueue queue;
    private ListView listView;
    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        queue = Volley.newRequestQueue(this);
        gson = new Gson();
    }


    // Triggered when GetData Button is pressed
    public void getData(View view){
        final String url = "https://jsonplaceholder.typicode.com/photos";
        this.getData(url);
    }


    public void getData(String url){
        // Preparing request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        albums = gson.fromJson(response.toString(), new TypeToken<List<Album>>() {}.getType());
                        displayOnScreen();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display error message
                        System.out.println("Error.Response "+ error);
                    }
                }
        );

        // adding it to the RequestQueue
        queue.add(getRequest);
    }

    public void displayOnScreen(){
        System.out.println(albums.get(0).getTitle());
        adapter= new CustomAdapter(albums, getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Album album = albums.get(position);

                Toast.makeText(getApplicationContext(), album.getTitle(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
