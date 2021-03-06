package com.kupat.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;
import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.Style;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Runnable {


    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    Button mScan, mPrint, mDisc;
    ImageView mImage;
    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket = null;
    private EscPos escpos;
    BluetoothDevice mBluetoothDevice;

    static String defaultJson = "{\"hargaKupat1\":[18000,22500,20000],\"hargaKupatSet\":[14000,17500,15000],\"hargaTahuToge1\":[18000,22500,20000],\"hargaTahuTogeSet\":[14000,17500,15000],\"hargaKariAyam1\":[21000,26500,23000],\"hargaKariAyamSet\":[16000,20000,17000],\"hargaKariSapi1\":[21000,26500,23000],\"hargaKariSapiSet\":[16000,20000,17000],\"hargaTelur\":[4000,5000,4500],\"hargaKerupukM\":[1000,1250,2250],\"hargaKerupukA\":[500,625,600],\"hargaEmping\":[4000,5000,4500],\"hargaTahu\":[2500,3125,2250],\"hargaPeyek\":[10000,12500,11000],\"hargaDaging\":[8000,10000,7000],\"hargaDagingA\":[6000,7500,7000],\"hargaBumbu\":[7000,8750,7000],\"hargaSaroja\":[9000,11250,10000],\"hargaKentang\":[10000,12500,10000],\"hargaLontongP\":[6000,7500,7500],\"hargaSeblak\":[6000,7500,7500],\"hargaJeruk\":[10000,12500,12500]}";

    static int priceIndex = 0;
    int[] hargaKupat1   ;
    int[] hargaKupatSet ;
    int[] hargaTahuToge1   ;
    int[] hargaTahuTogeSet ;
    int[] hargaKariAyam1    ;
    int[] hargaKariAyamSet  ;
    int[] hargaKariSapi1    ;
    int[] hargaKariSapiSet  ;
    int[] hargaTelur    ;
    int[] hargaKerupukM ;
    int[] hargaKerupukA ;
    int[] hargaEmping   ;
    int[] hargaTahu     ;
    int[] hargaPeyek     ;
    int[] hargaDaging    ;
    int[] hargaDagingA   ;
    int[] hargaBumbu     ;
    int[] hargaSaroja    ;
    int[] hargaKentang   ;
    int[] hargaLontongP  ;
    int[] hargaSeblak    ;
    int[] hargaJeruk    ;

    static int nKupat = 0;
    static int nKupatSet = 0;
    static int nTahuToge = 0;
    static int nTahuTogeSet = 0;
    static int nTahu = 0;
    static int nBumbu = 0;

    static int nKariAyam = 0;
    static int nKariAyamSet = 0;
    static int nDagingA = 0;

    static int nKariSapi = 0;
    static int nKariSapiSet = 0;
    static int nDaging = 0;

    static int nTelur = 0;
    static int nKerupukM = 0;
    static int nKerupukA = 0;
    static int nEmping = 0;
    static int nPeyek = 0;
    static int nSeroja = 0;
    static int nKentang = 0;
    static int nLontongP = 0;
    static int nSeblak =  0;
    static int nJeruk = 0;

    static String pin_sementara = "";

    static int hargaTotal = 0;
    static int totalHargaKupat = 0;
    static int totalHargaKupatSet = 0;
    static int totalHargaTahuToge = 0;
    static int totalHargaTahuTogeSet = 0;
    static int totalHargaTahu = 0;
    static int totalHargaBumbu = 0;

    static int totalHargaKariAyam = 0;
    static int totalHargaKariAyamSet = 0;
    static int totalHargaDagingA = 0;

    static int totalHargaKariSapi = 0;
    static int totalHargaKariSapiSet = 0;
    static int totalHargaDaging = 0;

    static int totalHargaTelur = 0;
    static int totalHargaKerupukM = 0;
    static int totalHargaKerupukA = 0;
    static int totalHargaEmping = 0;
    static int totalHargaPeyek = 0;
    static int totalHargaSeroja = 0;
    static int totalHargaKentang = 0;
    static int totalHargaLontongP = 0;
    static int totalHargaSeblak = 0;
    static int totalHargaJeruk = 0;

    static AlertDialog.Builder builder;
    static AlertDialog.Builder builder0;

    static Switch gojekSwitch;
    static Switch grabSwitch;

    static Button btnPrint;
    static TextView kupat1total;
    static TextView kupatsettotal ;
    static TextView tahutoge1total;
    static TextView tahutogesettotal;
    static TextView tahutotal;
    static TextView bumbutotal;

    static TextView kariayam1total ;
    static TextView kariayamsettotal ;
    static TextView dagingatotal;

    static TextView karisapi1total ;
    static TextView karisapisettotal ;
    static TextView dagingtotal;

    static TextView telurtotal ;
    static TextView kerupukmerahtotal;
    static TextView kerupukacitotal;
    static TextView empingtotal;
    static TextView peyektotal;
    static TextView serojatotal;
    static TextView kentangtotal;
    static TextView lontongptotal;
    static TextView seblaktotal;
    static TextView jeruktotal;

    static TextView totalhargaTV;

    static EditText gojekpemesan;
    static EditText pemesan2;
    static EditText gojekpin;
    static EditText gojekantrian;

    static EditText pindialog;


    void calculatePrice(int priceIndex){
        totalHargaKupat = hargaKupat1[priceIndex] * nKupat;
        kupat1total.setText(nKupat + " | " + totalHargaKupat);

        totalHargaKupatSet = hargaKupatSet[priceIndex] * nKupatSet;
        kupatsettotal.setText(nKupatSet + " | " + totalHargaKupatSet);

        totalHargaTahuToge = hargaTahuToge1[priceIndex] * nTahuToge;
        tahutoge1total.setText(nTahuToge + " | " + totalHargaTahuToge);

        totalHargaTahuTogeSet = hargaTahuTogeSet[priceIndex] * nTahuTogeSet;
        tahutogesettotal.setText(nTahuTogeSet + " | " + totalHargaTahuTogeSet);

        totalHargaTahu = hargaTahu[priceIndex] * nTahu;
        tahutotal.setText(nTahu + " | " + totalHargaTahu);

        totalHargaBumbu = hargaBumbu[priceIndex] * nBumbu;
        bumbutotal.setText(nBumbu + " | " + totalHargaBumbu);

        totalHargaKariAyam = hargaKariAyam1[priceIndex] * nKariAyam;
        kariayam1total.setText(nKariAyam + " | " + totalHargaKariAyam);

        totalHargaKariAyamSet = hargaKariAyamSet[priceIndex] * nKariAyamSet;
        kariayamsettotal.setText(nKariAyamSet + " | " + totalHargaKariAyamSet);

        totalHargaDagingA = hargaDagingA[priceIndex] * nDagingA;
        dagingatotal.setText(nDagingA + " | " + totalHargaDagingA);

        totalHargaKariSapi = hargaKariSapi1[priceIndex] * nKariSapi;
        karisapi1total.setText(nKariSapi + " | " + totalHargaKariSapi);

        totalHargaKariSapiSet = hargaKariSapiSet[priceIndex] * nKariSapiSet;
        karisapisettotal.setText(nKariSapiSet + " | " + totalHargaKariSapiSet);

        totalHargaDaging = hargaDaging[priceIndex] * nDaging;
        dagingtotal.setText(nDaging + " | " + totalHargaDaging);

        totalHargaTelur = hargaTelur[priceIndex] * nTelur;
        telurtotal.setText(nTelur + " | " + totalHargaTelur);

        totalHargaKerupukM = hargaKerupukM[priceIndex] * nKerupukM;
        kerupukmerahtotal.setText(nKerupukM + " | " + totalHargaKerupukM);

        totalHargaKerupukA = hargaKerupukA[priceIndex] * nKerupukA;
        kerupukacitotal.setText(nKerupukA + " | " + totalHargaKerupukA);

        totalHargaEmping = hargaEmping[priceIndex] * nEmping;
        empingtotal.setText(nEmping + " | " + totalHargaEmping);

        totalHargaPeyek = hargaPeyek[priceIndex] * nPeyek;
        peyektotal.setText(nPeyek + " | " + totalHargaPeyek);

        totalHargaSeroja = hargaSaroja[priceIndex] * nSeroja;
        serojatotal.setText(nSeroja + " | " + totalHargaSeroja);

        totalHargaKentang = hargaKentang[priceIndex] * nKentang;
        kentangtotal.setText(nKentang + " | " + totalHargaKentang);

        totalHargaLontongP = hargaLontongP[priceIndex] * nLontongP;
        lontongptotal.setText(nLontongP + " | " + totalHargaLontongP);

        totalHargaSeblak = hargaSeblak[priceIndex] * nSeblak;
        seblaktotal.setText(nSeblak + " | " + totalHargaSeblak);

        totalHargaJeruk = hargaJeruk[priceIndex] * nJeruk;
        jeruktotal.setText(nJeruk + " | " + totalHargaJeruk);

        hargaTotal = totalHargaKupat + totalHargaKupatSet + totalHargaTahuToge + totalHargaTahuTogeSet + totalHargaTahu + totalHargaBumbu +
                totalHargaKariAyam + totalHargaKariAyamSet + totalHargaDagingA +
                totalHargaKariSapi + totalHargaKariSapiSet + totalHargaDaging +
                totalHargaTelur + totalHargaKerupukM + totalHargaKerupukA + totalHargaEmping +
                totalHargaPeyek + totalHargaSeroja + totalHargaKentang + totalHargaLontongP + totalHargaSeblak + totalHargaJeruk;
        totalhargaTV.setText("" + hargaTotal);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean showUpdateDialog = false;
        String textDialog = "";
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPref = getSharedPreferences("pricesPreferences",
                Context.MODE_PRIVATE);
        if (sharedPref.getString("created", "kosong").equals("OK")) {
            hargaKupat1 = new int[]{sharedPref.getInt("hargaKupatN", -1), sharedPref.getInt("hargaKupatGo", -1), sharedPref.getInt("hargaKupatGr", -1)};
            hargaKupatSet = new int[]{sharedPref.getInt("hargaKupatSetN", -1), sharedPref.getInt("hargaKupatSetGo", -1), sharedPref.getInt("hargaKupatSetGr", -1)};
            hargaTahuToge1 = new int[]{sharedPref.getInt("hargaTahuTogeN", -1), sharedPref.getInt("hargaTahuTogeGo", -1), sharedPref.getInt("hargaTahuTogeGr", -1)};
            hargaTahuTogeSet = new int[]{sharedPref.getInt("hargaTahuTogeSetN", -1), sharedPref.getInt("hargaTahuTogeSetGo", -1), sharedPref.getInt("hargaTahuTogeSetGr", -1)};
            hargaKariAyam1 = new int[]{sharedPref.getInt("hargaKariAyam1N", -1), sharedPref.getInt("hargaKariAyam1Go", -1), sharedPref.getInt("hargaKariAyam1Gr", -1)};
            hargaKariAyamSet = new int[]{sharedPref.getInt("hargaKariAyamSetN", -1), sharedPref.getInt("hargaKariAyamSetGo", -1), sharedPref.getInt("hargaKariAyamSetGr", -1)};
            hargaKariSapi1 = new int[]{sharedPref.getInt("hargaKariSapi1N", -1), sharedPref.getInt("hargaKariSapi1Go", -1), sharedPref.getInt("hargaKariSapi1Gr", -1)};
            hargaKariSapiSet = new int[]{sharedPref.getInt("hargaKariSapiSetN", -1), sharedPref.getInt("hargaKariSapiSetGo", -1), sharedPref.getInt("hargaKariSapiSetGr", -1)};
            hargaTelur = new int[]{sharedPref.getInt("hargaTelurN", -1), sharedPref.getInt("hargaTelurGo", -1), sharedPref.getInt("hargaTelurGr", -1)};
            hargaKerupukM = new int[]{sharedPref.getInt("hargaKerupukMN", -1), sharedPref.getInt("hargaKerupukMGo", -1), sharedPref.getInt("hargaKerupukMGr", -1)};
            hargaKerupukA = new int[]{sharedPref.getInt("hargaKerupukAN", -1), sharedPref.getInt("hargaKerupukAGo", -1), sharedPref.getInt("hargaKerupukAGr", -1)};
            hargaEmping = new int[]{sharedPref.getInt("hargaEmpingN", -1), sharedPref.getInt("hargaEmpingGo", -1), sharedPref.getInt("hargaEmpingGr", -1)};
            hargaTahu = new int[]{sharedPref.getInt("hargaTahuN", -1), sharedPref.getInt("hargaTahuGo", -1), sharedPref.getInt("hargaTahuGr", -1)};
            hargaPeyek = new int[]{sharedPref.getInt("hargaPeyekN", -1), sharedPref.getInt("hargaPeyekGo", -1), sharedPref.getInt("hargaPeyekGr", -1)};
            hargaDaging = new int[]{sharedPref.getInt("hargaDagingN", -1), sharedPref.getInt("hargaDagingGo", -1), sharedPref.getInt("hargaDagingGr", -1)};
            hargaDagingA = new int[]{sharedPref.getInt("hargaDagingAN", -1), sharedPref.getInt("hargaDagingAGo", -1), sharedPref.getInt("hargaDagingAGr", -1)};
            hargaBumbu = new int[]{sharedPref.getInt("hargaBumbuN", -1), sharedPref.getInt("hargaBumbuGo", -1), sharedPref.getInt("hargaBumbuGr", -1)};
            hargaSaroja = new int[]{sharedPref.getInt("hargaSarojaN", -1), sharedPref.getInt("hargaSarojaGo", -1), sharedPref.getInt("hargaSarojaGr", -1)};
            hargaKentang = new int[]{sharedPref.getInt("hargaKentangN", -1), sharedPref.getInt("hargaKentangGo", -1), sharedPref.getInt("hargaKentangGr", -1)};
            hargaLontongP = new int[]{sharedPref.getInt("hargaLontongPN", -1), sharedPref.getInt("hargaLontongPGo", -1), sharedPref.getInt("hargaLontongPGr", -1)};
            hargaSeblak = new int[]{sharedPref.getInt("hargaSeblakN", -1), sharedPref.getInt("hargaSeblakGo", -1), sharedPref.getInt("hargaSeblakGr", -1)};
            hargaJeruk = new int[]{sharedPref.getInt("hargaJerukN", -1), sharedPref.getInt("hargaJerukGo", -1), sharedPref.getInt("hargaJerukGr", -1)};

            System.out.println("XXXXXXXX: SUDAH ADA HARGA");
        }
        else if (sharedPref.getString("created", "kosong").equals("UPDATED")) {
            sharedPref = getSharedPreferences("pricesPreferences",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("created", "OK");
            editor.apply();
            showUpdateDialog = true;
            textDialog = "Berhasil update harga";
            System.out.println("XXXXXXXX: BERHASIL UPDATE HARGA");
            parseJson(sharedPref.getString("jsonSebelum", defaultJson));
        } else if (sharedPref.getString("created", "kosong").equals("NOTOK")){
            Toast.makeText(this.getApplicationContext(), "Gagal Update Harga\n Harga kembali ke kondisi sebelumnya", Toast.LENGTH_LONG);
            System.out.println("ON REOPEN: GAGAL UPDATE HARGA");
            parseJson(sharedPref.getString("jsonSebelum", defaultJson));
            showUpdateDialog = true;
            textDialog = "Gagal update harga, kembali ke kondisi sebelumnya";
            System.out.println("XXXXXXXX: GAGAL UPDATE HARGA");
            parseJson(sharedPref.getString("jsonSebelum", defaultJson));
        } else {
            System.out.println("XXXXXXXX: KONDISI AWAL");
            parseJson(sharedPref.getString("jsonSebelum", defaultJson));
        }

        if (showUpdateDialog){
            builder = new AlertDialog.Builder(this);
            builder.setMessage(textDialog)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .setNegativeButton("Cek Harga", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            clearContent(1);
                        }
                    });
            builder.show();
        }

        btnPrint = (Button) findViewById(R.id.print);
        totalhargaTV = (TextView) findViewById(R.id.totalharga);

        Button kupat1kurang = (Button) findViewById(R.id.kupat1kurang);
        Button kupat1tambah = (Button) findViewById(R.id.kupat1tambah);
        kupat1total = (TextView) findViewById(R.id.kupat1total) ;

        Button kupatsetkurang = (Button) findViewById(R.id.kupatsetkurang);
        Button kupatsettambah = (Button) findViewById(R.id.kupatsettambah);
        kupatsettotal = (TextView) findViewById(R.id.kupatsettotal);

        Button tahutoge1kurang = (Button) findViewById(R.id.tahutoge1kurang);
        Button tahutoge1tambah = (Button) findViewById(R.id.tahutoge1tambah);
        tahutoge1total = (TextView) findViewById(R.id.tahutoge1total) ;

        Button tahutogesetkurang = (Button) findViewById(R.id.tahutogesetkurang);
        Button tahutogesettambah = (Button) findViewById(R.id.tahutogesettambah);
        tahutogesettotal = (TextView) findViewById(R.id.tahutogesettotal);

        Button tahutambah = (Button) findViewById(R.id.tahutambah);
        Button tahukurang = (Button) findViewById(R.id.tahukurang);
        tahutotal = (TextView) findViewById(R.id.tahutotal);

        Button bumbutambah = (Button) findViewById(R.id.bumbutambah);
        Button bumbukurang = (Button) findViewById(R.id.bumbukurang);
        bumbutotal = (TextView) findViewById(R.id.bumbutotal);



        Button kariayam1tambah = (Button) findViewById(R.id.kariayam1tambah);
        Button kariayam1kurang = (Button) findViewById(R.id.kariayam1kurang);
        kariayam1total = (TextView) findViewById(R.id.kariayam1total);

        Button kariayamsettambah = (Button) findViewById(R.id.kariayamsettambah);
        Button kariayamsetkurang = (Button) findViewById(R.id.kariayamsetkurang);
        kariayamsettotal = (TextView) findViewById(R.id.kariayamsettotal);

        Button dagingatambah = (Button) findViewById(R.id.dagingatambah);
        Button dagingakurang = (Button) findViewById(R.id.dagingakurang);
        dagingatotal = (TextView) findViewById(R.id.dagingatotal);


        Button karisapi1tambah = (Button) findViewById(R.id.karisapi1tambah);
        Button karisapi1kurang = (Button) findViewById(R.id.karisapi1kurang);
        karisapi1total = (TextView) findViewById(R.id.karisapi1total);

        Button karisapisettambah = (Button) findViewById(R.id.karisapisettambah);
        Button karisapisetkurang = (Button) findViewById(R.id.karisapisetkurang);
        karisapisettotal = (TextView) findViewById(R.id.karisapisettotal);

        Button dagingtambah = (Button) findViewById(R.id.dagingtambah);
        Button dagingkurang = (Button) findViewById(R.id.dagingkurang);
        dagingtotal = (TextView) findViewById(R.id.dagingtotal);


        Button telurtambah = (Button) findViewById(R.id.telurtambah);
        Button telurkurang = (Button) findViewById(R.id.telurkurang);
        telurtotal = (TextView) findViewById(R.id.telurtotal);

        Button kerupukmerahtambah = (Button) findViewById(R.id.kerupukmerahtambah);
        Button kerupukmerahkurang = (Button) findViewById(R.id.kerupukmerahkurang);
        kerupukmerahtotal = (TextView) findViewById(R.id.kerupukmerahtotal);

        Button kerupukacitambah = (Button) findViewById(R.id.kerupukacitambah);
        Button kerupukacikurang = (Button) findViewById(R.id.kerupukacikurang);
        kerupukacitotal = (TextView) findViewById(R.id.kerupukacitotal);

        Button empingtambah = (Button) findViewById(R.id.empingtambah);
        Button empingkurang = (Button) findViewById(R.id.empingkurang);
        empingtotal = (TextView) findViewById(R.id.empingtotal);

        Button peyektambah = (Button) findViewById(R.id.rempeyektambah);
        Button peyekkurang = (Button) findViewById(R.id.rempeyekkurang);
        peyektotal = (TextView) findViewById(R.id.rempeyektotal);

        Button serojatambah = (Button) findViewById(R.id.serojatambah);
        Button serojakurang = (Button) findViewById(R.id.serojakurang);
        serojatotal = (TextView) findViewById(R.id.serojatotal);

        Button kentangtambah = (Button) findViewById(R.id.kentangtambah);
        Button kentangkurang = (Button) findViewById(R.id.kentangkurang);
        kentangtotal = (TextView) findViewById(R.id.kentangtotal);

        Button lontongptambah = (Button) findViewById(R.id.lontongptambah);
        Button lontongpkurang = (Button) findViewById(R.id.lontongpkurang);
        lontongptotal = (TextView) findViewById(R.id.lontongptotal);

        Button seblaktambah = (Button) findViewById(R.id.seblaktambah);
        Button seblakkurang = (Button) findViewById(R.id.seblakkurang);
        seblaktotal = (TextView) findViewById(R.id.seblaktotal);

        Button jeruktambah = (Button) findViewById(R.id.jeruktambah);
        Button jerukkurang = (Button) findViewById(R.id.jerukkurang);
        jeruktotal = (TextView) findViewById(R.id.jeruktotal);

        gojekpemesan = (EditText) findViewById(R.id.gojekpemesan);
        pemesan2 = (EditText) findViewById(R.id.pemesan2);
        gojekpin = (EditText) findViewById(R.id.gojekpin);
        gojekantrian = (EditText) findViewById(R.id.gojekantrian);


        builder = new AlertDialog.Builder(this);
        builder.setMessage("Print pengingat?")
                .setPositiveButton("PRINT", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            PrintSecondReceipt();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        clearContent(0);
                    }
                });


        //GOJEK HEADER
        final ConstraintLayout gojekDetail = (ConstraintLayout) findViewById(R.id.gojekDetail);
        gojekSwitch = (Switch) findViewById(R.id.gojekswitch);
        grabSwitch = (Switch) findViewById(R.id.grabswitch);

        this.clearContent(0);

        gojekSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    grabSwitch.setChecked(false);
                    gojekDetail.setVisibility(ConstraintLayout.VISIBLE);
                    priceIndex = 1;
                    calculatePrice(priceIndex);
                } else {
                    gojekDetail.setVisibility(ConstraintLayout.GONE);
                    priceIndex = 0;
                    calculatePrice(priceIndex);
                }
            }});


        grabSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    gojekSwitch.setChecked(false);
                    gojekDetail.setVisibility(ConstraintLayout.VISIBLE);
                    priceIndex = 2;
                    calculatePrice(priceIndex);
                } else {
                    gojekDetail.setVisibility(ConstraintLayout.GONE);
                    priceIndex = 0;
                    calculatePrice(priceIndex);
                }
            }});




        kupat1tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKupat += 1;
                calculatePrice(priceIndex);
            }
        });

        kupat1kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKupat -= 1;
                if (nKupat < 0){
                    nKupat = 0;
                }
                calculatePrice(priceIndex);
            }
        });


        kupatsettambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKupatSet += 1;
                calculatePrice(priceIndex);
            }
        });

        kupatsetkurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKupatSet -= 1;
                if (nKupatSet < 0){
                    nKupatSet = 0;
                }
                calculatePrice(priceIndex);
            }
        });


        tahutoge1tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nTahuToge += 1;
                calculatePrice(priceIndex);
            }
        });

        tahutoge1kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nTahuToge -= 1;
                if (nTahuToge < 0){
                    nTahuToge = 0;
                }
                calculatePrice(priceIndex);
            }
        });


        tahutogesettambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nTahuTogeSet += 1;
                calculatePrice(priceIndex);
            }
        });

        tahutogesetkurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nTahuTogeSet -= 1;
                if (nTahuTogeSet < 0){
                    nTahuTogeSet = 0;
                }
                calculatePrice(priceIndex);
            }
        });

        kariayam1tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKariAyam += 1;
                calculatePrice(priceIndex);
            }
        });

        kariayam1kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKariAyam -= 1;
                if (nKariAyam < 0){
                    nKariAyam= 0;
                }
                calculatePrice(priceIndex);
            }
        });

        kariayamsettambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKariAyamSet += 1;
                calculatePrice(priceIndex);
            }
        });

        kariayamsetkurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKariAyamSet -= 1;
                if (nKariAyamSet < 0){
                    nKariAyamSet= 0;
                }
                calculatePrice(priceIndex);
            }
        });

        dagingatambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nDagingA += 1;
                calculatePrice(priceIndex);
            }
        });

        dagingakurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nDagingA -= 1;
                if (nDagingA < 0){
                    nDagingA = 0;
                }
                calculatePrice(priceIndex);
            }
        });

        karisapi1tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKariSapi += 1;
                calculatePrice(priceIndex);
            }
        });

        karisapi1kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKariSapi -= 1;
                if (nKariSapi < 0){
                    nKariSapi= 0;
                }
                calculatePrice(priceIndex);
            }
        });

        karisapisettambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKariSapiSet += 1;
                calculatePrice(priceIndex);
            }
        });

        karisapisetkurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKariSapiSet -= 1;
                if (nKariSapiSet < 0){
                    nKariSapiSet= 0;
                }
                calculatePrice(priceIndex);
            }
        });

        telurtambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nTelur += 1;
                calculatePrice(priceIndex);
            }
        });

        telurkurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nTelur -= 1;
                if (nTelur < 0){
                    nTelur= 0;
                }
                calculatePrice(priceIndex);
            }
        });

        kerupukacitambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKerupukA += 1;
                calculatePrice(priceIndex);
            }
        });

        kerupukacikurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKerupukA -= 1;
                if (nKerupukA < 0){
                    nKerupukA = 0;
                }
                calculatePrice(priceIndex);
            }
        });


        kerupukmerahtambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKerupukM += 1;
                calculatePrice(priceIndex);
            }
        });

        kerupukmerahkurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKerupukM -= 1;
                if (nKerupukM < 0){
                    nKerupukM = 0;
                }
                calculatePrice(priceIndex);
            }
        });


        empingtambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nEmping += 1;
                calculatePrice(priceIndex);
            }
        });

        empingkurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nEmping -= 1;
                if (nEmping < 0){
                    nEmping= 0;
                }
                calculatePrice(priceIndex);
            }
        });


        tahutambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nTahu += 1;
                calculatePrice(priceIndex);
            }
        });

        tahukurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nTahu -= 1;
                if (nTahu < 0){
                    nTahu = 0;
                }
                calculatePrice(priceIndex);
            }
        });

        peyektambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nPeyek += 1;
                calculatePrice(priceIndex);
            }
        });

        peyekkurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nPeyek -= 1;
                if (nPeyek < 0){
                    nPeyek = 0;
                }
                calculatePrice(priceIndex);
            }
        });


        dagingtambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nDaging += 1;
                calculatePrice(priceIndex);
            }
        });

        dagingkurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nDaging -= 1;
                if (nDaging < 0){
                    nDaging = 0;
                }
                calculatePrice(priceIndex);
            }
        });


        serojatambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nSeroja += 1;
                calculatePrice(priceIndex);
            }
        });

        serojakurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nSeroja -= 1;
                if (nSeroja < 0){
                    nSeroja = 0;
                }
                calculatePrice(priceIndex);
            }
        });



        bumbutambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nBumbu += 1;
                calculatePrice(priceIndex);
            }
        });

        bumbukurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nBumbu -= 1;
                if (nBumbu < 0){
                    nBumbu = 0;
                }
                calculatePrice(priceIndex);
            }
        });


        kentangtambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKentang += 1;
                calculatePrice(priceIndex);
            }
        });

        kentangkurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nKentang -= 1;
                if (nKentang < 0){
                    nKentang = 0;
                }
                calculatePrice(priceIndex);
            }
        });

        lontongptambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nLontongP += 1;
                calculatePrice(priceIndex);
            }
        });

        lontongpkurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nLontongP -= 1;
                if (nLontongP < 0){
                    nLontongP = 0;
                }
                calculatePrice(priceIndex);
            }
        });

        seblaktambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nSeblak += 1;
                calculatePrice(priceIndex);
            }
        });

        seblakkurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nSeblak -= 1;
                if (nSeblak < 0){
                    nSeblak = 0;
                }
                calculatePrice(priceIndex);
            }
        });

        jeruktambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nJeruk += 1;
                calculatePrice(priceIndex);
            }
        });

        jerukkurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nJeruk -= 1;
                if (nJeruk < 0){
                    nJeruk = 0;
                }
                calculatePrice(priceIndex);
            }
        });

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hargaTotal != 0) {
                        dialogInputPIN();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
        setResult(RESULT_CANCELED);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        //finish();
        //System.exit(0);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_setting) {
            clearContent(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_ganti_harga) {
            updateHarga();
        } else if (id == R.id.nav_cek_harga) {
            clearContent(1);
        }
        else if (id == R.id.nav_tools) {
            attempDisconnect();
            ScanBluetooth();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (mResultCode == Activity.RESULT_OK) {
                    Bundle mExtra = mDataIntent.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");
                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                    mBluetoothDevice = mBluetoothAdapter
                            .getRemoteDevice(mDeviceAddress);
                    mBluetoothConnectProgressDialog = ProgressDialog.show(this,
                            "Connecting...", mBluetoothDevice.getName() + " : "
                                    + mBluetoothDevice.getAddress(), true, false);
                    Thread mBlutoothConnectThread = new Thread(this);
                    mBlutoothConnectThread.start();

                }
                break;

            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
                    ListPairedDevices();
                    Intent connectIntent = new Intent(MainActivity.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(MainActivity.this, "Message", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private void ListPairedDevices() {
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
            }
        }
    }

    public void ScanBluetooth(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            //Toast.makeText(MainActivity.this, "Message1", Toast.LENGTH_SHORT).show();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,
                        REQUEST_ENABLE_BT);
            } else {
                Intent connectIntent = new Intent(MainActivity.this,
                        DeviceListActivity.class);
                startActivityForResult(connectIntent,
                        REQUEST_CONNECT_DEVICE);
            }
        }
    }

    public void updateHarga(){
        Intent updateHargaIntent = new Intent(MainActivity.this,
                UpdateHargaActivity.class);
        startActivity(updateHargaIntent);
        finish();
    }


    public void attempDisconnect(){
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
    }

    public void run() {
        try {
            mBluetoothSocket = mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
            return;
        }
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }

    public void clearContent(int num){
        gojekSwitch.setChecked(false);
        priceIndex = 0;
        nKupat = num;
        nKupatSet = num;
        nTahuToge = num;
        nTahuTogeSet =num;
        nKariAyam = num;
        nKariAyamSet = num;
        nDagingA = num;
        nKariSapi = num;
        nKariSapiSet = num;
        nTelur = num;
        nKerupukM = num;
        nKerupukA = num;
        nEmping = num;
        nTahu = num;
        nPeyek = num;
        nDaging = num;
        nBumbu = num;
        nSeroja = num;
        nKentang = num;
        nLontongP = num;
        nSeblak = num;
        nJeruk = num;
        gojekpemesan.setText("");
        pemesan2.setText("");
        gojekpin.setText("");
        gojekantrian.setText("");
        pin_sementara = "";
        calculatePrice(priceIndex);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(MainActivity.this, "DeviceConnected", Toast.LENGTH_SHORT).show();
        }
    };

    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
    }

    public byte[] sel(int val) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putInt(val);
        buffer.flip();
        return buffer.array();
    }


    public void PrintSecondReceipt() throws InterruptedException {
        Thread t = new Thread() {
            public void run() {
                try {
                    OutputStream os = mBluetoothSocket.getOutputStream();
                    escpos = new EscPos(os);
                    Style title = new Style()
                            .setFontSize(Style.FontSize._3, Style.FontSize._3)
                            .setJustification(EscPosConst.Justification.Center);
                    escpos.feed(1);
                    title = new Style().setJustification(EscPosConst.Justification.Center).setFontSize(Style.FontSize._3,Style.FontSize._3);
                    escpos.writeLF(title, gojekantrian.getText().toString());
                    title = new Style().setJustification(EscPosConst.Justification.Center).setFontSize(Style.FontSize._1,Style.FontSize._1);
                    escpos.writeLF(title, gojekpemesan.getText().toString());

                    escpos.feed(1);
                    ReceiptBuilder(escpos, false);
                    escpos.writeLF(title, "--------------------");
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    escpos.writeLF(title, formattedDate);
                    escpos.feed(2);

                } catch (Exception e) {
                    Log.e("MainActivity", "Exe ", e);
                }
            }
        };
        t.start();
        t.join();
        clearContent(0);
    }

    public boolean PrintReceipt() throws InterruptedException {
        if (mBluetoothSocket == null){
            Toast.makeText(MainActivity.this, "Silakan hubungkan printer terlebih dahulu", Toast.LENGTH_SHORT).show();
            //attempDisconnect();
            //ScanBluetooth();
            return false;
        }
        else {
        Thread t = new Thread() {
            public void run() {
                try {
                    OutputStream os = mBluetoothSocket.getOutputStream();
                    escpos = new EscPos(os);
                    Style title = new Style()
                            .setFontSize(Style.FontSize._3, Style.FontSize._3)
                            .setJustification(EscPosConst.Justification.Center);
                    escpos.feed(1);
                    escpos.writeLF(title, "KUPAT TAHU");
                    escpos.writeLF(title, "LONTONG KARI");
                    title = new Style().setJustification(EscPosConst.Justification.Center);
                    escpos.writeLF(title,"CICENDO - 1967 | 085108253545");
                    escpos.writeLF(title, "--------------------");

                    if (gojekSwitch.isChecked()){
                        escpos.writeLF("A/N: " + gojekpemesan.getText().toString() + " | " + pemesan2.getText().toString());
                        escpos.write("No : " + gojekantrian.getText().toString() + " |      PIN: ");
                        //Style PIN = new Style().setColorMode(Style.ColorMode.WhiteOnBlack);
                        escpos.writeLF(pin_sementara);
                    }

                    else {
                        escpos.writeLF("A/N: -");
                        escpos.writeLF("No : -");
                    }

                    escpos.feed(1);
                    ReceiptBuilder(escpos, true);
                    escpos.writeLF(title, "--------------------");
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    escpos.writeLF(title, "TERIMA KASIH :)");
                    escpos.writeLF(title, formattedDate);
                    escpos.writeLF(title, "--------------------");
                    escpos.writeLF(title, "Semoga sehat selalu");
                    escpos.feed(2);


                } catch (Exception e) {
                    Log.e("MainActivity", "Exe ", e);
                }
            }
        };
        t.start();
        t.join();

        /*
        if (gojekSwitch.isChecked()){
            builder.show();
        } else {
            clearContent(0);
        }
        */
        return true;
    }}

    public void ReceiptBuilder(EscPos escpos, boolean first) throws IOException {
        if (hargaTotal != 0){
            Style rightJust = new Style().setJustification(EscPosConst.Justification.Right);
            if (nKupat != 0){escpos.writeLF(nKupat             + " x Kupat Tahu @" + hargaKupat1[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaKupat);}
            if (nKupatSet != 0){escpos.writeLF(nKupatSet       + " x Kpt Tahu ½ @" + hargaKupatSet[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaKupatSet);}
            if (nTahuToge != 0){escpos.writeLF(nTahuToge       + " x Tahu Toge  @" + hargaTahuToge1[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaTahuToge);}
            if (nTahuTogeSet != 0){escpos.writeLF(nTahuTogeSet + " x Tahu Toge½ @" + hargaTahuTogeSet[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaTahuTogeSet);}
            if (nTahu != 0){escpos.writeLF(nTahu               + " x Tahu       @" + hargaTahu[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaTahu);}
            if (nBumbu != 0){escpos.writeLF(nBumbu             + " x Bumbu+++   @" + hargaBumbu[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaBumbu);}
            if (nKariAyam != 0){escpos.writeLF(nKariAyam       + " x Kari Ayam  @" + hargaKariAyam1[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaKariAyam);}
            if (nKariAyamSet != 0){escpos.writeLF(nKariAyamSet + " x Kari Ayam½ @" + hargaKariAyamSet[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaKariAyamSet);}
            if (nDagingA != 0){escpos.writeLF(nDagingA         + " x DagingA++  @" + hargaDagingA[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaDagingA);}

            if (nKariSapi != 0){escpos.writeLF(nKariSapi       + " x Kari Sapi  @" + hargaKariSapi1[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaKariSapi);}
            if (nKariSapiSet != 0){escpos.writeLF(nKariSapiSet + " x Kari Sapi½ @" + hargaKariSapiSet[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaKariSapiSet);}
            if (nDaging != 0){escpos.writeLF(nDaging           + " x Daging++   @" + hargaDaging[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaDaging);}

            if (nTelur != 0){escpos.writeLF(nTelur       + " x Telur      @" + hargaTelur[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaTelur);}
            if (nKerupukM != 0){escpos.writeLF(nKerupukM + " x Kerupuk M  @" + hargaKerupukM[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaKerupukM);}
            if (nKerupukA != 0){escpos.writeLF(nKerupukA + " x Kerupuk A  @" + hargaKerupukA[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaKerupukA);}
            if (nEmping != 0){escpos.writeLF(nEmping     + " x Emping     @" + hargaEmping[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaEmping);}
            if (nPeyek != 0){escpos.writeLF(nPeyek       + " x Rempeyek   @" + hargaPeyek[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaPeyek);}
            if (nSeroja != 0){escpos.writeLF(nSeroja     + " x Seroja     @" + hargaSaroja[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaSeroja);}
            if (nKentang != 0){escpos.writeLF(nKentang   + " x Mustopa    @" + hargaKentang[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaKentang);}
            if (nLontongP != 0){escpos.writeLF(nLontongP + " x Lontong++  @" + hargaLontongP[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaLontongP);}
            if (nSeblak != 0){escpos.writeLF(nSeblak +     " x Seblak     @" + hargaSeblak[priceIndex]); if(first) escpos.writeLF(rightJust, "" + totalHargaSeblak);}
            if (nJeruk != 0) {escpos.writeLF(nJeruk +      " x Jeruk      @" + hargaJeruk[priceIndex]);  if(first) escpos.writeLF(rightJust, "" + totalHargaJeruk);}

            if(first) escpos.writeLF(rightJust, "Total: " + hargaTotal);
        }
    }

    public void dialogClearReceipt(){
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Hapus data sebelumnya?")
                .setPositiveButton("Ya, hapus", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        clearContent(0);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.show();
    }

    public void dialogInputPIN(){
        if ((gojekSwitch.isChecked() || grabSwitch.isChecked()) && !(mBluetoothSocket == null)) {
            builder0 = new AlertDialog.Builder(this);
            LinearLayout ll_alert_layout = new LinearLayout(this);
            ll_alert_layout.setOrientation(LinearLayout.VERTICAL);
            ll_alert_layout.setPadding(8,8,8,8);
            final EditText ed_input = new EditText(this);
            ed_input.setInputType(InputType.TYPE_CLASS_NUMBER);
            ed_input.setHint("MASUKAN PIN GOJEK/GRAB");
            ll_alert_layout.addView(ed_input);
            builder0.setView(ll_alert_layout);
            builder0.setMessage("TOTAL: " + totalhargaTV.getText().toString())
                    .setPositiveButton("PRINT", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                pin_sementara = ed_input.getText().toString();
                                if (!(PrintReceipt())){
                                    PrintReceipt();
                                }
                                dialogClearReceipt();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //clearContent(0);
                        }
                    });
            builder0.show();
        }
        else {
            try {
            if (!(PrintReceipt())){PrintReceipt();};
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
        }

    }

    public void parseJson(String text){
        SharedPreferences sharedPref = getSharedPreferences("pricesPreferences",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        try {
            String jsonString = text;
            JSONObject JSo = new JSONObject(jsonString);
            JSONArray temp;
            temp = JSo.getJSONArray("hargaKupat1");
            hargaKupat1 = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaKupatN", temp.getInt(0));
            editor.putInt("hargaKupatGo", temp.getInt(1));
            editor.putInt("hargaKupatGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaKupatSet");
            hargaKupatSet = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaKupatSetN", temp.getInt(0));
            editor.putInt("hargaKupatSetGo", temp.getInt(1));
            editor.putInt("hargaKupatSetGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaTahuToge1");
            hargaTahuToge1 = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaTahuTogeN", temp.getInt(0));
            editor.putInt("hargaTahuTogeGo", temp.getInt(1));
            editor.putInt("hargaTahuTogeGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaTahuTogeSet");
            hargaTahuTogeSet = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaTahuTogeSetN", temp.getInt(0));
            editor.putInt("hargaTahuTogeSetGo", temp.getInt(1));
            editor.putInt("hargaTahuTogeSetGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaKariAyam1");
            hargaKariAyam1 = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaKariAyam1N", temp.getInt(0));
            editor.putInt("hargaKariAyam1Go", temp.getInt(1));
            editor.putInt("hargaKariAyam1Gr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaKariAyamSet");
            hargaKariAyamSet = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaKariAyamSetN", temp.getInt(0));
            editor.putInt("hargaKariAyamSetGo", temp.getInt(1));
            editor.putInt("hargaKariAyamSetGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaKariSapi1");
            hargaKariSapi1 = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaKariSapi1N", temp.getInt(0));
            editor.putInt("hargaKariSapi1Go", temp.getInt(1));
            editor.putInt("hargaKariSapi1Gr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaKariSapiSet");
            hargaKariSapiSet = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaKariSapiSetN", temp.getInt(0));
            editor.putInt("hargaKariSapiSetGo", temp.getInt(1));
            editor.putInt("hargaKariSapiSetGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaTelur");
            hargaTelur = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaTelurN", temp.getInt(0));
            editor.putInt("hargaTelurGo", temp.getInt(1));
            editor.putInt("hargaTelurGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaKerupukM");
            hargaKerupukM = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaKerupukMN", temp.getInt(0));
            editor.putInt("hargaKerupukMGo", temp.getInt(1));
            editor.putInt("hargaKerupukMGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaKerupukA");
            hargaKerupukA = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaKerupukAN", temp.getInt(0));
            editor.putInt("hargaKerupukAGo", temp.getInt(1));
            editor.putInt("hargaKerupukAGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaEmping");
            hargaEmping = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaEmpingN", temp.getInt(0));
            editor.putInt("hargaEmpingGo", temp.getInt(1));
            editor.putInt("hargaEmpingGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaTahu");
            hargaTahu = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaTahuN", temp.getInt(0));
            editor.putInt("hargaTahuGo", temp.getInt(1));
            editor.putInt("hargaTahuGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaPeyek");
            hargaPeyek = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaPeyekN", temp.getInt(0));
            editor.putInt("hargaPeyekGo", temp.getInt(1));
            editor.putInt("hargaPeyekGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaDaging");
            hargaDaging = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaDagingN", temp.getInt(0));
            editor.putInt("hargaDagingGo", temp.getInt(1));
            editor.putInt("hargaDagingGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaDagingA");
            hargaDagingA = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaDagingAN", temp.getInt(0));
            editor.putInt("hargaDagingAGo", temp.getInt(1));
            editor.putInt("hargaDagingAGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaBumbu");
            hargaBumbu = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaBumbuN", temp.getInt(0));
            editor.putInt("hargaBumbuGo", temp.getInt(1));
            editor.putInt("hargaBumbuGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaSaroja");
            hargaSaroja = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaSarojaN", temp.getInt(0));
            editor.putInt("hargaSarojaGo", temp.getInt(1));
            editor.putInt("hargaSarojaGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaKentang");
            hargaKentang = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaKentangN", temp.getInt(0));
            editor.putInt("hargaKentangGo", temp.getInt(1));
            editor.putInt("hargaKentangGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaLontongP");
            hargaLontongP = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaLontongPN", temp.getInt(0));
            editor.putInt("hargaLontongPGo", temp.getInt(1));
            editor.putInt("hargaLontongPGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaSeblak");
            hargaSeblak = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaSeblakN", temp.getInt(0));
            editor.putInt("hargaSeblakGo", temp.getInt(1));
            editor.putInt("hargaSeblakGr", temp.getInt(2));

            temp = JSo.getJSONArray("hargaJeruk");
            hargaJeruk = new int[]{temp.getInt(0), temp.getInt(1), temp.getInt(2)};
            editor.putInt("hargaJerukN", temp.getInt(0));
            editor.putInt("hargaJerukGo", temp.getInt(1));
            editor.putInt("hargaJerukGr", temp.getInt(2));

            editor.putString("jsonSebelum", jsonString);
            editor.putString("created", "OK");

            editor.apply();
        } catch (JSONException e){
            System.out.println("PARSING ERRRPORRR");
            Toast.makeText(this.getApplicationContext(), "WRONG JSON!! REVERT", Toast.LENGTH_LONG);
            editor.putString("created", "NOTOK");
            this.parseJson(sharedPref.getString("jsonSebelum", defaultJson));
            System.out.println(e);
            editor.apply();
        }
    }
}
