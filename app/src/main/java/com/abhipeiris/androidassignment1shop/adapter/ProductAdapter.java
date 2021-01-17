package com.abhipeiris.androidassignment1shop.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abhipeiris.androidassignment1shop.R;
import com.abhipeiris.androidassignment1shop.model.ProductModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<ProductModel> productModelList;
    private Context context;

    public ProductAdapter(Context context, List<ProductModel> productModelList) {
        this.productModelList = productModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cc = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_view, parent, false);

        return new ViewHolder(cc);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView name = cardView.findViewById(R.id.label_name);
        TextView price = cardView.findViewById(R.id.label_price);
        ImageView img = cardView.findViewById(R.id.image_main);

        name.setText(productModelList.get(position).getProductName());
        price.setText(productModelList.get(position).getDescription());

        Glide.with(context)
                .load(Uri.parse(productModelList.get(position).getImg()))
                .into(img);

    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            this.cardView = itemView;
        }
    }
}
