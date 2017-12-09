package com.example.manilal.heart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DoctorsLoginSuccess extends AppCompatActivity {

    TextView Name,Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_login_success);


        Name = (TextView) findViewById(R.id.name);
        Email = (TextView) findViewById(R.id.email);

        Bundle bundle = getIntent().getExtras();

        Name.setText(bundle.getString("name"));
        Email.setText(bundle.getString("email"));
    }
}
