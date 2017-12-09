package com.example.manilal.heart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PatientLogin extends AppCompatActivity {

    TextView switchToPatientRegistrationHomePage;
    EditText Mobili_no,Password;
    String mobile_no,password;
    Button login_btn;
    AlertDialog.Builder builder;
    String login_url = "http://192.168.43.154/Heart/heart_patients_login.php";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        switchToPatientRegistrationHomePage = (TextView) findViewById(R.id.patient_registration_page);
        switchToPatientRegistrationHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(PatientLogin.this,PatientRegister.class);
                startActivity(i);
            }
        });

        builder = new AlertDialog.Builder(PatientLogin.this);
        Mobili_no = (EditText) findViewById(R.id.mobile_no);
        Password =  (EditText) findViewById(R.id.password);
        login_btn = (Button) findViewById(R.id.login_button);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile_no = Mobili_no.getText().toString().trim();
                password = Password.getText().toString().trim();

                if(mobile_no.equals("") || password.equals(""))
                {
                    builder.setTitle("Login Error...");
                    displayAleart("Enter valid UserName and Password");
                }

                else
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");

                                        if(code.equals("login failed"))
                                        {

                                            builder.setTitle("Login Error");
                                            displayAleart(jsonObject.getString("message"));
                                        }
                                        else
                                        {

                                            Intent i = new Intent(PatientLogin.this,PatientsLoginSuccess.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("name",jsonObject.getString("name"));
                                            i.putExtras(bundle);
                                            startActivity(i);
                                            Password.setText("");
                                            Mobili_no.setText("");

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PatientLogin.this,"Error",Toast.LENGTH_SHORT).show();

                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params  = new HashMap<String,String>();

                            params.put("mobile_no",mobile_no);
                            params.put("password",password);
                            return params;
                        }
                    };

                    MySingleton.getmInstance(PatientLogin.this).addToRequestque(stringRequest);

                }

            }
        });
    }


    protected void displayAleart(final String message)
    {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Password.setText("");
                Mobili_no.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
