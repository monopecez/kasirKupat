package com.kupat.test;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
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


public class UpdateHargaActivity extends Activity {

    static EditText jsonInput;
    static Button jsonButton;

    @Override
    protected void onCreate(Bundle mSavedInstanceState) {
        super.onCreate(mSavedInstanceState);
        setContentView(R.layout.update_harga);
        jsonInput = (EditText) findViewById(R.id.hargaBaruET);
        jsonButton = (Button) findViewById(R.id.hargaBaruButton);
        jsonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    System.out.println("YANG DIINPUT : " + jsonInput.getText().toString());
                    String jsonString = jsonInput.getText().toString();
                    JSONObject JSo = new JSONObject(jsonString);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("hargaBaru", jsonString);
                    startActivity(intent);

                } catch (JSONException e){
                    Context context = getApplicationContext();
                    CharSequence text = "INVALID JSON";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    System.out.println(e);
                };
            }
        });

    }

}