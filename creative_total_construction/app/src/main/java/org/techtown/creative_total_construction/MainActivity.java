package org.techtown.creative_total_construction;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

import static app.akexorcist.bluetotohspp.library.BluetoothState.REQUEST_ENABLE_BT;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Set<BluetoothDevice> mDevices;
    OutputStream mOutputStream;
    InputStream mInputStream;
    byte mDelimiter;
    String LOCATION = "미정";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("info");
                builder.setMessage("SMS 권한을 설정하지 않을 경우 어플리케이션에 문제 발생 가능");
                builder.setIcon(android.R.drawable.ic_dialog_info);

                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.SEND_SMS}, 1001);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.SEND_SMS}, 1001);
            }
        }
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( MainActivity.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }
        setContentView(R.layout.activity_main);
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // 페어링 된 장치가 있는 경우.
        } else {
            // 페어링 된 장치가 없는 경우.
        }
        if (mBluetoothAdapter == null) {
            Log.d("Error_msg", "This Device don't support bluetooth tech sorry.");
        }
        if (!mBluetoothAdapter.isEnabled()) { // bluetooth 켜기
            Log.d("msg", "Getting ready for turn on the bluetooth");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        Intent intent = new Intent(
                getApplicationContext(),//현재제어권자
                MyService.class); // 이동할 컴포넌트
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent); // 서비스 시작
        }
            startService(intent);



    }


    public void onClickTime(View view) {
        Intent intent = new Intent(this, AlramSetActivity.class);
        startActivity(intent);
        Toast.makeText(this.getApplicationContext(), "알람 설정 화면으로 이동합니다.", Toast.LENGTH_LONG).show();
        finish();
    }
    public void onClickGPS(View view) {
        loadGPS();
        SendSMS("01028620411","Message Test : Moon is BABO");
        Toast.makeText(this.getApplicationContext(), LOCATION, Toast.LENGTH_LONG).show();
    }

    public void onClickBoHo(View view) {
        Intent intent2 = new Intent(this, boho.class);
        startActivity(intent2);
        Toast.makeText(this.getApplicationContext(), "보호자 설정 화면으로 이동합니다.", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onClickUserSet(View view) {
        Intent intent = new Intent(this, UserSet.class);
        startActivity(intent);
        Toast.makeText(this.getApplicationContext(), "사용자 설정 화면으로 이동합니다.", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onClickBluetooth(View view) {
        selectDevice();
    }

    public void onClickFind(View view){
        sendData("9"); //9는 약통 찾기 신호이다.
    }


    //Intent intent = new Intent(this,bluetooth_wizards.class);
    //startActivity(intent);
    //Toast.makeText(this.getApplicationContext(),"블루투스 설정 화면으로 이동합니다.",Toast.LENGTH_LONG).show();
    //finish();

    public void onClickSearch(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://nedrug.mfds.go.kr/index"));
        startActivity(intent);
        Toast.makeText(this.getApplicationContext(), "의약품 안전나라 페이지로 이동합니다.", Toast.LENGTH_LONG).show();
    }


    void selectDevice(){
        ArrayList<String> device_names = new ArrayList<String>();
        mDevices = mBluetoothAdapter.getBondedDevices();
        final int mPairedDeviceCount = mDevices.size();

        if (mPairedDeviceCount == 0) {
            Toast.makeText(this.getApplicationContext(), "페어링된 디바이스가 없습니다.", Toast.LENGTH_LONG).show();
            //  페어링 된 장치가 없는 경우
            // 어플리케이션 종료
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("블루투스 장치 선택");
        // 페어링 된 블루투스 장치의 이름 목록 작성
        List<String> listItems = new ArrayList<String>();
        for (BluetoothDevice device : mDevices) {
            listItems.add(device.toString());
            device_names.add(device.getName());
        }

        listItems.add("취소");    // 취소 항목 추가
        device_names.add("취소");

        final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
        final CharSequence[] names = device_names.toArray(new CharSequence[device_names.size()]);

        builder.setItems(names, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == mPairedDeviceCount) {
                    // 연결할 장치를 선택하지 않고 '취소'를 누른 경우
                    Log.d("Status", "Cancel");
                } else {
                    // 연결할 장치를 선택한 경우
                    // 선택한 장치와 연결을 시도함
                    connectToSelectedDevices(items[item].toString());
                }
            }
        });

        builder.setCancelable(false);    // 뒤로 가기 버튼 사용 금지
        AlertDialog alert = builder.create();
        alert.show();
    }
    void connectToSelectedDevices(String selectedDeviceName) {
        BluetoothDevice mRemoteDevice = getDeviceFromBondedList(selectedDeviceName);
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try {
            // 소켓 생성
            BluetoothSocket mSocket = mRemoteDevice.createRfcommSocketToServiceRecord(uuid);
            // RFCOMM 채널을 통한 연결
            mSocket.connect();

            // 데이터 송수신을 위한 스트림 열기
            mOutputStream = mSocket.getOutputStream();
            mInputStream = mSocket.getInputStream();

            // 데이터 수신 준비
            beginListenForData();
        }catch(Exception e) {
            // 블루투스 연결 중 오류 발생
            finish();   // 어플 종료
        }
    }


    BluetoothDevice getDeviceFromBondedList(String name) {
        BluetoothDevice selectedDevice = null;

        for(BluetoothDevice device : mDevices) {
            if(name.equals(device.getName())) {
                selectedDevice = device;
                break;
            }
        }
        return selectedDevice;
    }
    void beginListenForData()
    {
        final Handler handler = new Handler();

        final byte[] readBuffer = new byte[1024] ;  //  수신 버퍼
        final int[] readBufferPositon = {0};        //   버퍼 내 수신 문자 저장 위치

        // 문자열 수신 쓰레드
        Thread mWorkerThread = new Thread(new Runnable() {
            public void run() {
                while(!Thread.currentThread().isInterrupted()){

                    try {
                        int bytesAvailable = mInputStream.available();    // 수신 데이터 확인
                        if(bytesAvailable > 0) {                     // 데이터가 수신된 경우
                            byte[] packetBytes = new byte[bytesAvailable];
                            mInputStream.read(packetBytes);
                            for(int i=0 ; i<bytesAvailable; i++) {
                                byte b = packetBytes[i];
                                if(b == mDelimiter) {
                                    byte[] encodedBytes = new byte[readBufferPositon[0]];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPositon[0] = 0;

                                    handler.post(new Runnable() {
                                        public void run() {
                                            // 수신된 문자열 데이터에 대한 처리 작업
                                        }
                                    });
                                }
                                else {
                                    readBuffer[readBufferPositon[0]++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex) {
                        // 데이터 수신 중 오류 발생.
                        finish();
                    }
                }
            }
        });

        mWorkerThread.start();
    }
    void sendData(String msg) {
        msg += mDelimiter;    // 문자열 종료 표시

        try {
            mOutputStream.write(msg.getBytes());    // 문자열 전송
            Log.d("Status", "MESSAGE is sended!");
        } catch(Exception e) {
            // 문자열 전송 도중 오류가 발생한 경우.
            Log.d("Error", "ERROR!");
            finish();    //  APP 종료
        }
    }
    private void SaveDeviceName(String device) {
        File file = new File(getFilesDir(), "device.list");
        FileWriter fw = null;
        BufferedWriter bufwr = null;

        try {
            // open file.
            fw = new FileWriter(file);
            bufwr = new BufferedWriter(fw);
            bufwr.write(device);

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
    private void loadGPS() {
        File file = new File(getFilesDir(), "Location.list");
        FileReader fr = null;
        BufferedReader bufrd = null;
        String str;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file);
                bufrd = new BufferedReader(fr);

                while ((str = bufrd.readLine()) != null) {
                    LOCATION = str;
                }

                bufrd.close();
                fr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    void SendSMS(String number,String msg){

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number,null,msg,null,null);
    }




    }


        // end of class


