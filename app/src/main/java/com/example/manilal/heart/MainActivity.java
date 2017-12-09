package com.example.manilal.heart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView patientLoginHomePage,doctorLoginHomePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        patientLoginHomePage = (TextView) findViewById(R.id.patient_login_homepage);
        doctorLoginHomePage = (TextView) findViewById(R.id.doctor_login_homepage);

        patientLoginHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,PatientLogin.class);
                startActivity(i);
            }
        });

        doctorLoginHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,DoctorLogin.class);
                startActivity(i);
            }
        });
    }
}
