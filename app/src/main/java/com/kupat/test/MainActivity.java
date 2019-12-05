package com.kupat.test;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static int priceIndex = 0;
    static int[] hargaKupat1   =   {15000, 18000};
    static int[] hargaKupatSet =   {10000, 12000};
    static int[] hargaKari1    =   {17000, 22000};
    static int[] hargaKariSet  =   {12000, 15000};
    static int[] hargaTelur    =   {3500, 5000};
    static int[] hargaKerupukM =   {2000, 2000};
    static int[] hargaKerupukA =   {500, 625};
    static int[] hargaEmping   =   {3500, 4000};

    static int nKupat = 0;
    static int nKupatSet = 0;
    static int nKariAyam = 0;
    static int nKariAyamSet = 0;
    static int nKariSapi = 0;
    static int nKariSapiSet = 0;
    static int nTelur = 0;
    static int nKerupukM = 0;
    static int nKerupukA = 0;
    static int nEmping = 0;

    static int hargaTotal = 0;
    static int totalHargaKupat = 0;
    static int totalHargaKupatSet = 0;
    static int totalHargaKariAyam = 0;
    static int totalHargaKariAyamSet = 0;
    static int totalHargaKariSapi = 0;
    static int totalHargaKariSapiSet = 0;
    static int totalHargaTelur = 0;
    static int totalHargaKerupukM = 0;
    static int totalHargaKerupukA = 0;
    static int totalHargaEmping = 0;

    static Button btnPrint;
    static TextView kupat1total;
    static TextView kupatsettotal ;
    static TextView kariayam1total ;
    static TextView kariayamsettotal ;
    static TextView karisapi1total ;
    static TextView karisapisettotal ;
    static TextView telurtotal ;
    static TextView kerupukmerahtotal;
    static TextView kerupukacitotal;
    static TextView empingtotal;


    static void calculatePrice(int priceIndex){
        totalHargaKupat = hargaKupat1[priceIndex] * nKupat;
        kupat1total.setText(nKupat + " | " + totalHargaKupat);
        totalHargaKupatSet = hargaKupatSet[priceIndex] * nKupatSet;
        kupatsettotal.setText(nKupatSet + " | " + totalHargaKupatSet);
        totalHargaKariAyam = hargaKari1[priceIndex] * nKariAyam;
        kariayam1total.setText(nKariAyam + " | " + totalHargaKariAyam);
        totalHargaKariAyamSet = hargaKariSet[priceIndex] * nKariAyamSet;
        kariayamsettotal.setText(nKariAyamSet + " | " + totalHargaKariAyamSet);
        totalHargaKariSapi = hargaKari1[priceIndex] * nKariSapi;
        karisapi1total.setText(nKariSapi + " | " + totalHargaKariSapi);
        totalHargaKariSapiSet = hargaKariSet[priceIndex] * nKariSapiSet;
        karisapisettotal.setText(nKariSapiSet + " | " + totalHargaKariSapiSet);
        totalHargaTelur = hargaTelur[priceIndex] * nTelur;
        telurtotal.setText(nTelur + " | " + totalHargaTelur);
        totalHargaKerupukM = hargaKerupukM[priceIndex] * nKerupukM;
        kerupukmerahtotal.setText(nKerupukM + " | " + totalHargaKerupukM);
        totalHargaKerupukA = hargaKerupukA[priceIndex] * nKerupukA;
        kerupukacitotal.setText(nKerupukA + " | " + totalHargaKerupukA);
        totalHargaEmping = hargaEmping[priceIndex] * nEmping;
        empingtotal.setText(nEmping + " | " + totalHargaEmping);

        hargaTotal = totalHargaKupat + totalHargaKupatSet + totalHargaKariAyam + totalHargaKariAyamSet +
                totalHargaKariSapi + totalHargaKariSapiSet + totalHargaTelur + totalHargaKerupukM + totalHargaKerupukA + totalHargaEmping;
        btnPrint.setText("" + hargaTotal);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        btnPrint = (Button) findViewById(R.id.print);
        Button kupat1kurang = (Button) findViewById(R.id.kupat1kurang);
        Button kupat1tambah = (Button) findViewById(R.id.kupat1tambah);
        kupat1total = (TextView) findViewById(R.id.kupat1total) ;
        Button kupatsetkurang = (Button) findViewById(R.id.kupatsetkurang);
        Button kupatsettambah = (Button) findViewById(R.id.kupatsettambah);
        kupatsettotal = (TextView) findViewById(R.id.kupatsettotal);
        Button kariayam1tambah = (Button) findViewById(R.id.kariayam1tambah);
        Button kariayam1kurang = (Button) findViewById(R.id.kariayam1kurang);
        kariayam1total = (TextView) findViewById(R.id.kariayam1total);
        Button karisapi1tambah = (Button) findViewById(R.id.karisapi1tambah);
        Button karisapi1kurang = (Button) findViewById(R.id.karisapi1kurang);
        karisapi1total = (TextView) findViewById(R.id.karisapi1total);
        Button kariayamsettambah = (Button) findViewById(R.id.kariayamsettambah);
        Button kariayamsetkurang = (Button) findViewById(R.id.kariayamsetkurang);
        kariayamsettotal = (TextView) findViewById(R.id.kariayamsettotal);
        Button karisapisettambah = (Button) findViewById(R.id.karisapisettambah);
        Button karisapisetkurang = (Button) findViewById(R.id.karisapisetkurang);
        karisapisettotal = (TextView) findViewById(R.id.karisapisettotal);
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


        //GOJEK HEADER
        final ConstraintLayout gojekDetail = (ConstraintLayout) findViewById(R.id.gojekDetail);
        Switch gojekSwitch = (Switch) findViewById(R.id.gojekswitch);
        gojekSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    gojekDetail.setVisibility(ConstraintLayout.VISIBLE);
                    priceIndex = 1;
                    calculatePrice(priceIndex);
                } else {
                    gojekDetail.setVisibility(ConstraintLayout.GONE);
                    priceIndex = 0;
                    calculatePrice(priceIndex);
                }
        }});
        Log.i("Harga", Integer.toString(hargaKupat1[0]));


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




        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                


            }
        });


    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            nKupat = 0;
            nKupatSet = 0;
            nKariAyam = 0;
            nKariAyamSet = 0;
            nKariSapi = 0;
            nKariSapiSet = 0;
            nTelur = 0;
            nKerupukM = 0;
            nKerupukA = 0;
            nEmping = 0;
            calculatePrice(priceIndex);


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
            // Handle the camera action
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
