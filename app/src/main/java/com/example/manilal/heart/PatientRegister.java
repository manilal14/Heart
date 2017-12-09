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

public class PatientRegister extends AppCompatActivity {

    TextView switchToPatientLoginHomePage;
    EditText Name,Mobile,Gender,Age,Address,Password,ConfirmPassword;
    //Spinner Gender;
    String name,mobile,gender,age,address,password,confirmPassword;
    Button reg_btn;
    AlertDialog.Builder builder;
    String reg_url = "http://192.168.43.154/Heart/heart_patients_register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        switchToPatientLoginHomePage = (TextView) findViewById(R.id.back_to_login);
        switchToPatientLoginHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PatientRegister.this,PatientLogin.class);
                startActivity(i);
            }
        });

        Name = (EditText) findViewById(R.id.name);
        Mobile = (EditText) findViewById(R.id.mobile_no);

        Gender = (EditText) findViewById(R.id.gender_spinner);

       // Gender = (Spinner) findViewById(R.id.gender_spinner);

        Age = (EditText) findViewById(R.id.age);
        Address = (EditText) findViewById(R.id.address);
        Password = (EditText) findViewById(R.id.password);
        ConfirmPassword = (EditText) findViewById(R.id.confirm_password);

        builder = new AlertDialog.Builder(PatientRegister.this);

        reg_btn = (Button) findViewById(R.id.reg_button);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = Name.getText().toString().trim();
                mobile = Mobile.getText().toString().trim();


                //gender = String.valueOf(Gender);


               gender = Gender.getText().toString().trim();

                age = Age.getText().toString().trim();
                address = Address.getText().toString().trim();
                password = Password.getText().toString().trim();
                confirmPassword = ConfirmPassword.getText().toString().trim();

                /*Log.v("Patient",name);
                Log.v("Patient",mobile);
                Log.v("Patient",gender);
                Log.v("Patient",age);
                Log.v("Patient",address);
                Log.v("Patient",password);
                Log.v("Patient",confirmPassword);*/


                if(name.equals("") || mobile.equals("") || gender.equals("") ||
                        age.equals("") || password.equals("") || confirmPassword.equals("") )
                {
                    builder.setTitle("Something went wrong...");
                    builder.setMessage("Please fill all the required field");
                    displayAleart("input error");
                }

                else
                {
                    if(!password.equals(confirmPassword))
                    {
                        builder.setTitle("Something went wrong...");
                        builder.setMessage("Password is not matching");
                        displayAleart("input error");
                    }

                    else
                    {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");
                                            String message = jsonObject.getString("message");
                                            builder.setTitle("Server Response...");
                                            builder.setMessage(message);
                                            displayAleart(code);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String,String> params  = new HashMap<String,String>();

                                params.put("name",name);
                                params.put("mobile_no",mobile);
                                params.put("gender",gender);
                                params.put("age",age);
                                params.put("address",address);
                                params.put("password",password);

                                return params;
                            }
                        };

                        MySingleton.getmInstance(PatientRegister.this).addToRequestque(stringRequest);

                    }
                }


            }
        });
    }

    protected void displayAleart(final String code)
    {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(code.equals("input error"))
                {
                    Password.setText("");
                    ConfirmPassword.setText("");
                }
                else if(code.equals("reg_success"))
                {
                    finish();
                }
                else if(code.equals("reg_failed"))
                {
                    Name.setText("");

                    Password.setText("");
                    ConfirmPassword.setText("");
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
