package com.example.survey_create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Question1Activity extends AppCompatActivity {
    private TextView mq1tv;
    private RadioButton mo1rb, mo2rb,mradiobutton;
    private Button mbutton;
    private String question1,option1,option2,answer1;
    private RadioGroup mradiogroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        mq1tv = findViewById(R.id.textview2);
        mo1rb = findViewById(R.id.radiobutton1);
        mo2rb = findViewById(R.id.radiobutton2);
        mbutton = findViewById(R.id.button);
        mradiogroup = findViewById(R.id.radiogroup);

        try {
            AssetManager assetManager = getAssets(); //获得assets资源管理器（assets中的文件无法直接访问，可以使用AssetManager访问）
            InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open("questions.json"), "UTF-8"); //使用IO流读取json文件内容
            BufferedReader br = new BufferedReader(inputStreamReader);//使用字符高效流
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            inputStreamReader.close();

            JSONObject jsonobject = new JSONObject(builder.toString()); // 从builder中读取了json中的数据。
            // 直接传入JSONObject来构造一个实例
            JSONArray jsonarray = jsonobject.getJSONArray("survey");

            option1 = jsonobject.getJSONArray("survey").getJSONObject(0).getJSONArray("options").getJSONObject(0).getString("1");
            option2 = jsonobject.getJSONArray("survey").getJSONObject(0).getJSONArray("options").getJSONObject(1).getString("2");
            question1 = jsonobject.getJSONArray("survey").getJSONObject(0).getString("question");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mq1tv.setText(question1);
        mo1rb.setText(option1);
        mo2rb.setText(option2);

        mradiogroup=findViewById(R.id.radiogroup);
        //radiogroup传值
        mradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mradiobutton = findViewById(mradiogroup.getCheckedRadioButtonId());
                answer1=String.valueOf(mradiobutton.getText());
                //获取SharedPreferences对象
                SharedPreferences sp = getSharedPreferences("SP", MODE_PRIVATE);
                //存入数据
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("question1", question1);
                editor.putString("answer1", answer1);
                editor.commit();
            }
        });


        mbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                if (!mo1rb.isChecked() && !mo2rb.isChecked()) {
                    Toast.makeText(Question1Activity.this, "The answer can't be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(Question1Activity.this, Question2Activity.class));
                }
            }
        });
    }
    }

