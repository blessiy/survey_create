package com.example.survey_create;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Question2Activity extends AppCompatActivity {
    private TextView mq2tv;
    private CheckBox mo1cb, mo2cb,mo3cb, mo4cb,mo5cb;
    private Button mbutton;
    private String question2,answer2,option1,option2,option3,option4,option5;
    private ConstraintLayout mconstraintlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        mbutton=findViewById(R.id.button);
        mq2tv=findViewById(R.id.textview2);
        mo1cb=findViewById(R.id.checkbox1);
        mo2cb=findViewById(R.id.checkbox2);
        mo3cb=findViewById(R.id.checkbox3);
        mo4cb=findViewById(R.id.checkbox4);
        mo5cb=findViewById(R.id.checkbox5);
        mconstraintlayout=findViewById(R.id.constraintlayout);

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

            option1=jsonobject.getJSONArray("survey").getJSONObject(1).getJSONArray("options").getJSONObject(0).getString("1");
            option2=jsonobject.getJSONArray("survey").getJSONObject(1).getJSONArray("options").getJSONObject(1).getString("2");
            option3=jsonobject.getJSONArray("survey").getJSONObject(1).getJSONArray("options").getJSONObject(2).getString("3");
            option4=jsonobject.getJSONArray("survey").getJSONObject(1).getJSONArray("options").getJSONObject(3).getString("4");
            option5=jsonobject.getJSONArray("survey").getJSONObject(1).getJSONArray("options").getJSONObject(4).getString("5");
            question2 = jsonobject.getJSONArray("survey").getJSONObject(1).getString("question");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mq2tv.setText(question2);
        mo1cb.setText(option1);
        mo2cb.setText(option2);
        mo3cb.setText(option3);
        mo4cb.setText(option4);
        mo5cb.setText(option5);



        mbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mo1cb.isChecked() && !mo2cb.isChecked()&& !mo3cb.isChecked()&& !mo4cb.isChecked()&& !mo5cb.isChecked()) {
                    Toast.makeText(Question2Activity.this, "The answer can't be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    StringBuilder as2 = new StringBuilder();
                    //检查checkbox
                    int count = mconstraintlayout.getChildCount();
                    for(int i = 0;i < count;i++){
//					获得子控件对象
                        View child = mconstraintlayout.getChildAt(i);
//					判断是否是CheckBox
                        if(child instanceof CheckBox){
//						转为CheckBox对象
                            CheckBox checkbox = (CheckBox)child;
                            if(checkbox.isChecked()){
                                as2.append(checkbox.getText()+",");
                            }
                        }
                    }
                    if(as2.length()!= 0)
                        answer2=String.valueOf(as2);

                    //获取SharedPreferences对象
                    SharedPreferences sp = getSharedPreferences("SP", MODE_PRIVATE);
                    //存入数据
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("question2", question2);
                    editor.putString("answer2", answer2);
                    editor.commit();

                    startActivity(new Intent(Question2Activity.this, Question3Activity.class));
                }
            }
        });

    }
}
