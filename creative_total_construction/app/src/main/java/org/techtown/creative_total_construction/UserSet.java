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

public class UserSet extends AppCompatActivity {
    String HowToDo;
    int HowLogic;
    TextView textView_how;
    EditText text_name;
    EditText text_age;
    EditText text_sex;
    EditText text_ill;
    EditText text_drug;
    EditText text_home;
    String strname;
    String strage;
    String strsex;
    String strill;
    String strdurg;
    String strhome;
    String data_name;
    String data_age;
    String data_sex;
    String data_ill;
    String data_drug;
    String data_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_for_user);
        HowToDo = "사용법 : 스크린에 나타나 있는 텍스트 창에 정보를 입력해주시면 됩니다.\n1. 이름 칸에는 이용자의 이름을 적어주세요.\n2. 나이 칸에는 사용자의 나이를 적어주세요.\n3.성별칸에는 사용자의 성별을 적어주세요." +
                "\n4.지병칸에는 만약 지병이 있으실 경우 이에 대해 상세하게 서술해주세요.\n5.약 칸에는 현재 복용중인 약물에 관해 상세하게 적어주세요.\n6.자택 칸에는 상세한 집 주소를 적어주세요." +
                "\n 이렇게 적어주신 정보들은 위급상황시 119에 정확한 정보를 전달하기 위해 사용됩니다. :)" +
                "설명창을 끄고 싶으시면 버튼을 한번 더 눌러주세요.";
        HowLogic = 0;
        textView_how = findViewById(R.id.textViewhow);
        text_name = findViewById(R.id.editTextName);
        text_age = findViewById(R.id.editTextAge);
        text_sex = findViewById(R.id.editTextSex);
        text_ill = findViewById(R.id.editTextIll);
        text_drug = findViewById(R.id.editTextDrug);
        text_home = findViewById(R.id.editTextHome);
        loadAgeFromFile1();
        loadDrugFromFile1();
        loadHomeFromFile1();
        loadIllFromFile1();
        loadSexFromFile1();
        loadNameFromFile1();
        text_name.setText(data_name);
        text_age.setText(data_age);
        text_drug.setText(data_drug);
        text_sex.setText(data_sex);
        text_ill.setText(data_ill);
        text_home.setText(data_home);
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
    public void onClickSaveSave(View view) {
        strname = text_name.getText().toString();
        strage = text_age.getText().toString();
        strsex = text_sex.getText().toString();
        strill = text_ill.getText().toString();
        strdurg = text_drug.getText().toString();
        strhome = text_home.getText().toString();
        saveAgeToFile();
        saveDrugToFile();
        saveHomeToFile();
        saveIllToFile();
        saveNameToFile();
        saveSexToFile();
        Toast.makeText(this.getApplicationContext(), "정보들을 저장했습니다.", Toast.LENGTH_LONG).show();
    }

    public void  onClickReset(View view) {
        strname = "";
        strdurg = "";
        strage = "";
        strhome = "";
        strill = "";
        strsex = "";
        saveSexToFile();
        saveNameToFile();
        saveIllToFile();
        saveHomeToFile();
        saveDrugToFile();
        saveAgeToFile();
        Toast.makeText(this.getApplicationContext(), "정보들을 초기화 했습니다.", Toast.LENGTH_LONG).show();
        text_name.setText("");
        text_age.setText("");
        text_drug.setText("");
        text_sex.setText("");
        text_ill.setText("");
        text_home.setText("");

    }
    public void onClickHome(View view) // Go to Main Activity Code
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this.getApplicationContext(), "홈 화면으로 이동합니다.", Toast.LENGTH_LONG).show();

        finish();
    }

    private void saveNameToFile() {//Function for storing data in device
        File file = new File(getFilesDir(), "name.list") ;
        FileWriter fw = null ;
        BufferedWriter bufwr = null ;

        try {
            // open file.
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;
            bufwr.write(strname) ;
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
    private void saveAgeToFile() {//Function for storing data in device
        File file = new File(getFilesDir(), "age.list") ;
        FileWriter fw = null ;
        BufferedWriter bufwr = null ;

        try {
            // open file.
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;
            bufwr.write(strage) ;
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
    private void saveSexToFile() {//Function for storing data in device
        File file = new File(getFilesDir(), "sex.list") ;
        FileWriter fw = null ;
        BufferedWriter bufwr = null ;

        try {
            // open file.
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;
            bufwr.write(strsex) ;
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
    private void saveIllToFile() {//Function for storing data in device
        File file = new File(getFilesDir(), "ill.list") ;
        FileWriter fw = null ;
        BufferedWriter bufwr = null ;

        try {
            // open file.
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;
            bufwr.write(strill) ;
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
    private void saveDrugToFile() {//Function for storing data in device
        File file = new File(getFilesDir(), "drug.list") ;
        FileWriter fw = null ;
        BufferedWriter bufwr = null ;

        try {
            // open file.
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;
            bufwr.write(strdurg) ;
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
    private void saveHomeToFile() {//Function for storing data in device
        File file = new File(getFilesDir(), "home.list") ;
        FileWriter fw = null ;
        BufferedWriter bufwr = null ;

        try {
            // open file.
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;
            bufwr.write(strhome) ;
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

}
