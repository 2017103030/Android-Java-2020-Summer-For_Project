package org.techtown.creative_total_construction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class boho extends AppCompatActivity {
    TextView textView_how;
    int HowLogic = 0;
    String HowToDo;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editMemo;
    String strText1;
    String strText2;
    String strText3;
    String data1;
    String data2;
    String data3;
    String Memo = "메모 : ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bohoja);
        textView_how = findViewById(R.id.howto);
        editText1 = findViewById(R.id.editText1) ;
        editText2 = findViewById(R.id.editText2) ;
        editText3 = findViewById(R.id.editText3) ;
        editMemo = findViewById(R.id.editText124);
        HowToDo = "사용법 : 스크린에 나타나있는 3개의 입력이 가능한 창에 보호자 번호 하나씩 입력할 수 있습니다." +
                " 정확한 복약지도를 위해 약을 복용하지 않을 시 입려된 전화번호들로 연락이 가게 됩니다. 입력 예시) 01011112222 (설명 끄는 법 : 오른쪽위의 사용법 버튼을 다시 눌러주세요.)";
        loadItemsFromFile1();
        loadItemsFromFile2();
        loadItemsFromFile3();
        loadMemoFromFile();
        editMemo.setText(Memo);
        editText1.setText(data1);
        editText2.setText(data2);
        editText3.setText(data3);
    }
    public void onClickMemo(View view) {
        Memo = editMemo.getText().toString();
        SaveMemoToFile();
        Toast.makeText(this.getApplicationContext(), "메모를 저장했습니다.", Toast.LENGTH_LONG).show();
    }
    public void onClickHowTo(View view) {
        if (HowLogic == 0) {
            textView_how.setText(HowToDo);
            HowLogic = 1;
        }
        else if (HowLogic == 1) {
            textView_how.setText("");
            HowLogic = 0;
        }
    }
    public void onClickSave(View view) {
        strText1 = editText1.getText().toString() ;
        strText2 = editText2.getText().toString() ;
        strText3 = editText3.getText().toString() ;
        int first = strText1.length();
        int second = strText2.length();
        int third = strText3.length();
        if (first == 11) {
            saveItemsToFile1();
        }
        else if (first == 0){
        }
        else {
            Toast.makeText(this.getApplicationContext(), "잘못된 형식의 전화번호가 존재합니다. 저장하지 않으시려면 입력창의 모든 텍스트를 지워주세요", Toast.LENGTH_LONG).show();
        }
        if(second == 11) {
            saveItemsToFile2();
        }
        else if (second == 0){

        }
        else {
            Toast.makeText(this.getApplicationContext(), "잘못된 형식의 전화번호가 존재합니다. 저장하지 않으시려면 입력창의 모든 텍스트를 지워주세요", Toast.LENGTH_LONG).show();
        }
        if (third == 11) {
            saveItemsToFile3();
        }
        else if (third == 0) {
        }
        else {
            Toast.makeText(this.getApplicationContext(), "잘못된 형식의 전화번호입니다. 저장하지 않으시려면 입력창의 모든 텍스트를 지워주세요", Toast.LENGTH_LONG).show();
        }

        if (first == 11 || first == 0) {
            if(second == 11 || second == 0) {
                if(third == 11 || third == 0) {
                    Toast.makeText(this.getApplicationContext(), "전체 전화번호가 성공적으로 저장되었습니다.", Toast.LENGTH_LONG).show();
                }
            }
        }

    }
    public void onClickDel(View view) {
        File file = new File(getFilesDir(), "bohoja1.list") ;
        FileWriter fw = null ;
        BufferedWriter bufwr = null ;

        try {
            // open file.
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;
            bufwr.write("") ;
            bufwr.newLine() ;


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
        File file2 = new File(getFilesDir(), "bohoja2.list") ;
        FileWriter fw2 = null ;
        BufferedWriter bufwr2 = null ;

        try {
            // open file.
            fw2 = new FileWriter(file2) ;
            bufwr2 = new BufferedWriter(fw2) ;
            bufwr2.write("") ;
            bufwr2.newLine() ;


            // write data to the file.
            bufwr2.flush() ;

        } catch (Exception e) {
            e.printStackTrace() ;
        }

        try {
            // close file.
            if (bufwr2 != null) {
                bufwr2.close();
            }

            if (fw2 != null) {
                fw2.close();
            }
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        File file3 = new File(getFilesDir(), "bohoja3.list") ;
        FileWriter fw3 = null ;
        BufferedWriter bufwr3 = null ;

        try {
            // open file.
            fw3 = new FileWriter(file3) ;
            bufwr3 = new BufferedWriter(fw3) ;
            bufwr3.write("") ;
            bufwr3.newLine() ;


            // write data to the file.
            bufwr3.flush() ;

        } catch (Exception e) {
            e.printStackTrace() ;
        }

        try {
            // close file.
            if (bufwr3 != null) {
                bufwr3.close();
            }

            if (fw3 != null) {
                fw3.close();
            }
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        editText1.setText("");
        editText2.setText("");
        editText3.setText("");
        Toast.makeText(this.getApplicationContext(), "전체 전화번호가 초기화 되었습니다.", Toast.LENGTH_LONG).show();
    }
    public void onClickHome(View view) // Go to Main Activity Code
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this.getApplicationContext(), "홈 화면으로 이동합니다.", Toast.LENGTH_LONG).show();

        finish();
    }
    private void saveItemsToFile1() {//Function for storing data in device
        File file = new File(getFilesDir(), "bohoja1.list") ;
        FileWriter fw = null ;
        BufferedWriter bufwr = null ;

        try {
            // open file.
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;
            bufwr.write(strText1) ;
            bufwr.newLine() ;


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
    private void saveItemsToFile2() {//Function for storing data in device
        File file = new File(getFilesDir(), "bohoja2.list") ;
        FileWriter fw = null ;
        BufferedWriter bufwr = null ;

        try {
            // open file.
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;
            bufwr.write(strText2) ;
            bufwr.newLine() ;


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
    private void saveItemsToFile3() {//Function for storing data in device
        File file = new File(getFilesDir(), "bohoja3.list") ;
        FileWriter fw = null ;
        BufferedWriter bufwr = null ;

        try {
            // open file.
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;
            bufwr.write(strText3) ;
            bufwr.newLine() ;


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
    private void SaveMemoToFile() {
        File file = new File(getFilesDir(), "Memo2.list");
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
    private void loadMemoFromFile() { //Function for loading data in Device
        File file = new File(getFilesDir(), "Memo2.list") ;
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

