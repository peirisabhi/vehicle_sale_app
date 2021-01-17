package com.abhipeiris.androidassignment1shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhipeiris.androidassignment1shop.adapter.CategoryAdapter;
import com.abhipeiris.androidassignment1shop.adapter.ProductAdapter;
import com.abhipeiris.androidassignment1shop.config.Config;
import com.abhipeiris.androidassignment1shop.model.CategoryModel;
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

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<ProductModel> productModelList = new ArrayList<>();
    private ImageView logout;
    KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        logout = findViewById(R.id.imageView2);

        hud = KProgressHUD.create(ProductsActivity.this)
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


        recyclerView = findViewById(R.id.product_recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        int categoryId = 0;
        if (intent != null){
            categoryId = intent.getIntExtra("categoryId", 0);
        }

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = Config.HOST + "AndroidAssignment1Shop/GetProductsByCategory?category_id="+categoryId;

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
                        productModelList.clear();
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            productModelList.add(new ProductModel(object.getInt("id"), object.getString("category"), object.getString("productName"), object.getString("description"), object.getDouble("price"), Config.HOST + object.getString("img")));
                        }

                        ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), productModelList);
                        recyclerView.setAdapter(productAdapter);


                        Log.i("Response", "Size>>>: " + productModelList.size());
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