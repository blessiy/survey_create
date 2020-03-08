package com.example.survey_create;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

public class ReportActivity extends AppCompatActivity {
    private TextView mq1tv,ma1tv,mq2tv,ma2tv,mq3tv,ma3tv;
    private Button finishbutton;
    private String [] mans=new String [3];
    private String [] mqs = new String [3];

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        finishbutton=findViewById(R.id.button);
        mq1tv=findViewById(R.id.question1);
        mq2tv=findViewById(R.id.question2);
        mq3tv=findViewById(R.id.question3);
        ma1tv=findViewById(R.id.answer1);
        ma2tv=findViewById(R.id.answer2);
        ma3tv=findViewById(R.id.answer3);

        SharedPreferences sp = getSharedPreferences("SP", MODE_PRIVATE);

        mans[0]=sp.getString("answer1","");
        mans[1]=sp.getString("answer2","");
        mans[2]=sp.getString("answer3","");

        mqs[0]=sp.getString("question1","");
        mqs[1]=sp.getString("question2","");
        mqs[2]=sp.getString("question3","");

        mq1tv.setText(mqs[0]);
        mq2tv.setText(mqs[1]);
        mq3tv.setText(mqs[2]);
        ma1tv.setText(mans[0]);
        ma2tv.setText(mans[1]);
        ma3tv.setText(mans[2]);

       //转换为Json数据
        JSONArray array =new JSONArray();
        for(int i=0; i<3; i++) {
            JSONObject object =new JSONObject(new LinkedHashMap());
            object.put("Question",mqs[i]);
            object.put("Answer",mans[i]);
            array.add(object);
        }
        JSONObject obj =new JSONObject(new LinkedHashMap());
        obj.put("SurveyData",array);

        verifyStoragePermissions(ReportActivity.this);

        try {
            //存储saveData到sd卡上
            new saveFileInEx().saveFile(obj);
            //存储saveData到内部存储
            new saveFileInEx().saveFileInner(obj);
            Toast.makeText(ReportActivity.this,"成功存入SD卡和App中！",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //跳转到MainActivity
        finishbutton = findViewById(R.id.button);
        finishbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("code", 0);
                startActivity(intent);
                finish();
            }
        });
    }
    public class saveFileInEx{

        public void saveFile(JSONObject jsonObject)
                throws IOException {

            File path = Environment.getExternalStorageDirectory();
            File saveData = new File(path, "results.json");
            FileOutputStream fout=new FileOutputStream(saveData,true);
            fout.write(jsonObject.toJSONString().getBytes());
            fout.write("\r\n".getBytes());
            fout.flush();
            fout.close();

        }

        //存储文件到内部存储
        @RequiresApi(api = Build.VERSION_CODES.N)
        public void saveFileInner(JSONObject jsonObject)
                throws IOException {
            File pathInner=getApplicationContext().getDataDir();
            File saveDataInner=new File(pathInner, "results.json");
            FileOutputStream foutcode=new FileOutputStream(saveDataInner,true);
            foutcode.write(jsonObject.toJSONString().getBytes());
            foutcode.write("\r\n".getBytes());
            foutcode.flush();
            foutcode.close();

        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
