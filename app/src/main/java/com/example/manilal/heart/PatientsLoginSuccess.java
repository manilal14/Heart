package com.example.manilal.heart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PatientsLoginSuccess extends AppCompatActivity {

    TextView Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_login_success);

        Name = (TextView) findViewById(R.id.name);
        Bundle bundle = getIntent().getExtras();
        Name.setText(bundle.getString("name"));

    }
}
