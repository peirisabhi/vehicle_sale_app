package com.abhipeiris.androidassignment1shop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abhipeiris.androidassignment1shop.config.Config;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    EditText fullName;
    EditText email;
    EditText password;
    CheckBox checkBox;
    Button signUp;
    LinearLayout signIn;
    KProgressHUD hud;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullName = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        signUp = findViewById(R.id.button3);
        checkBox = findViewById(R.id.checkBox);
        signIn = findViewById(R.id.linearLayout);

        hud = KProgressHUD.create(SignUpActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });


        email.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ImageView img = findViewById(R.id.imageView7);
                if (s.length() != 0) {
                    if (isValidEmail(s)) {
                        img.setVisibility(View.VISIBLE);
                    } else {
                        img.setVisibility(View.INVISIBLE);
                    }
                } else {
                    img.setVisibility(View.INVISIBLE);
                }
            }
        });


        password.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ImageView img = findViewById(R.id.imageView1);
                if (s.length() != 0) {
                    if (validatePassword(s)) {
                        img.setVisibility(View.VISIBLE);
                    } else {
                        img.setVisibility(View.INVISIBLE);
                    }
                } else {
                    img.setVisibility(View.INVISIBLE);
                }
            }
        });

        fullName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ImageView img = findViewById(R.id.imageView12);
                if (s.length() != 0) {
                        img.setVisibility(View.VISIBLE);
                } else {
                    img.setVisibility(View.INVISIBLE);
                }
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(checkBox.isChecked());


                if (!password.getText().toString().equals("") && !email.getText().toString().equals("") && !fullName.getText().toString().equals("")) {

                    if (isValidEmail(email.getText().toString())) {

                        if (checkBox.isChecked()) {
                            hud.show();
                            JSONObject jsonLoginCredentials = new JSONObject();
                            try {
                                jsonLoginCredentials.put("name", fullName.getText().toString());
                                jsonLoginCredentials.put("email", email.getText().toString());
                                jsonLoginCredentials.put("password", password.getText().toString());
                                jsonLoginCredentials.put("status", "1");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            System.out.println(jsonLoginCredentials.toString());


                            RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                            String url = Config.HOST + "AndroidAssignment1Shop/SignUp?a=aa";

                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonLoginCredentials, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    hud.dismiss();
                                    Log.i("Response", "Response is: " + response);

                                    try {
                                        int status = response.getInt("status");
                                        System.out.println(status);

                                        if (status == 200) {
                                            Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.i("Response", error.toString());
                                }
                            });
                            queue.add(jsonObjectRequest);
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Agree Our Conditions", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Required Field Missing", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private boolean validatePassword(CharSequence password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            System.out.println("false");
            return false;
        } else {
            System.out.println("true");
            return true;
        }
    }


}

