package com.abhipeiris.androidassignment1shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.abhipeiris.androidassignment1shop.adapter.CategoryAdapter;
import com.abhipeiris.androidassignment1shop.config.Config;
import com.abhipeiris.androidassignment1shop.model.CategoryModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<CategoryModel> categoryModels = new ArrayList<>();
    private ImageView logout;
    KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        logout = findViewById(R.id.imageView2);

        hud = KProgressHUD.create(CategoryActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f).show();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


        recyclerView = findViewById(R.id.category_recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        RequestQueue queue = Volley.newRequestQueue(CategoryActivity.this);
        String url = Config.HOST + "AndroidAssignment1Shop/GetAllCategories";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hud.dismiss();
                Log.i("Response", "Response is: " + response.toString().trim());

                try {
                    int status = response.getInt("status");
                    System.out.println(status);

                    if (status == 200) {
                        System.out.println("ok");
                        Log.i("Response", "Response is String: " + response.getString("data"));
                        JSONArray jsonArray = response.getJSONArray("data");
                        Log.i("Response", "Response is json: " + jsonArray);
                        categoryModels.clear();
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            categoryModels.add(new CategoryModel(object.getInt("id"), object.getString("category"), Config.HOST +object.getString("img")));
                        }

                        CategoryAdapter categoryAdapter = new CategoryAdapter(getApplicationContext(), categoryModels);
                        recyclerView.setAdapter(categoryAdapter);

                        categoryAdapter.setListener(new CategoryAdapter.Listener() {
                            @Override
                            public void onClick(int position) {
//                                Toast.makeText(getApplicationContext(), categoryModels.get(position).getCategory(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                                intent.putExtra("categoryId", categoryModels.get(position).getId());
                                startActivity(intent);
                            }
                        });

                        Log.i("Response", "Size>>>: " + categoryModels.size());
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

    }
}