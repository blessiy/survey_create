package com.example.survey_create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Question3Activity extends AppCompatActivity {
    private TextView mq3tv;
    private EditText ma3et;
    private Button mbutton;
    private String question3,answer3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);

        mq3tv=findViewById(R.id.textview2);
        ma3et=findViewById(R.id.edittext);
        mbutton=findViewById(R.id.button);

        try {
            AssetManager assetManager = getAssets(); //获得assets资源管理器（assets中的文件无法直接访问，可以使用AssetManager访问）
            InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open("questions.json"),"UTF-8"); //使用IO流读取json文件内容
            BufferedReader br = new BufferedReader(inputStreamReader);//使用字符高效流
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine())!=null){
                builder.append(line);
            }
            br.close();
            inputStreamReader.close();

            JSONObject jsonobject = new JSONObject(builder.toString()); // 从builder中读取了json中的数据。
            // 直接传入JSONObject来构造一个实例
            JSONArray jsonarray = jsonobject.getJSONArray("survey");

            question3 = jsonobject.getJSONArray("survey").getJSONObject(2).getString("question");

            mq3tv.setText(question3);

            mbutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (ma3et.length()==0) {
                        Toast.makeText(Question3Activity.this, "The answer can't be empty!", Toast.LENGTH_SHORT).show();
                    } else {
                        answer3=String.valueOf(ma3et.getText());
                        //获取SharedPreferences对象
                        SharedPreferences sp = getSharedPreferences("SP", MODE_PRIVATE);
                        //存入数据
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("question3", question3);
                        editor.putString("answer3", answer3);
                        editor.commit();
                        startActivity(new Intent(Question3Activity.this, ReportActivity.class));
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
