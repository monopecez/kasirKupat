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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;


public class UpdateHargaActivity extends MainActivity {

    static EditText jsonInput;
    static Button jsonButtonOk;
    static Button jsonButtonCancel;

    @Override
    protected void onCreate(Bundle mSavedInstanceState) {
        super.onCreate(mSavedInstanceState);
        setContentView(R.layout.update_harga);
        jsonInput = (EditText) findViewById(R.id.hargaBaruET);
        jsonButtonOk = (Button) findViewById(R.id.hargaBaruButton);
        jsonButtonCancel = (Button) findViewById(R.id.hargaBaruButtonCancel);

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

    }

}