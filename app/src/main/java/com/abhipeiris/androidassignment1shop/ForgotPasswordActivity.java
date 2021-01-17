package com.abhipeiris.androidassignment1shop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abhipeiris.androidassignment1shop.adapter.ProductAdapter;
import com.abhipeiris.androidassignment1shop.config.Config;
import com.abhipeiris.androidassignment1shop.model.ProductModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email;
    Button sendCode;
    KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.editEmail);
        sendCode = findViewById(R.id.button3);

        hud = KProgressHUD.create(ForgotPasswordActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        sendCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                hud.show();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = Config.HOST + "AndroidAssignment1Shop/ResetPassword?email="+email.getText().toString();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hud.dismiss();
                        Log.i("Response", "Response is: " + response.toString().trim());

                        try {
                            int status = response.getInt("status");
                            System.out.println(status);

                            if (status == 200) {
                                Toast.makeText(getApplicationContext(), "Email Sent", Toast.LENGTH_LONG).show();

                            } else if(status == 400){
                                Toast.makeText(getApplicationContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
                            }else if(status == 500){
                                Toast.makeText(getApplicationContext(), "Error Please try again later", Toast.LENGTH_SHORT).show();

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

            }
        });

    }
}