package com.kupat.test;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;


public class UpdateHargaActivity extends MainActivity {

    static EditText jsonInput;
    static Button jsonButtonOk;
    static Button jsonButtonCancel;
    static Button jsonButtonRequest;
    static TextView jsonTVResponse;

    @Override
    protected void onCreate(Bundle mSavedInstanceState) {
        super.onCreate(mSavedInstanceState);
        setContentView(R.layout.update_harga);
        jsonInput = (EditText) findViewById(R.id.hargaBaruET);
        jsonButtonOk = (Button) findViewById(R.id.hargaBaruButton);
        jsonButtonCancel = (Button) findViewById(R.id.hargaBaruButtonCancel);
        jsonButtonRequest = (Button) findViewById(R.id.hargaBaruButtonRequest);
        jsonButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("YANG DIINPUT : " + jsonInput.getText().toString());
                String jsonString = jsonInput.getText().toString();
                if(jsonString.length()!=0){
                    try {
                        JSONObject JSo = new JSONObject(jsonString);
                        parseJson(jsonString);
                        SharedPreferences sharedPref = getSharedPreferences("pricesPreferences",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        if (sharedPref.getString("created", "").equals("OK")) {
                            editor.putString("created", "UPDATED");
                        }
                        editor.apply();


                        finish();
                    } catch (JSONException e){
                        Context context = getApplicationContext();
                        CharSequence text = "INVALID JSON";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        System.out.println(e);
                    };
                    }
            }
        });

        jsonButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        jsonButtonCancel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                jsonInput.setText(defaultJson);
                return true;
            }
        });

        jsonButtonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final TextView textView = (TextView) findViewById(R.id.hargaBaruResponse);
// ...

// Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="https://bookshelf-170600.appspot.com/";

// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                textView.setText("Response is: "+ response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("That didn't work!");
                    }
                });

// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

    }

}