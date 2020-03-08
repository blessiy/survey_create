package com.example.survey_create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mbutton;
    private CheckBox mcheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        int code = intent.getIntExtra("code", -1);
        if (code == 0)
        {
            finish();
        }
        else {
            mbutton = findViewById(R.id.button);
            mcheckbox=findViewById(R.id.checkbox);

                mbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                        {
                            if(mcheckbox.isChecked())
                            {
                                startActivity(new Intent(MainActivity.this, Question1Activity.class));
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Please check this option!",Toast.LENGTH_SHORT).show();
                            }
                            }
                });
    }
}
}

