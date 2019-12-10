package com.kupat.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;
import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.Style;

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
    private BluetoothSocket mBluetoothSocket;
    private EscPos escpos;
    BluetoothDevice mBluetoothDevice;


    static int priceIndex = 0;
    static int[] hargaKupat1   =   {17000, 21250};
    static int[] hargaKupatSet =   {13000, 16250};
    static int[] hargaKari1    =   {20000, 25000};
    static int[] hargaKariSet  =   {15000, 18750};
    static int[] hargaTelur    =   {4000, 5000};
    static int[] hargaKerupukM =   {2000, 2500};
    static int[] hargaKerupukA =   {500, 625};
    static int[] hargaEmping   =   {4000, 5000};
    static int[] hargaTahu     =    {2000, 2500};
    static int[] hargaPeyek      =   {10000, 12500};

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
    static int nTahu = 0;
    static int nPeyek = 0;


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
    static int totalHargaTahu = 0;
    static int totalHargaPeyek = 0;

    static Switch gojekSwitch;

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
    static TextView tahutotal;
    static TextView peyektotal;


    static EditText gojekpemesan;
    static EditText gojekpin;
    static EditText gojekantrian;


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

        totalHargaTahu = hargaTahu[priceIndex] * nTahu;
        tahutotal.setText(nTahu + " | " + totalHargaTahu);

        totalHargaPeyek = hargaPeyek[priceIndex] * nPeyek;
        peyektotal.setText(nPeyek + " | " + totalHargaPeyek);


        hargaTotal = totalHargaKupat + totalHargaKupatSet + totalHargaKariAyam + totalHargaKariAyamSet +
                totalHargaKariSapi + totalHargaKariSapiSet + totalHargaTelur + totalHargaKerupukM + totalHargaKerupukA + totalHargaEmping+
        totalHargaTahu + totalHargaPeyek;
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
        Button tahutambah = (Button) findViewById(R.id.tahutambah);
        Button tahukurang = (Button) findViewById(R.id.tahukurang);
        tahutotal = (TextView) findViewById(R.id.tahutotal);
        Button peyektambah = (Button) findViewById(R.id.rempeyektambah);
        Button peyekkurang = (Button) findViewById(R.id.rempeyekkurang);
        peyektotal = (TextView) findViewById(R.id.rempeyektotal);

        gojekpemesan = (EditText) findViewById(R.id.gojekpemesan);
        gojekpin = (EditText) findViewById(R.id.gojekpin);
        gojekantrian = (EditText) findViewById(R.id.gojekantrian);


        //GOJEK HEADER
        final ConstraintLayout gojekDetail = (ConstraintLayout) findViewById(R.id.gojekDetail);
        gojekSwitch = (Switch) findViewById(R.id.gojekswitch);
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




        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hargaTotal != 0) {PrintReceipt();}
            }
        });


    };

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
            nTahu = 0;
            nPeyek = 0;
            gojekpemesan.setText("");
            gojekpin.setText("");
            gojekantrian.setText("");
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

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {
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
            Toast.makeText(MainActivity.this, "Message1", Toast.LENGTH_SHORT).show();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,
                        REQUEST_ENABLE_BT);
            } else {
                ListPairedDevices();
                Intent connectIntent = new Intent(MainActivity.this,
                        DeviceListActivity.class);
                startActivityForResult(connectIntent,
                        REQUEST_CONNECT_DEVICE);
            }
        }
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

    public void PrintReceipt(){
        if (mBluetoothSocket == null){
            Toast.makeText(MainActivity.this, "Silakan hubungkan printer terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
        Thread t = new Thread() {
            public void run() {
                try {
                    OutputStream os = mBluetoothSocket.getOutputStream();
                    escpos = new EscPos(os);
                    Style title = new Style()
                            .setFontSize(Style.FontSize._3, Style.FontSize._3)
                            .setJustification(EscPosConst.Justification.Center);
                    escpos.feed(2);
                    escpos.writeLF(title, "KUPAT TAHU");
                    escpos.writeLF(title, "LONTONG KARI");
                    title = new Style().setJustification(EscPosConst.Justification.Center);
                    escpos.writeLF(title,"CICENDO - 1967 | 085108253545");
                    escpos.writeLF(title, "--------------------");

                    if (gojekSwitch.isChecked()){
                        escpos.writeLF("A/N: " + gojekpemesan.getText().toString());
                        escpos.write("No : " + gojekantrian.getText().toString() + " | PIN: ");
                        //Style PIN = new Style().setColorMode(Style.ColorMode.WhiteOnBlack);
                        escpos.writeLF(gojekpin.getText().toString());}

                    else {
                        escpos.writeLF("A/N: -");
                        escpos.writeLF("No : -");
                    }

                    escpos.feed(1);
                    ReceiptBuilder(escpos);
                    escpos.writeLF(title, "--------------------");
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    escpos.writeLF(title, "TERIMA KASIH :)");
                    escpos.writeLF(title, formattedDate);
                    escpos.writeLF(title, "--------------------");
                    escpos.feed(2);


                } catch (Exception e) {
                    Log.e("MainActivity", "Exe ", e);
                }
            }
        };
        t.start();
    }}


    public void ReceiptBuilder(EscPos escpos) throws IOException {
        if (hargaTotal != 0){
            Style rightJust = new Style().setJustification(EscPosConst.Justification.Right);
            if (nKupat != 0){escpos.writeLF(nKupat +  " x Kupat Tahu @" + hargaKupat1[priceIndex]); escpos.writeLF(rightJust, "" + totalHargaKupat);}
            if (nKupatSet != 0){escpos.writeLF(nKupatSet + " x Kpt Tahu ½ @" + hargaKupatSet[priceIndex]); escpos.writeLF(rightJust, "" + totalHargaKupatSet);}
            if (nKariAyam != 0){escpos.writeLF(nKariAyam + " x Kari Ayam  @" + hargaKari1[priceIndex]); escpos.writeLF(rightJust, "" + totalHargaKariAyam);}
            if (nKariAyamSet != 0){escpos.writeLF(nKariAyamSet + " x Kari Ayam½ @" + hargaKariSet[priceIndex]); escpos.writeLF(rightJust, "" + totalHargaKariAyamSet);}
            if (nKariSapi != 0){escpos.writeLF(nKariSapi + " x Kari Sapi  @" + hargaKari1[priceIndex]); escpos.writeLF(rightJust, "" + totalHargaKariSapi);}
            if (nKariSapiSet != 0){escpos.writeLF(nKariSapiSet + " x Kari Sapi½ @" + hargaKariSet[priceIndex]); escpos.writeLF(rightJust, "" + totalHargaKariSapiSet);}
            if (nTelur != 0){escpos.writeLF(nTelur + " x Telur      @" + hargaTelur[priceIndex]); escpos.writeLF(rightJust, "" + totalHargaTelur);}
            if (nKerupukM != 0){escpos.writeLF(nKerupukM + " x Kerupuk M  @" + hargaKerupukM[priceIndex]); escpos.writeLF(rightJust, "" + totalHargaKerupukM);}
            if (nKerupukA != 0){escpos.writeLF(nKerupukA + " x Kerupuk A  @" + hargaKerupukA[priceIndex]); escpos.writeLF(rightJust, "" + totalHargaKerupukA);}
            if (nEmping != 0){escpos.writeLF(nEmping + " x Emping    @" + hargaEmping[priceIndex]); escpos.writeLF(rightJust, "" + totalHargaEmping);}
            if (nTahu != 0){escpos.writeLF(nTahu + " x Tahu      @" + hargaTahu[priceIndex]); escpos.writeLF(rightJust, "" + totalHargaTahu);}
            if (nPeyek != 0){escpos.writeLF(nPeyek + " x Rempeyek  @" + hargaPeyek[priceIndex]); escpos.writeLF(rightJust, "" + totalHargaPeyek);}

            escpos.writeLF(rightJust, "Total: " + hargaTotal);







        }


    }
}
