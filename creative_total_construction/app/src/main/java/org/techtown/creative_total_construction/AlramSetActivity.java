package org.techtown.creative_total_construction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Messenger;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class AlramSetActivity extends AppCompatActivity {
    private TimePicker tp;
    TextView textView; //To show Alarm list to App User
    int User_hour;//User Define hour
    int User_minute;//User Define minute
    String new_User_hour; //just same but String
    String new_User_minute;//just same but String
    String showing = "설정한 알람 : "; //Ingredient for TextView textView
    String para; // Human Friendly time notification parameter
    ArrayList<String> TimeTable = new ArrayList<String>(); //list type of alarm
    String Memo;
    EditText Memotext;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
        textView = findViewById(R.id.textView);
        loadItemsFromFile();
        Memotext = findViewById(R.id.editText1);
        loadMemoFromFile();
        try {
            Memotext.setText(Memo);
        }
        catch (Exception e) {
            Log.d("msg", "Fail to Upload");
        }
        for (String value : TimeTable) {
            showing = showing + "\n" + value;
        }
        textView.setText(showing);
    }
    public void onClickHome(View view) // Go to Main Activity Code
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this.getApplicationContext(), "홈 화면으로 이동합니다.", Toast.LENGTH_LONG).show();

        finish();
    }
    public void onClickSaveMemo(View view) {
        Toast.makeText(this.getApplicationContext(), "메모를 저장했습니다.", Toast.LENGTH_LONG).show();

        Memo = Memotext.getText().toString();
        SaveMemoToFile();
    }

    public void onClickTime(View view) //Alarm Update Code
    {
        showing = "지정된 알람 : ";
        tp = (TimePicker) findViewById(R.id.time_picker);
        if (Build.VERSION.SDK_INT >= 23) {
            User_hour = tp.getHour();
            User_minute = tp.getMinute();
        } else {
            User_hour = tp.getCurrentHour();
            User_minute = tp.getCurrentMinute();

        }
        new_User_hour = Integer.toString(User_hour);
        new_User_minute = Integer.toString(User_minute);
        if (new_User_hour.length() == 1) {
            new_User_hour ="0" + new_User_hour;
        }
        if (new_User_minute.length() == 1) {
            new_User_minute ="0" + new_User_minute;
        }
        para = new_User_hour + ":" + new_User_minute;

        TimeTable.add(para);
        Toast.makeText(this.getApplicationContext(), "알람이 추가되었습니다.", Toast.LENGTH_LONG).show();

        for (String value : TimeTable) {
            showing = showing + "\n" + value;
        }

        textView.setText(showing);
        saveItemsToFile();
    }

    public void onClickOff(View view) {
        SaveButtonLogicToFile();
        Toast.makeText(this.getApplicationContext(), "알람을 종료시킵니다.", Toast.LENGTH_LONG).show();
    }

    public void onClickReset(View view) //Alarm Reset Code
    {
        showing = "지정된 알람 : ";
        Toast.makeText(this.getApplicationContext(), "알람이 초기화 되었습니다.", Toast.LENGTH_LONG).show();
        TimeTable = new ArrayList<String>();

        for (String value : TimeTable) {
            showing = showing + "\n" + value;
        }
        textView.setText(showing);
        saveItemsToFile();
    }

    private void saveItemsToFile() {//Function for storing data in device
        File file = new File(getFilesDir(), "time_items.list") ;
        FileWriter fw = null ;
        BufferedWriter bufwr = null ;

        try {
            // open file.
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;

            for (String str : TimeTable) {
                bufwr.write(str) ;
                bufwr.newLine() ;
            }

            // write data to the file.
            bufwr.flush() ;

        } catch (Exception e) {
            e.printStackTrace() ;
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
            e.printStackTrace() ;
        }
    }
    private void loadItemsFromFile() { //Function for loading data in Device
        File file = new File(getFilesDir(), "time_items.list") ;
        FileReader fr = null ;
        BufferedReader bufrd = null ;
        String str ;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file) ;
                bufrd = new BufferedReader(fr) ;

                while ((str = bufrd.readLine()) != null) {
                    TimeTable.add(str) ;
                }

                bufrd.close() ;
                fr.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }
    private void SaveMemoToFile() {
        File file = new File(getFilesDir(), "Memo.list");
        FileWriter fw = null ;
        BufferedWriter bufwr = null ;

        try {
            // open file.
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;
            bufwr.write(Memo) ;

            // write data to the file.
            bufwr.flush() ;

        } catch (Exception e) {
            e.printStackTrace() ;
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
            e.printStackTrace() ;
        }

    }
    private void SaveButtonLogicToFile() {
        File file = new File(getFilesDir(), "Button_Logic.list");
        FileWriter fw = null ;
        BufferedWriter bufwr = null ;

        try {
            // open file.
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;
            bufwr.write("1") ;

            // write data to the file.
            bufwr.flush() ;

        } catch (Exception e) {
            e.printStackTrace() ;
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
            e.printStackTrace() ;
        }

    }
    private void loadMemoFromFile() { //Function for loading data in Device
        File file = new File(getFilesDir(), "Memo.list") ;
        FileReader fr = null ;
        BufferedReader bufrd = null ;
        String str ;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file) ;
                bufrd = new BufferedReader(fr) ;
                while ((str = bufrd.readLine()) != null) {
                    Memo = str;
                }
                bufrd.close() ;
                fr.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }



}
