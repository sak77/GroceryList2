package com.saket.grocerylist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saket.grocerylist.data.Product;

import java.util.List;

/**
 * Created by sshriwas on 2019-11-03
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    List<Product> mProducts;

    public interface onItemSelectedListener {
        void onItemSelected (Product selectedProduct, View selectedView);
    }

    onItemSelectedListener mOnItemSelectedListener;

    public ProductsAdapter (List<Product> products, onItemSelectedListener listener) {
        mProducts = products;
        mOnItemSelectedListener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item , parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(itemview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if (mProducts.get(position) != null) {
            holder.txtName.setText(mProducts.get(position).product_name);
            holder.txtQty.setText(mProducts.get(position).qty);
            holder.mView.setOnClickListener(view -> {
                mOnItemSelectedListener.onItemSelected(mProducts.get(position), view);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView txtName;
        TextView txtQty;
        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            txtName = itemView.findViewById(R.id.txtName);
            txtQty = itemView.findViewById(R.id.txtQty);
        }
    }
}
