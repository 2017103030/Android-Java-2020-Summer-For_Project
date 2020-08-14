package org.techtown.creative_total_construction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.crypto.Mac;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

import static app.akexorcist.bluetotohspp.library.BluetoothState.REQUEST_ENABLE_BT;

public class bluetooth_wizards extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    ArrayList<String> device_MAC = new ArrayList<>();
    ArrayList<String> device_names = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
        InitializeData();
    }
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver);
    }



    public void InitializeData() {
        Button btn1 = findViewById(R.id.button_1);
        Button btn2 = findViewById(R.id.button_2);
        Button btn3 = findViewById(R.id.button_3);
        Button btn4 = findViewById(R.id.button_4);
        Button btn5 = findViewById(R.id.button_5);
        Button btn6 = findViewById(R.id.button_6);
        Button btn7 = findViewById(R.id.button_7);
        Button btn8 = findViewById(R.id.button_8);
        Button btn9 = findViewById(R.id.button_9);
        Button btn10 = findViewById(R.id.button_10);
        Button btn11 = findViewById(R.id.button_11);
        Button btn12 = findViewById(R.id.button_12);
        Button btn13 = findViewById(R.id.button_13);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        ArrayList<String> device_names = new ArrayList<String>();
        ArrayList<String> device_MAC = new ArrayList<>();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                device_names.add(deviceName);
                device_MAC.add(deviceHardwareAddress);
            }
        }
        int SIZE = device_names.size();
        if (SIZE == 0) {
            btn1.setText("");
            btn2.setText("");
            btn3.setText("");
            btn4.setText("");
            btn5.setText("");
            btn6.setText("");
            btn7.setText("");
            btn8.setText("");
            btn9.setText("");
            btn10.setText("");
            btn11.setText("");
            btn12.setText("");
            btn13.setText("");
            btn1.setBackgroundColor(Color.WHITE);
            btn2.setBackgroundColor(Color.WHITE);
            btn3.setBackgroundColor(Color.WHITE);
            btn4.setBackgroundColor(Color.WHITE);
            btn5.setBackgroundColor(Color.WHITE);
            btn6.setBackgroundColor(Color.WHITE);
            btn7.setBackgroundColor(Color.WHITE);
            btn8.setBackgroundColor(Color.WHITE);
            btn9.setBackgroundColor(Color.WHITE);
            btn10.setBackgroundColor(Color.WHITE);
            btn11.setBackgroundColor(Color.WHITE);
            btn12.setBackgroundColor(Color.WHITE);
            btn13.setBackgroundColor(Color.WHITE);
        } else if (SIZE == 1) {
            btn1.setText(device_names.get(0));
            btn2.setText("");
            btn3.setText("");
            btn4.setText("");
            btn5.setText("");
            btn6.setText("");
            btn7.setText("");
            btn8.setText("");
            btn9.setText("");
            btn10.setText("");
            btn11.setText("");
            btn12.setText("");
            btn13.setText("");
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.WHITE);
            btn3.setBackgroundColor(Color.WHITE);
            btn4.setBackgroundColor(Color.WHITE);
            btn5.setBackgroundColor(Color.WHITE);
            btn6.setBackgroundColor(Color.WHITE);
            btn7.setBackgroundColor(Color.WHITE);
            btn8.setBackgroundColor(Color.WHITE);
            btn9.setBackgroundColor(Color.WHITE);
            btn10.setBackgroundColor(Color.WHITE);
            btn11.setBackgroundColor(Color.WHITE);
            btn12.setBackgroundColor(Color.WHITE);
            btn13.setBackgroundColor(Color.WHITE);
        } else if (SIZE == 2) {
            btn1.setText(device_names.get(0));
            btn2.setText(device_names.get(1));
            btn3.setText("");
            btn4.setText("");
            btn5.setText("");
            btn6.setText("");
            btn7.setText("");
            btn8.setText("");
            btn9.setText("");
            btn10.setText("");
            btn11.setText("");
            btn12.setText("");
            btn13.setText("");
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.BLACK);
            btn3.setBackgroundColor(Color.WHITE);
            btn4.setBackgroundColor(Color.WHITE);
            btn5.setBackgroundColor(Color.WHITE);
            btn6.setBackgroundColor(Color.WHITE);
            btn7.setBackgroundColor(Color.WHITE);
            btn8.setBackgroundColor(Color.WHITE);
            btn9.setBackgroundColor(Color.WHITE);
            btn10.setBackgroundColor(Color.WHITE);
            btn11.setBackgroundColor(Color.WHITE);
            btn12.setBackgroundColor(Color.WHITE);
            btn13.setBackgroundColor(Color.WHITE);
        } else if (SIZE == 3) {
            btn1.setText(device_names.get(0));
            btn2.setText(device_names.get(1));
            btn3.setText(device_names.get(2));
            btn4.setText("");
            btn5.setText("");
            btn6.setText("");
            btn7.setText("");
            btn8.setText("");
            btn9.setText("");
            btn10.setText("");
            btn11.setText("");
            btn12.setText("");
            btn13.setText("");
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.BLACK);
            btn3.setBackgroundColor(Color.BLACK);
            btn4.setBackgroundColor(Color.WHITE);
            btn5.setBackgroundColor(Color.WHITE);
            btn6.setBackgroundColor(Color.WHITE);
            btn7.setBackgroundColor(Color.WHITE);
            btn8.setBackgroundColor(Color.WHITE);
            btn9.setBackgroundColor(Color.WHITE);
            btn10.setBackgroundColor(Color.WHITE);
            btn11.setBackgroundColor(Color.WHITE);
            btn12.setBackgroundColor(Color.WHITE);
            btn13.setBackgroundColor(Color.WHITE);
        } else if (SIZE == 4) {
            btn1.setText(device_names.get(0));
            btn2.setText(device_names.get(1));
            btn3.setText(device_names.get(2));
            btn4.setText(device_names.get(3));
            btn5.setText("");
            btn6.setText("");
            btn7.setText("");
            btn8.setText("");
            btn9.setText("");
            btn10.setText("");
            btn11.setText("");
            btn12.setText("");
            btn13.setText("");
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.BLACK);
            btn3.setBackgroundColor(Color.BLACK);
            btn4.setBackgroundColor(Color.BLACK);
            btn5.setBackgroundColor(Color.WHITE);
            btn6.setBackgroundColor(Color.WHITE);
            btn7.setBackgroundColor(Color.WHITE);
            btn8.setBackgroundColor(Color.WHITE);
            btn9.setBackgroundColor(Color.WHITE);
            btn10.setBackgroundColor(Color.WHITE);
            btn11.setBackgroundColor(Color.WHITE);
            btn12.setBackgroundColor(Color.WHITE);
            btn13.setBackgroundColor(Color.WHITE);
        } else if (SIZE == 5) {
            btn1.setText(device_names.get(0));
            btn2.setText(device_names.get(1));
            btn3.setText(device_names.get(2));
            btn4.setText(device_names.get(3));
            btn5.setText(device_names.get(4));
            btn6.setText("");
            btn7.setText("");
            btn8.setText("");
            btn9.setText("");
            btn10.setText("");
            btn11.setText("");
            btn12.setText("");
            btn13.setText("");
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.BLACK);
            btn3.setBackgroundColor(Color.BLACK);
            btn4.setBackgroundColor(Color.BLACK);
            btn5.setBackgroundColor(Color.BLACK);
            btn6.setBackgroundColor(Color.WHITE);
            btn7.setBackgroundColor(Color.WHITE);
            btn8.setBackgroundColor(Color.WHITE);
            btn9.setBackgroundColor(Color.WHITE);
            btn10.setBackgroundColor(Color.WHITE);
            btn11.setBackgroundColor(Color.WHITE);
            btn12.setBackgroundColor(Color.WHITE);
            btn13.setBackgroundColor(Color.WHITE);
        } else if (SIZE == 6) {
            btn1.setText(device_names.get(0));
            btn2.setText(device_names.get(1));
            btn3.setText(device_names.get(2));
            btn4.setText(device_names.get(3));
            btn5.setText(device_names.get(4));
            btn6.setText(device_names.get(5));
            btn7.setText("");
            btn8.setText("");
            btn9.setText("");
            btn10.setText("");
            btn11.setText("");
            btn12.setText("");
            btn13.setText("");
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.BLACK);
            btn3.setBackgroundColor(Color.BLACK);
            btn4.setBackgroundColor(Color.BLACK);
            btn5.setBackgroundColor(Color.BLACK);
            btn6.setBackgroundColor(Color.BLACK);
            btn7.setBackgroundColor(Color.WHITE);
            btn8.setBackgroundColor(Color.WHITE);
            btn9.setBackgroundColor(Color.WHITE);
            btn10.setBackgroundColor(Color.WHITE);
            btn11.setBackgroundColor(Color.WHITE);
            btn12.setBackgroundColor(Color.WHITE);
            btn13.setBackgroundColor(Color.WHITE);
        }
        else if (SIZE == 7) {
            btn1.setText(device_names.get(0));
            btn2.setText(device_names.get(1));
            btn3.setText(device_names.get(2));
            btn4.setText(device_names.get(3));
            btn5.setText(device_names.get(4));
            btn6.setText(device_names.get(5));
            btn7.setText(device_names.get(6));
            btn8.setText("");
            btn9.setText("");
            btn10.setText("");
            btn11.setText("");
            btn12.setText("");
            btn13.setText("");
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.BLACK);
            btn3.setBackgroundColor(Color.BLACK);
            btn4.setBackgroundColor(Color.BLACK);
            btn5.setBackgroundColor(Color.BLACK);
            btn6.setBackgroundColor(Color.BLACK);
            btn7.setBackgroundColor(Color.BLACK);
            btn8.setBackgroundColor(Color.WHITE);
            btn9.setBackgroundColor(Color.WHITE);
            btn10.setBackgroundColor(Color.WHITE);
            btn11.setBackgroundColor(Color.WHITE);
            btn12.setBackgroundColor(Color.WHITE);
            btn13.setBackgroundColor(Color.WHITE);
        }
        else if (SIZE == 8) {
            btn1.setText(device_names.get(0));
            btn2.setText(device_names.get(1));
            btn3.setText(device_names.get(2));
            btn4.setText(device_names.get(3));
            btn5.setText(device_names.get(4));
            btn6.setText(device_names.get(5));
            btn7.setText(device_names.get(6));
            btn8.setText(device_names.get(7));
            btn9.setText("");
            btn10.setText("");
            btn11.setText("");
            btn12.setText("");
            btn13.setText("");
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.BLACK);
            btn3.setBackgroundColor(Color.BLACK);
            btn4.setBackgroundColor(Color.BLACK);
            btn5.setBackgroundColor(Color.BLACK);
            btn6.setBackgroundColor(Color.BLACK);
            btn7.setBackgroundColor(Color.BLACK);
            btn8.setBackgroundColor(Color.BLACK);
            btn9.setBackgroundColor(Color.WHITE);
            btn10.setBackgroundColor(Color.WHITE);
            btn11.setBackgroundColor(Color.WHITE);
            btn12.setBackgroundColor(Color.WHITE);
            btn13.setBackgroundColor(Color.WHITE);
        }
        else if (SIZE == 9) {
            btn1.setText(device_names.get(0));
            btn2.setText(device_names.get(1));
            btn3.setText(device_names.get(2));
            btn4.setText(device_names.get(3));
            btn5.setText(device_names.get(4));
            btn6.setText(device_names.get(5));
            btn7.setText(device_names.get(6));
            btn8.setText(device_names.get(7));
            btn9.setText(device_names.get(8));
            btn10.setText("");
            btn11.setText("");
            btn12.setText("");
            btn13.setText("");
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.BLACK);
            btn3.setBackgroundColor(Color.BLACK);
            btn4.setBackgroundColor(Color.BLACK);
            btn5.setBackgroundColor(Color.BLACK);
            btn6.setBackgroundColor(Color.BLACK);
            btn7.setBackgroundColor(Color.BLACK);
            btn8.setBackgroundColor(Color.BLACK);
            btn9.setBackgroundColor(Color.BLACK);
            btn10.setBackgroundColor(Color.WHITE);
            btn11.setBackgroundColor(Color.WHITE);
            btn12.setBackgroundColor(Color.WHITE);
            btn13.setBackgroundColor(Color.WHITE);
        }
        else if (SIZE == 10) {
            btn1.setText(device_names.get(0));
            btn2.setText(device_names.get(1));
            btn3.setText(device_names.get(2));
            btn4.setText(device_names.get(3));
            btn5.setText(device_names.get(4));
            btn6.setText(device_names.get(5));
            btn7.setText(device_names.get(6));
            btn8.setText(device_names.get(7));
            btn9.setText(device_names.get(8));
            btn10.setText(device_names.get(9));
            btn11.setText("");
            btn12.setText("");
            btn13.setText("");
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.BLACK);
            btn3.setBackgroundColor(Color.BLACK);
            btn4.setBackgroundColor(Color.BLACK);
            btn5.setBackgroundColor(Color.BLACK);
            btn6.setBackgroundColor(Color.BLACK);
            btn7.setBackgroundColor(Color.BLACK);
            btn8.setBackgroundColor(Color.BLACK);
            btn9.setBackgroundColor(Color.BLACK);
            btn10.setBackgroundColor(Color.BLACK);
            btn11.setBackgroundColor(Color.WHITE);
            btn12.setBackgroundColor(Color.WHITE);
            btn13.setBackgroundColor(Color.WHITE);
        }
        else if (SIZE == 11) {
            btn1.setText(device_names.get(0));
            btn2.setText(device_names.get(1));
            btn3.setText(device_names.get(2));
            btn4.setText(device_names.get(3));
            btn5.setText(device_names.get(4));
            btn6.setText(device_names.get(5));
            btn7.setText(device_names.get(6));
            btn8.setText(device_names.get(7));
            btn9.setText(device_names.get(8));
            btn10.setText(device_names.get(9));
            btn11.setText(device_names.get(10));
            btn12.setText("");
            btn13.setText("");
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.BLACK);
            btn3.setBackgroundColor(Color.BLACK);
            btn4.setBackgroundColor(Color.BLACK);
            btn5.setBackgroundColor(Color.BLACK);
            btn6.setBackgroundColor(Color.BLACK);
            btn7.setBackgroundColor(Color.BLACK);
            btn8.setBackgroundColor(Color.BLACK);
            btn9.setBackgroundColor(Color.BLACK);
            btn10.setBackgroundColor(Color.BLACK);
            btn11.setBackgroundColor(Color.BLACK);
            btn12.setBackgroundColor(Color.WHITE);
            btn13.setBackgroundColor(Color.WHITE);
        }
        else if (SIZE == 12) {
            btn1.setText(device_names.get(0));
            btn2.setText(device_names.get(1));
            btn3.setText(device_names.get(2));
            btn4.setText(device_names.get(3));
            btn5.setText(device_names.get(4));
            btn6.setText(device_names.get(5));
            btn7.setText(device_names.get(6));
            btn8.setText(device_names.get(7));
            btn9.setText(device_names.get(8));
            btn10.setText(device_names.get(9));
            btn11.setText(device_names.get(10));
            btn12.setText(device_names.get(11));
            btn13.setText("");
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.BLACK);
            btn3.setBackgroundColor(Color.BLACK);
            btn4.setBackgroundColor(Color.BLACK);
            btn5.setBackgroundColor(Color.BLACK);
            btn6.setBackgroundColor(Color.BLACK);
            btn7.setBackgroundColor(Color.BLACK);
            btn8.setBackgroundColor(Color.BLACK);
            btn9.setBackgroundColor(Color.BLACK);
            btn10.setBackgroundColor(Color.BLACK);
            btn11.setBackgroundColor(Color.BLACK);
            btn12.setBackgroundColor(Color.BLACK);
            btn13.setBackgroundColor(Color.WHITE);
        }
        else {
            btn1.setText(device_names.get(0));
            btn2.setText(device_names.get(1));
            btn3.setText(device_names.get(2));
            btn4.setText(device_names.get(3));
            btn5.setText(device_names.get(4));
            btn6.setText(device_names.get(5));
            btn7.setText(device_names.get(6));
            btn8.setText(device_names.get(7));
            btn9.setText(device_names.get(8));
            btn10.setText(device_names.get(9));
            btn11.setText(device_names.get(10));
            btn12.setText(device_names.get(11));
            btn13.setText(device_names.get(13));
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.BLACK);
            btn3.setBackgroundColor(Color.BLACK);
            btn4.setBackgroundColor(Color.BLACK);
            btn5.setBackgroundColor(Color.BLACK);
            btn6.setBackgroundColor(Color.BLACK);
            btn7.setBackgroundColor(Color.BLACK);
            btn8.setBackgroundColor(Color.BLACK);
            btn9.setBackgroundColor(Color.BLACK);
            btn10.setBackgroundColor(Color.BLACK);
            btn11.setBackgroundColor(Color.BLACK);
            btn12.setBackgroundColor(Color.BLACK);
            btn13.setBackgroundColor(Color.BLACK);
        }
    }
    public void one(View view) {
        int MAC_NUM = 0;
        SaveDeviceNameToFile(MAC_NUM);
    }
    public void two(View view) {
        int MAC_NUM = 1;
        SaveDeviceNameToFile(MAC_NUM);
    }
    public void three(View view) {
        int MAC_NUM = 2;
        SaveDeviceNameToFile(MAC_NUM);
    }
    public void four(View view) {
        int MAC_NUM = 3;
        SaveDeviceNameToFile(MAC_NUM);
    }
    public void five(View view) {
        int MAC_NUM = 5;
        SaveDeviceNameToFile(MAC_NUM);
    }
    public void six(View view) {
        int MAC_NUM = 5;
        SaveDeviceNameToFile(MAC_NUM);
    }
    public void seven(View view) {
        int MAC_NUM = 6;
        SaveDeviceNameToFile(MAC_NUM);
    }
    public void eight(View view) {
        int MAC_NUM = 7;
        SaveDeviceNameToFile(MAC_NUM);
    }
    public void nine(View view) {
        int MAC_NUM = 8;
        SaveDeviceNameToFile(MAC_NUM);
    }
    public void ten(View view) {
        int MAC_NUM = 9;
        SaveDeviceNameToFile(MAC_NUM);
    }
    public void eleven(View view) {
        int MAC_NUM = 10;
        SaveDeviceNameToFile(MAC_NUM);
    }
    public void twenteen(View view) {
        int MAC_NUM = 11;
        SaveDeviceNameToFile(MAC_NUM);
    }
    public void thirteen(View view){
        int MAC_NUM =12;
        SaveDeviceNameToFile(MAC_NUM);
    }



    private void SaveDeviceNameToFile(int MAC) {
        File file = new File(getFilesDir(), "device.list");
        FileWriter fw = null;
        BufferedWriter bufwr = null;
        String data = device_names.get(MAC);

        try {
            // open file.
            fw = new FileWriter(file);
            bufwr = new BufferedWriter(fw);
            bufwr.write(data);

            // write data to the file.
            bufwr.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // close file.
            if (bufwr != null) {
                bufwr.close();
            }

            if (fw != null) {
                fw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
//       Intent discoverableIntent =
//                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
//        startActivity(discoverableIntent);
// 블루투스 검색 가능하도록 허용하는 코드 나중에 필요할 경우를 위해 이렇게 저장장
