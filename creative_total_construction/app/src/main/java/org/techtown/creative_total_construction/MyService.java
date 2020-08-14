package org.techtown.creative_total_construction;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import javax.crypto.Mac;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

import static app.akexorcist.bluetotohspp.library.BluetoothState.REQUEST_ENABLE_BT;

public class MyService extends Service {
    MediaPlayer mp; // 음악 재생을 위한 객체
    ArrayList<String> TimeTable = new ArrayList<String>();
    int playingLogic = 0;
    String buttonLogic = "0";
    Boolean bool;
    double longitude;
    double latitude;
    int init = 0;
    String data1, data2, data3;
    String SOS_Logic = "0";
    String SOS_INFO;
    String data_name, data_age, data_sex, data_ill,data_drug, data_home;
    String LocationInfo;
    class InstructionThread extends Thread {
        @Override
        public void run() {
            init = 1;
            loadItemsFromFile1();
            loadItemsFromFile2();
            loadItemsFromFile3();
            try {
                Thread.sleep(600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (buttonLogic.equals("0")) {
                try {
                    SendSMS(data1,"약통 소지자가 복약하지 않았습니다. 복약지도를 해주십시오.");
                }
                catch (Exception e) {
                    Log.d("msg", "There is no data or proper form of account to send msg.");
                }
                try {
                    SendSMS(data2,"약통 소지자가 복약하지 않았습니다. 복약지도를 해주십시오.");
                }
                catch (Exception e) {
                    Log.d("msg", "There is no data or proper form of account to send msg.");
                }
                try {
                    SendSMS(data3,"약통 소지자가 복약하지 않았습니다. 복약지도를 해주십시오.");
                }
                catch (Exception e) {
                    Log.d("msg", "There is no data or proper form of account to send msg.");
                }
                init = 1;
            }
            else {
                init = 0;
            }


        }

    }

    class CountThread extends Thread {
        @Override
        public void run() {
            AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float percent = 0.7f;
            int seventyVolume = (int) (maxVolume * percent);
            while (true) {
                if(SOS_Logic.equals("1")) {
                    SetSosInfo();
                    //SendSMS("119", SOS_INFO);
                    Log.d("Status", "Sos msg 성공");

                }
                loadButtonLogic();
                loadItemsFromFile();
                if (playingLogic == 0) {
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm");
                    String time = mFormat.format(date);
                    Log.d(time, "서비스의 onStartCommand");
                    for (String value : TimeTable) {
                        bool = (value.equals(time));
                        if (bool) {
                            playingLogic = 1;
                            Log.d(time, "성공적 진입");
                        }
                    }
                }//play logic end
                else if (playingLogic == 1) {
                    if(init == 0) {
                        InstructionThread thread = new InstructionThread();
                        thread.start();
                    }
                    audio.setStreamVolume(AudioManager.STREAM_MUSIC, seventyVolume, 0);
                    if (!mp.isPlaying()) {
                        mp.start();
                        Log.d("Status", "Music on");

                    }
                }
                if (buttonLogic.equals("1")) {
                    Log.d("Status", "Off State access");
                    if (mp.isPlaying()) {
                        mp.pause();
                    } else {
                        mp.stop();
                    }
                    buttonLogic = "0";
                    playingLogic = 0;
                    SaveButtonLogicToFile();
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //60seconds delay is needed
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 (화면단 Activity 사이에서)
        // 통신(데이터를 주고받을) 때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 return null;
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        witch();
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        loadItemsFromFile(); //To update alarm data for Every call of BackGround Service
        Log.d("test", "서비스의 onCreate");
        mp = MediaPlayer.create(this, R.raw.chacha);
        mp.setLooping(true); // 반복재생
        CountThread thread = new CountThread();
        thread.start();
        //BluetoothThread TThread = new BluetoothThread();
        //TThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test", "서비스의 onDestroy");

    }

    private void loadButtonLogic() {
        File file = new File(getFilesDir(), "Button_Logic.list");
        FileReader fr = null;
        BufferedReader bufrd = null;
        String str;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file);
                bufrd = new BufferedReader(fr);

                while ((str = bufrd.readLine()) != null) {
                    buttonLogic = str;
                }

                bufrd.close();
                fr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void loadItemsFromFile() { //Function for loading data in Device
        File file = new File(getFilesDir(), "time_items.list");
        FileReader fr = null;
        BufferedReader bufrd = null;
        String str;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file);
                bufrd = new BufferedReader(fr);

                while ((str = bufrd.readLine()) != null) {
                    TimeTable.add(str);
                }

                bufrd.close();
                fr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void SaveButtonLogicToFile() {
        File file = new File(getFilesDir(), "Button_Logic.list");
        FileWriter fw = null;
        BufferedWriter bufwr = null;

        try {
            // open file.
            fw = new FileWriter(file);
            bufwr = new BufferedWriter(fw);
            bufwr.write("0");

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
    private void SaveLocationToFile(String Location) {
        File file = new File(getFilesDir(), "Location.list");
        FileWriter fw = null;
        BufferedWriter bufwr = null;

        try {
            // open file.
            fw = new FileWriter(file);
            bufwr = new BufferedWriter(fw);
            bufwr.write(Location);

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

    public void witch() {
        try {
            final LocationListener gpsLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    String provider = location.getProvider();
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    double altitude = location.getAltitude();
                    String address = getCurrentAddress(latitude,longitude);
                    LocationInfo = "위치정보 : " +  provider + "\n" + "위도 : " + longitude + "\n" + "경도 : " + latitude + "\n" + "고도  : " + altitude + "\n" + "주소 : " + address;
                    SaveLocationToFile(LocationInfo);
                    Log.d("위치정보 : ", provider + "\n" +
                            "위도 : " + longitude + "\n" +
                            "경도 : " + latitude + "\n" +
                            "고도  : " + altitude + "\n" +
                            "주소 : " + address);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            Log.d("진입", "성공");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.d("permission", "이 부분에서 문제 발생");
                return;
            }
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();

            Log.d("위치정보 : ", provider + "\n" +
                    "위도 : " + longitude + "\n" +
                    "경도 : " + latitude + "\n" +
                    "고도  : " + altitude);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
        }
        catch (Exception e){
            Log.d("실패", "닫힌창에서의 GPS동작 실패");

        }
    }
    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }



        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }



    @Override
    public void onTaskRemoved(Intent rootIntent){
        super.onTaskRemoved(rootIntent);

}
    void SendSMS(String number,String msg){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number,null,msg,null,null);
    }
    private void loadItemsFromFile1() { //Function for loading data in Device
        File file = new File(getFilesDir(), "bohoja1.list") ;
        FileReader fr = null ;
        BufferedReader bufrd = null ;
        String str ;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file) ;
                bufrd = new BufferedReader(fr) ;
                while ((str = bufrd.readLine()) != null) {
                    data1 = str;
                }
                bufrd.close() ;
                fr.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }
    private void loadItemsFromFile2() { //Function for loading data in Device
        File file = new File(getFilesDir(), "bohoja2.list") ;
        FileReader fr = null ;
        BufferedReader bufrd = null ;
        String str ;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file) ;
                bufrd = new BufferedReader(fr) ;
                while ((str = bufrd.readLine()) != null) {
                    data2 = str;
                }
                bufrd.close() ;
                fr.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }
    public void SetSosInfo() {
        loadAgeFromFile1();
        loadNameFromFile1();
        loadSexFromFile1();
        loadIllFromFile1();
        loadDrugFromFile1();
        loadHomeFromFile1();
        SOS_INFO = "[긴급 구조 필요] 구조자 정보 -> " + " 이름 : " + data_name + "\n" + " 나이 : " + data_age + "\n" + " 성별 : " + data_sex + "\n" + "지병 정보 : " + data_ill + "\n"
                + "복용 중인 약 : " + data_drug + "\n" + "상세 자택 주소 : " + data_home + "\n" + "최근 GPS 정보 : "  + LocationInfo;
    }
    private void loadHomeFromFile1() { //Function for loading data in Device
        File file = new File(getFilesDir(), "home.list") ;
        FileReader fr = null ;
        BufferedReader bufrd = null ;
        String str ;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file) ;
                bufrd = new BufferedReader(fr) ;
                while ((str = bufrd.readLine()) != null) {
                    data_home = str;
                }
                bufrd.close() ;
                fr.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }
    private void loadDrugFromFile1() { //Function for loading data in Device
        File file = new File(getFilesDir(), "drug.list") ;
        FileReader fr = null ;
        BufferedReader bufrd = null ;
        String str ;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file) ;
                bufrd = new BufferedReader(fr) ;
                while ((str = bufrd.readLine()) != null) {
                    data_drug = str;
                }
                bufrd.close() ;
                fr.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }
    private void loadIllFromFile1() { //Function for loading data in Device
        File file = new File(getFilesDir(), "ill.list") ;
        FileReader fr = null ;
        BufferedReader bufrd = null ;
        String str ;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file) ;
                bufrd = new BufferedReader(fr) ;
                while ((str = bufrd.readLine()) != null) {
                    data_ill = str;
                }
                bufrd.close() ;
                fr.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }
    private void loadSexFromFile1() { //Function for loading data in Device
        File file = new File(getFilesDir(), "sex.list") ;
        FileReader fr = null ;
        BufferedReader bufrd = null ;
        String str ;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file) ;
                bufrd = new BufferedReader(fr) ;
                while ((str = bufrd.readLine()) != null) {
                    data_sex = str;
                }
                bufrd.close() ;
                fr.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }
    private void loadAgeFromFile1() { //Function for loading data in Device
        File file = new File(getFilesDir(), "age.list") ;
        FileReader fr = null ;
        BufferedReader bufrd = null ;
        String str ;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file) ;
                bufrd = new BufferedReader(fr) ;
                while ((str = bufrd.readLine()) != null) {
                    data_age = str;
                }
                bufrd.close() ;
                fr.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }
    private void loadNameFromFile1() { //Function for loading data in Device
        File file = new File(getFilesDir(), "name.list") ;
        FileReader fr = null ;
        BufferedReader bufrd = null ;
        String str ;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file) ;
                bufrd = new BufferedReader(fr) ;
                while ((str = bufrd.readLine()) != null) {
                    data_name = str;
                }
                bufrd.close() ;
                fr.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }
    private void loadItemsFromFile3() { //Function for loading data in Device
        File file = new File(getFilesDir(), "bohoja3.list") ;
        FileReader fr = null ;
        BufferedReader bufrd = null ;
        String str ;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file) ;
                bufrd = new BufferedReader(fr) ;
                while ((str = bufrd.readLine()) != null) {
                    data3 = str;
                }
                bufrd.close() ;
                fr.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }
    }





